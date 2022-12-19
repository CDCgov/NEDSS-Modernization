# Dockerfile to build nifi image
FROM apache/nifi:1.16.3

# Set environment variables
ENV NIFI_SENSITIVE_PROPS_KEY='12345678901234567890A'
ENV SINGLE_USER_CREDENTIALS_USERNAME=admin
ENV SINGLE_USER_CREDENTIALS_PASSWORD=fake.fake.fake.1234

COPY ./nifi_drivers /var/opt/nifi_drivers
# COPY ./nifi_conf/conf /opt/nifi/nifi-current/conf

USER root

RUN chown -R nifi:nifi /var/opt/nifi_drivers
RUN chown -R nifi:nifi /opt/nifi

USER nifi