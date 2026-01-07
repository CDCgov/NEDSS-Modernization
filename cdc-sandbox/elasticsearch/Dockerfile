# Dockerfile to build elasticsearch image
FROM docker.elastic.co/elasticsearch/elasticsearch:7.17.7

# Relaxes security and sets node to single
ENV xpack.security.enabled=false
ENV discovery.type=single-node

RUN /usr/share/elasticsearch/bin/elasticsearch-plugin install analysis-phonetic
