# Dockerfile to build elasticsearch image
FROM dhi.io/elasticsearch:9.2

# Relaxes security 
ENV xpack.security.enabled=false
ENV xpack.security.transport.ssl.enabled=false
ENV xpack.security.http.ssl.enabled=false

# sets node to single
ENV discovery.type=single-node

RUN /usr/share/elasticsearch/bin/elasticsearch-plugin install analysis-phonetic
