# Dockerfile to build elasticsearch image
FROM opensearchproject/opensearch:2.4.1

# Relaxes security and sets node to single
ENV plugins.security.disabled=true
ENV discovery.type=single-node
