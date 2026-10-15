# Trusted Container Base Images - Technical Design

## Status

**Proposed**

## Purpose

Provide a repeatable and security-conscious way for Docker build stages in this repository to trust the CDC enterprise TLS interception certificate authority (CA). The initial implementation addresses the two observed failures:

1. Gradle cannot download Maven metadata through the CDC Zscaler path because the Java trust store does not trust the enterprise CA.
2. Elasticsearch cannot download the `analysis-phonetic` plugin because its bundled JDK does not trust the enterprise CA.

The design establishes a repository-owned framework for additional build families, including Python/`uv` and Node/npm, without copying certificate files beside each application Dockerfile.

This design does **not** disable TLS verification, use `--insecure`, or add a proxy exception. Those approaches would bypass the control that the enterprise proxy is intended to provide.

## Background and Current State

The reported failures are PKIX validation failures during Docker builds. The successful TLS connection inspection shows an intercepted certificate with a subject for `elastic.co` and an issuer in the CDC PKI. The required enterprise root currently available to the developer is `CDC-G2` (SHA-256 fingerprint `65:DE:48:95:9F:B5:74:07:4C:3F:8B:F5:2E:F4:EC:5C:4A:E7:22:0E:2E:B3:FF:04:17:C6:BD:02:31:B0:F1:B2`).

Host or Docker daemon trust is insufficient. It can allow Docker to pull an image, but it does not alter the trust store used by processes executed by a Dockerfile `RUN` instruction. In particular:

- Gradle resolves dependencies in `gradle:9-jdk21`, an Ubuntu image with its own JDK trust store.
- The Elasticsearch plugin installer uses `/usr/share/elasticsearch/jdk`, an embedded JDK in an RHEL-based image.
- Future `uv` and npm builds may use a native or language-specific trust store rather than the host store.

The current build topology is:

| Build family | Current upstream image | Current consumers | Trust problem |
| --- | --- | --- | --- |
| Gradle/JDK 21 | `gradle:9-jdk21` | `apps/modernization-api/Dockerfile`, `apps/nbs-gateway/Dockerfile`, `apps/question-bank/Dockerfile` | Gradle/Java TLS validation |
| Elasticsearch 9.4.2 | `docker.elastic.co/elasticsearch/elasticsearch:9.4.2` | `cdc-sandbox/elasticsearch/Dockerfile` | Elasticsearch plugin downloader/Java TLS validation |
| UBI + uv | `registry.access.redhat.com/ubi10/ubi-minimal:latest` | `apps/report-execution/Dockerfile` | Not currently a CA failure, but an expected future family |
| Node 22 | `node:22` | `apps/modernization-api/Dockerfile`, `apps/modernization-ui/Dockerfile` | Not currently a CA failure, but an expected future family |

The three Gradle application images create a trimmed runtime with `jlink`. Elasticsearch is built by `cdc-sandbox/build_modernized.sh`; the application services are built with the repository-root Compose file included by `cdc-sandbox/docker-compose.yml`. Production container workflows are reusable workflows from `CDCgov/NEDSS-Workflows`, so CI integration needs explicit coordination with that repository.

## Goals

- Keep trusted-image Dockerfile sources, scripts, documentation, and verification in this repository.
- Build CA-aware images once per upstream image family, then reuse them by tag.
- Keep CA files out of application directories and out of source control by default.
- Trust the CA in both the operating system trust store and the relevant language/runtime trust store.
- Ensure the enterprise CA is needed only in build stages whenever feasible.
- Preserve normal upstream image defaults for developers and CI environments that do not require the enterprise CA.
- Make adding a new language family a small, standard operation.
- Make certificate rotation, provenance, and validation auditable.

## Non-Goals

- Configure Docker Desktop or the Docker daemon as a universal outbound TLS trust mechanism. Docker has no supported setting that injects a CA into all arbitrary `RUN` containers.
- Change application runtime HTTP behavior unless the application itself needs to call an enterprise-intercepted endpoint.
- Store certificates in every service build context.
- Modify the external `CDCgov/NEDSS-Workflows` implementation as part of the first local-development rollout.
- Support the Windows NBS6 build in the first iteration. Windows certificate-store handling is a separate adapter and must be designed independently.

