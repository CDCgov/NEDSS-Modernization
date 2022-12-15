# Dockerfile to build nifi image
FROM apache/nifi:1.16.3

# Set environment variables
ENV NIFI_SENSITIVE_PROPS_KEY='12345678901234567890A'
ENV SINGLE_USER_CREDENTIALS_USERNAME=admin
ENV SINGLE_USER_CREDENTIALS_PASSWORD=fake.fake.fake.1234

COPY ./nifi_conf /opt/nifi/nifi-current/conf
COPY ./nifi_drivers /var/opt/nifi_drivers