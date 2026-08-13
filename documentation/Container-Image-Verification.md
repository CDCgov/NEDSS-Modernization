# Container Image Verification

Container images published by this project include a **SLSA v0.2 provenance
attestation** — a machine-readable record of how the image was built, attached
as an OCI referrer. The attestation can be used to verify that the image was
built by the project's CI pipeline and hasn't been tampered with.

For background on the format, see the [SLSA Provenance v0.2 specification](https://slsa.dev/provenance/v0.2)
and [Docker BuildKit attestations documentation](https://docs.docker.com/build/metadata/attestations/).

## Inspecting the provenance

Requires Docker with `buildx` (Docker Desktop 4.x+, Docker Engine 23.0+). Public
Quay.io repositories allow anonymous read, so no login is required.

```sh
docker buildx imagetools inspect \
  quay.io/us-cdcgov/cdc-nbs-modernization/<repo>:<tag> \
  --format '{{ json .Provenance }}'
```

## What to expect

A populated JSON object with `predicateType: "https://slsa.dev/provenance/v0.2"`
and a `predicate` block containing:

- `buildDefinition.externalParameters` — the workflow file, git ref, and commit
  that produced the image
- `buildDefinition.resolvedDependencies` — the base image, pinned by content
  digest
- `runDetails.metadata` — build timestamps and invocation ID
- The Dockerfile embedded as base64 (when built with `mode=max`)

An empty response (`{}`) indicates the attestation is missing or was stripped
in transit. Investigate before trusting the image.

## Image structure

Tags published with provenance appear as **OCI Image Indexes** rather than
plain image manifests. Each tag references two artifacts: the container image
itself (`linux/amd64`) and an attestation manifest (`unknown/unknown` platform,
~20 KB). This is transparent to Docker, containerd, and Kubernetes runtimes —
`docker pull` and `kubectl` behave identically regardless of shape.

## Troubleshooting

| Symptom | Cause | Fix |
|---|---|---|
| `.Provenance` returns `{}` | Attestation never attached, or stripped by registry | Check the workflow run log for `exporting attestation manifest`. If present, the registry stripped it — investigate. |
| Attestation missing after copying image between registries | Copy tool only handles single manifests | Use `docker buildx imagetools create` or `skopeo copy --all` |