## Architecture Decision

Implement **trusted build-image adapters**. An adapter is a Dockerfile that derives from one pinned upstream image, adds the approved CA to every trust store used by that image family, and is tagged as a local or registry image. Application Dockerfiles select the adapter through a pre-`FROM` build argument.

There cannot be one universal trusted image because the initial consumers have incompatible upstream operating systems and JDK layouts. The repository will own a single framework containing multiple adapters:

```text
approved enterprise CA file
          |
          v
local/CI staging directory (ephemeral)
          |
          v
repository-owned trusted-image Dockerfiles
          |
          +--> nedss trusted Gradle builder ----> Gradle application build stage
          |
          +--> nedss trusted Elasticsearch builder -> plugin-install stage
          |
          +--> future trusted UBI/uv builder
          |
          +--> future trusted Node builder
```

The generated images live in the developer's Docker image cache for local use and, when approved, in the organization container registry for CI use. Their source definitions live in this repository.

### Why this approach

| Alternative | Decision | Reason |
| --- | --- | --- |
| Copy the CA into every service Dockerfile | Rejected | Duplicates security-sensitive logic and makes rotation error-prone. |
| Mount a certificate into Compose runtime containers | Rejected | Build-time Gradle, `uv`, npm, and plugin downloads occur before runtime. |
| Configure only Docker Desktop/daemon CA trust | Rejected | Affects registry access, not every process inside a `RUN` layer. |
| Disable certificate verification | Rejected | Defeats TLS identity validation. |
| One derived image for all workloads | Rejected | Gradle is Ubuntu/JDK-based and Elasticsearch is RHEL with a bundled JDK; a single base cannot safely replace both. |
| Repository-owned image adapters | Accepted | Centralizes trust behavior, supports immutable tags, minimizes application changes, and is extensible. |

## Proposed Repository Layout

Create the following directory. Certificate material is intentionally not placed in this tree.

```text
container-images/
  trusted-builders/
    README.md
    certificate-manifest.env
    scripts/
      build-local-images.sh
      verify-images.sh
    gradle-jdk21/
      Dockerfile
    elasticsearch-9.4/
      Dockerfile
    templates/
      ubi-uv.Dockerfile
      node.Dockerfile
```

`certificate-manifest.env` is committed and contains only non-secret verification metadata:

```dotenv
TRUSTED_CA_ALIAS=cdc-g2
TRUSTED_CA_SUBJECT='DC=gov,DC=cdc,CN=CDC-G2'
TRUSTED_CA_SHA256=65DE48959FB574074C3F8B5F2EE4EC5C4AE7220E2EB3FF0417C6BD0231B0F1B2
```

The CA PEM file is supplied through `NEDSS_TRUSTED_CA_FILE`, for example from the workstation PKI installation. `build-local-images.sh` validates its fingerprint and subject before use, copies it into a `mktemp` staging context, and removes that context through a shell `trap` on exit. The script must reject an unreadable file, a non-PEM certificate, a subject mismatch, and a fingerprint mismatch.

The design does not assume the CA is secret. It is nevertheless kept out of version control until Security approves a distribution policy. The certificate is a high-impact trust anchor and must be integrity-verified exactly as above.

## Trusted Image Contracts

Every adapter must meet the following contract:

1. Accept its upstream image reference as an `ARG` before `FROM`.
2. Add the approved certificate to the operating-system trust store when the distribution supports one.
3. Add the certificate to every non-OS trust store used by the build tool.
4. Return to the upstream image's expected non-root user before the adapter image is complete.
5. Label the image with the upstream reference, certificate SHA-256 fingerprint, source repository, and build timestamp.
6. Provide a verification command that proves the trust anchor exists and a tool-specific HTTPS check that does not use an insecure option.
7. Never accept an arbitrary certificate path or arbitrary fingerprint from a Compose environment variable.

