# Dockerfile to build elasticsearch image
FROM docker.elastic.co/elasticsearch/elasticsearch:9.4.2

# Required for instal on CDC machines
# Other Dockerfiles have similar issues. Drafted a plan to fix which can be found here: documentation/Trusted-Container-Base-Images-Design.md
USER root
COPY /certs/DigiCertGlobalRootCA.crt.pem /etc/pki/ca-trust/source/anchors/DigiCertGlobalRootCA.crt.pem
COPY /certs/enterprise-bundle.crt /etc/pki/ca-trust/source/anchors/enterprise-bundle.crt

RUN update-ca-trust extract && \
    /usr/share/elasticsearch/jdk/bin/keytool -importcert -noprompt -trustcacerts \
      -alias enterprise-bundle \
      -file /etc/pki/ca-trust/source/anchors/enterprise-bundle.crt \
      -cacerts -storepass changeit

USER 1000

# Relaxes security and sets node to single
ENV xpack.security.enabled=false
ENV discovery.type=single-node

RUN /usr/share/elasticsearch/bin/elasticsearch-plugin install analysis-phonetic
