FROM maven:3-eclipse-temurin-8 AS nbs-source

ARG ant_version=1.10.14

# install ant
WORKDIR /home
ADD https://dlcdn.apache.org/ant/binaries/apache-ant-$ant_version-bin.tar.gz .
RUN tar -zxvf apache-ant-$ant_version-bin.tar.gz && rm apache-ant-$ant_version*.gz
ENV ANT_HOME=/home/apache-ant-$ant_version

# copy NBS source code
ADD builder/NEDSSDev ./NEDSSDev/

# Massage NEDSS source code
ENV NEDSS_HOME=/home/NEDSSDev
ADD builder/dependencies.xml .
RUN sed -i 's/PhcMartEtl.bat/PHCMartETL.bat/gi' $NEDSS_HOME/pom.xml
RUN sed -i "s#</dependencies>#$(cat dependencies.xml)</dependencies>#g" $NEDSS_HOME/pom-jar.xml
RUN cp $NEDSS_HOME/source/gov/cdc/nedss/webapp/nbs/resource/javascript/Globals.js $NEDSS_HOME/source/gov/cdc/nedss/webapp/nbs/resource/javascript/globals.js

WORKDIR $NEDSS_HOME

# Clean container dist directory
RUN rm -rf build

ENV JBOSS_HOME=build
ENV JAVA_TOOL_OPTIONS=-Dfile.encoding=ISO-8859-1
RUN /usr/share/maven/bin/mvn package -DskipTests


FROM alpine:latest AS files
ADD https://download.jboss.org/wildfly/10.0.0.Final/wildfly-10.0.0.Final.tar.gz .
ADD ./pagemanagement.zip .

RUN apk add unzip && \
    mkdir /wildfly && tar -zxvf wildfly-10.0.0.Final.tar.gz --directory /wildfly && \
    mkdir /pagemanagement && unzip "./pagemanagement.zip" -d /pagemanagement

FROM eclipse-temurin:8-jdk-ubi9-minimal
#===============================================================================================================
# Set WildFly and NEDSS Specific Environment Variables
#===============================================================================================================

ENV JBOSS_HOME=/opt/jboss/wildfly-10.0.0.Final
ENV JBOSS_BASE_DIR=${JBOSS_HOME}/nedssdomain
ENV JBOSS_CONFIG_DIR=${JBOSS_BASE_DIR}/configuration
ENV NEDSS_DIR=${JBOSS_BASE_DIR}/Nedss
ENV JBOSS_ENDORSED_DIRS=${JBOSS_BASE_DIR}/lib/Endorsed

# This is hardcoded in as log dump, so keeping this path absolute
ENV JBOSS_LOG_DIR=${JBOSS_BASE_DIR}/log

#===============================================================================================================
# Set up directories
#===============================================================================================================
RUN mkdir /log && \
    chmod a+w log/ && \
    touch /log/nedss.log && \
    mkdir -p $NEDSS_DIR

COPY --from=files /wildfly /opt/jboss
COPY "./config/wildfly" "${JBOSS_HOME}/nedssdomain/configuration"
COPY --from=nbs-source /home/NEDSSDev/build/ "${JBOSS_HOME}"
COPY --from=files /pagemanagement "${JBOSS_HOME}/nedssdomain/Nedss/"

WORKDIR ${JBOSS_HOME}

RUN chmod +x bin/standalone.sh

CMD ["./bin/standalone.sh", "-b", "0.0.0.0","--debug"]