### Gradle/JDK 21 adapter

**Source:** `container-images/trusted-builders/gradle-jdk21/Dockerfile`
**Upstream:** `gradle:9-jdk21`
**Local tag format:** `nedss-local/trusted-gradle:9-jdk21-cdc-g2-<fingerprint12>`

The Gradle image is Ubuntu-based. It must update both Ubuntu's CA bundle and the OpenJDK `cacerts` store. Relying only on `update-ca-certificates` is insufficient because the Gradle image's JDK has its own `cacerts` file.

Representative implementation:

```dockerfile
ARG UPSTREAM_IMAGE=gradle:9-jdk21
FROM ${UPSTREAM_IMAGE}

ARG CA_SHA256
LABEL org.opencontainers.image.source="https://github.com/CDCgov/NEDSS-Modernization" \
      gov.cdc.nedss.trusted-ca.sha256="${CA_SHA256}"

USER root
COPY cdc-g2.crt /usr/local/share/ca-certificates/cdc-g2.crt
RUN update-ca-certificates && \
    keytool -importcert -noprompt -trustcacerts \
      -alias cdc-g2 \
      -file /usr/local/share/ca-certificates/cdc-g2.crt \
      -cacerts -storepass changeit
```

The upstream Gradle image currently runs as root. The final adapter must explicitly set the documented upstream user if that changes in a future upstream image.

All three Gradle Dockerfiles will gain the following selector immediately before their first `FROM` instruction:

```dockerfile
ARG GRADLE_BUILDER_IMAGE=gradle:9-jdk21
FROM ${GRADLE_BUILDER_IMAGE} AS builder
```

`apps/modernization-api/Dockerfile` uses `AS api-builder`, which must retain that existing stage name. The selectors apply only to the Gradle build stages; Node UI stages and UBI runtime stages remain unchanged.

To avoid carrying the enterprise CA into the final application runtime through `jlink`, each Java Dockerfile should add a clean runtime-assembly stage based on the unmodified JDK image. That stage receives only the already-built JAR from the trusted Gradle stage, executes `jar`, `jdeps`, and `jlink`, then the existing UBI runtime receives the resulting runtime and JAR. This is required verification work: the final runtime must be inspected with `keytool` to prove the `cdc-g2` alias is absent.

### Elasticsearch 9.4 adapter

**Source:** `container-images/trusted-builders/elasticsearch-9.4/Dockerfile`
**Upstream:** `docker.elastic.co/elasticsearch/elasticsearch:9.4.2`
**Local tag format:** `nedss-local/trusted-elasticsearch:9.4.2-cdc-g2-<fingerprint12>`

The Elasticsearch image is RHEL-based and uses an embedded JDK. The adapter must update RHEL's CA bundle and `/usr/share/elasticsearch/jdk` directly:

```dockerfile
ARG UPSTREAM_IMAGE=docker.elastic.co/elasticsearch/elasticsearch:9.4.2
FROM ${UPSTREAM_IMAGE}

ARG CA_SHA256
LABEL org.opencontainers.image.source="https://github.com/CDCgov/NEDSS-Modernization" \
      gov.cdc.nedss.trusted-ca.sha256="${CA_SHA256}"

USER root
COPY cdc-g2.crt /etc/pki/ca-trust/source/anchors/cdc-g2.crt
RUN update-ca-trust extract && \
    /usr/share/elasticsearch/jdk/bin/keytool -importcert -noprompt -trustcacerts \
      -alias cdc-g2 \
      -file /etc/pki/ca-trust/source/anchors/cdc-g2.crt \
      -cacerts -storepass changeit
USER 1000
```

`cdc-sandbox/elasticsearch/Dockerfile` will use this adapter only in a `plugin-builder` stage. It will install `analysis-phonetic` with `--batch`, then copy only `/usr/share/elasticsearch/plugins/analysis-phonetic` into a clean official Elasticsearch runtime stage. This ensures the runtime container does not retain the enterprise CA merely because it downloaded the plugin during the build.

```dockerfile
ARG ELASTICSEARCH_IMAGE=docker.elastic.co/elasticsearch/elasticsearch:9.4.2
ARG ELASTICSEARCH_PLUGIN_BUILDER_IMAGE=${ELASTICSEARCH_IMAGE}

FROM ${ELASTICSEARCH_PLUGIN_BUILDER_IMAGE} AS plugin-builder
RUN /usr/share/elasticsearch/bin/elasticsearch-plugin install --batch analysis-phonetic

FROM ${ELASTICSEARCH_IMAGE}
ENV xpack.security.enabled=false
ENV discovery.type=single-node
COPY --from=plugin-builder --chown=1000:0 \
  /usr/share/elasticsearch/plugins/analysis-phonetic \
  /usr/share/elasticsearch/plugins/analysis-phonetic
```

The existing local certificate copies in `cdc-sandbox/elasticsearch/certs/` are a temporary troubleshooting implementation. They are replaced by the adapter workflow; no certificate directory remains in the Elasticsearch build context after migration.

### Future adapter templates

Templates are included now but no consumer moves until a genuine requirement exists.

| Family | Base behavior | Tool-specific behavior | Intended consumers |
| --- | --- | --- | --- |
| UBI + uv | Add CA to `/etc/pki/ca-trust/source/anchors`; run `update-ca-trust extract`. | Set `UV_NATIVE_TLS=true` so `uv` uses the platform trust store rather than its bundled root set. | `report-execution` if future package downloads fail TLS validation. |
| Node | Add CA to the base distribution's OS trust store. | Set `NODE_EXTRA_CA_CERTS` to the PEM path. The value must be set before Node starts. | UI build stages if `npm ci` fails TLS validation. |
| NiFi/Kibana | Add OS CA plus the bundled JVM/Node trust configuration. | Select only after a reproducible build-time TLS failure. | `cdc-sandbox/nifi`, `cdc-sandbox/kibana`. |
| Windows | Import with `Import-Certificate` into the appropriate machine store and validate Java separately. | Requires its own threat review and Windows runner validation. | `cdc-sandbox/nbs6-win-container`. |

## Local Build Flow

1. The developer obtains the current approved `CDC-G2` PEM from the enterprise PKI source.
2. The developer exports `NEDSS_TRUSTED_CA_FILE` with the absolute certificate path.
3. The repository script validates the certificate against `certificate-manifest.env` and builds/uses locally cached trusted images.
4. The script exports immutable local image references for the current shell:

   ```dotenv
   NEDSS_GRADLE_BUILDER_IMAGE=nedss-local/trusted-gradle:9-jdk21-cdc-g2-<fingerprint12>
   NEDSS_ELASTICSEARCH_PLUGIN_BUILDER_IMAGE=nedss-local/trusted-elasticsearch:9.4.2-cdc-g2-<fingerprint12>
   ```

5. Compose passes those values to the corresponding `build.args`; direct `docker build` users pass the same values through `--build-arg`.
6. `cdc-sandbox/build_all.sh` sources a repository helper before invoking `build_modernized.sh`. The helper performs an idempotent local image build only when `NEDSS_TRUSTED_CA_FILE` is set. When it is unset, it leaves the default upstream arguments in effect for developers outside the intercepted network.

The trusted-image helper must not silently choose a certificate. If the configured certificate fails validation, it exits before any application build begins and prints the expected subject and fingerprint.

### Compose and Dockerfile wiring

The root `docker-compose.yml` must add the Gradle argument to `modernization-api`, `nbs-gateway`, and `pagebuilder-api`:

```yaml
build:
  context: ./
  dockerfile: ./apps/example/Dockerfile
  args:
    GRADLE_BUILDER_IMAGE: ${NEDSS_GRADLE_BUILDER_IMAGE:-gradle:9-jdk21}
```

`cdc-sandbox/docker-compose.yml` must add the Elasticsearch builder argument:

```yaml
build:
  context: ./elasticsearch
  args:
    ELASTICSEARCH_PLUGIN_BUILDER_IMAGE: >-
      ${NEDSS_ELASTICSEARCH_PLUGIN_BUILDER_IMAGE:-docker.elastic.co/elasticsearch/elasticsearch:9.4.2}
```

All argument defaults must remain the present upstream image values. This preserves existing CI and non-CDC developer behavior until the trusted image is explicitly selected.

## CI and Registry Strategy

The initial implementation is local-first. CI currently calls reusable workflows in `CDCgov/NEDSS-Workflows` and the caller workflows do not show a facility for setting Docker `--build-arg` values or building prerequisite base images.

Before enabling trusted bases in CI, the platform team must provide one of these approved mechanisms:

1. Extend the reusable build workflows with a controlled `docker_build_args` input and a protected setup step that pulls a prebuilt trusted adapter image from Harbor/ECR.
2. Add repository-owned build jobs that build and push the trusted adapters, then invoke the reusable workflow with immutable image digest build arguments.
3. Move the image-build implementation from the reusable workflow into this repository, only if ownership and maintenance are explicitly approved.

The preferred steady state is option 1. A separate platform pipeline builds each adapter after either its source Dockerfile, the certificate manifest, or the pinned upstream digest changes. It scans the resulting image, signs it, and publishes an immutable digest to the approved internal registry. Application builds consume the digest, not a mutable tag.

CI must never receive a developer workstation path. The enterprise certificate must be delivered from an approved PKI source or a protected CI secret, staged only for the adapter build, and verified against the committed manifest. The CA certificate is not logged.

## Versioning, Updates, and Rotation

- Replace mutable upstream references such as `latest` with pinned digests for every adapter source before production adoption.
- Image tags include the upstream version and the first 12 hexadecimal characters of the CA fingerprint. The complete fingerprint is stored in an OCI label.
- Dependabot or a scheduled workflow opens a pull request when an upstream adapter base changes. That pull request updates the adapter input, test evidence, and any digest references together.
- CA rotation changes `certificate-manifest.env`, adapter tag/digest, and validation evidence in one pull request.
- During a planned overlap, support two approved CA aliases in the adapter, document their fingerprints, and remove the old alias only after enterprise PKI confirms retirement.
- Revoke a compromised CA by removing its adapter image from the registry, updating the manifest, rebuilding consumers against the new digest, and invalidating affected local image tags.

## Security Controls

- The certificate subject and SHA-256 fingerprint are allowlisted in version control; arbitrary local certificates are rejected.
- The certificate is injected only into builders that need it. Elasticsearch's final runtime uses a clean vendor base plus the installed plugin; Java application runtime stages use a clean runtime-assembly stage.
- Trusted image builds run as root only while writing system/JDK trust stores and restore the expected unprivileged user afterward.
- No proxy credentials, certificate private keys, or trust-store passwords are committed. The standard Java `cacerts` password is not treated as a secret; it is used only to import the public CA.
- Adapter images are scanned and signed before shared-registry publication.
- Logs record image digest, upstream digest, certificate fingerprint, and verification outcome, but never the PEM body.
- Build caches are invalidated when the CA fingerprint changes so old trust layers cannot be reused accidentally.

## Implementation Plan

1. Confirm ownership and approved distribution of the `CDC-G2` CA; record the reviewed fingerprint in `certificate-manifest.env`.
2. Add the trusted-builder directory, README, manifest, local build script, and verification script.
3. Implement and test the Gradle/JDK 21 adapter using the `io.cucumber:query` metadata resolution failure as the acceptance case.
4. Add `GRADLE_BUILDER_IMAGE` arguments to the three Gradle application Dockerfiles and root Compose services. Refactor their clean runtime-assembly stages and confirm the final runtime has no `cdc-g2` alias.
5. Implement and test the Elasticsearch adapter; refactor the Elasticsearch Dockerfile into plugin-builder and clean runtime stages. Confirm `analysis-phonetic` is listed while the final runtime JDK has no enterprise CA alias.
6. Integrate the idempotent local helper into the documented `cdc-sandbox/build_all.sh` path and the direct Compose developer path.
7. Update `cdc-sandbox/doc/build.md` with certificate prerequisites, the local build command, failure diagnosis, and image-cache cleanup instructions.
8. Coordinate the approved CI integration with `CDCgov/NEDSS-Workflows`, then publish signed trusted adapters by immutable digest.
9. Add the UBI/uv and Node adapters only after an observed TLS failure or an explicit policy decision to standardize all outbound build clients.

## Validation and Acceptance Criteria

### Adapter validation

- `openssl x509` confirms the supplied CA subject and SHA-256 fingerprint match the manifest.
- The Gradle adapter lists `cdc-g2` with `keytool -list -cacerts` and resolves `https://packages.confluent.io/maven/io/cucumber/query/maven-metadata.xml` through Gradle without an SSL handshake exception.
- The Elasticsearch adapter lists `cdc-g2` with `/usr/share/elasticsearch/jdk/bin/keytool -list -cacerts` and installs `analysis-phonetic` without an SSL handshake exception.
- Verification commands fail if the certificate is absent, malformed, or has the wrong fingerprint.

### Application validation

- `docker compose -f cdc-sandbox/docker-compose.yml build elasticsearch` succeeds on the CDC network.
- `docker compose -f cdc-sandbox/docker-compose.yml up elasticsearch --build -d` starts Elasticsearch and `/_cat/plugins` reports `analysis-phonetic`.
- `docker compose -f cdc-sandbox/docker-compose.yml build modernization-api nbs-gateway pagebuilder-api` succeeds on the CDC network.
- `cdc-sandbox/build_all.sh` completes its build phase with the trusted-image helper enabled.
- Final application runtime images and final Elasticsearch image do not contain the `cdc-g2` certificate alias unless a documented runtime outbound TLS requirement exists.
- Builds without `NEDSS_TRUSTED_CA_FILE` retain the current behavior outside the intercepted network.

### Negative tests

- Set `NEDSS_TRUSTED_CA_FILE` to an unrelated public CA and verify the helper stops before invoking Docker builds.
- Remove the trusted base image and confirm the helper rebuilds it from the verified certificate.
- Use a stale trusted-image tag after a manifest fingerprint change and confirm the helper rejects or rebuilds it.

## Rollback

The selector arguments make rollback low risk:

1. Unset `NEDSS_GRADLE_BUILDER_IMAGE` and `NEDSS_ELASTICSEARCH_PLUGIN_BUILDER_IMAGE`, or set them to the documented upstream defaults.
2. Rebuild the affected service.
3. Remove only the explicit `nedss-local/trusted-*` image tags if cache cleanup is required.

Do not remove upstream images, application images, volumes, or databases as part of this rollback. If a published adapter is faulty, repoint the consuming build argument to the previous signed immutable digest.

## Open Questions and Required Decisions

1. **Certificate ownership:** Which CDC PKI or security team owns the canonical `CDC-G2` CA download and rotation notice? The current local PEM is not an adequate shared source of truth.
2. **Certificate distribution:** Is the public enterprise CA permitted in this repository, or must it be supplied from a protected CI secret/internal PKI endpoint? The design defaults to external supply plus fingerprint verification.
3. **CI integration:** Does `CDCgov/NEDSS-Workflows` already support Docker build arguments and prebuilt internal base images? If not, which team will add and maintain that capability?
4. **Registry:** Which internal registry/repository should store signed trusted adapters, and what retention and vulnerability-scanning policies apply?
5. **Scope:** Should the first release cover only Gradle and Elasticsearch, or should UBI/uv and Node adapters be built and validated proactively?
6. **Runtime trust policy:** Do any deployed Java, Elasticsearch, Python, or Node services need the enterprise CA at runtime? If yes, identify the endpoints and authorize an exception to the build-stage-only default.
7. **Pinning policy:** May this work replace the current mutable `latest` image references with immutable upstream digests as part of the same implementation?
