FROM maven:3.9-amazoncorretto-8 AS nbs-source

ARG ant_version=1.10.14

# update
RUN yum update -y
RUN yum install -y tar gzip hostname

# install ant
WORKDIR /home
ADD https://dlcdn.apache.org/ant/binaries/apache-ant-$ant_version-bin.tar.gz .
RUN tar -zxvf apache-ant-$ant_version-bin.tar.gz
RUN rm apache-ant-$ant_version*.gz
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

#Open JDK build from amazon https://docs.aws.amazon.com/corretto/latest/corretto-8-ug/docker-install.html
FROM amazoncorretto:8
# FROM ubuntu:latest

#===============================================================================================================
# Installs
#===============================================================================================================
RUN yum update -y && yum -y install xmlstarlet saxon augeas bsdtar unzip xz gzip tar shadow-utils emacs && yum clean all

# RUN apt-get update && apt-get install -y locales && rm -rf /var/lib/apt/lists/* \
# 	&& localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8

# RUN apt-get install -y xmlstarlet saxon augeas bsdtar unzip xz gzip tar shadow-utils
# RUN apt-get update
# RUN apt-get install -y software-properties-common 
# # RUN add-apt-repository ppa:webupd8team/java
# # RUN apt-get update
# RUN apt-get install -y openjdk-8-jdk

#===============================================================================================================
# Set Additional Environment Variables
#===============================================================================================================
# ENV LANG en_US.utf8

# ENV JAVA_HOME=/opt/jboss/jdk-1.8.0
ENV JBOSS_HOME=/opt/jboss/wildfly-10.0.0.Final
ENV JBOSS_BASE_DIR=${JBOSS_HOME}/nedssdomain
ENV JBOSS_CONFIG_DIR=${JBOSS_BASE_DIR}/configuration
ENV NEDSS_DIR=${JBOSS_BASE_DIR}/Nedss
ENV JBOSS_ENDORSED_DIRS=${JBOSS_BASE_DIR}/lib/Endorsed
# ENV nbs.dir=${NEDSS_DIR}

# This is hardcoded in as log dump, so keeping this path absolute
ENV JBOSS_LOG_DIR=${JBOSS_BASE_DIR}/log

# Create user (USER CURRENTLY NOT IN USE)
RUN groupadd -r jboss -g 1000 && useradd -u 1000 -r -g jboss -m -d /opt/jboss -c "JBoss user" jboss &&  chmod 755 /opt/jboss

#===============================================================================================================
# Set up directories
#===============================================================================================================
RUN mkdir /log
RUN chmod a+w log/
RUN touch /log/nedss.log
# RUN chown jboss:jboss /log/nedss.log

# Download wildfly and un-tar (SKIP in favor of pulling local, for future updating)
# USER jboss:jboss

# RUN curl https://download.jboss.org/wildfly/10.0.0.Final/wildfly-10.0.0.Final.tar.gz --output wildfly.tar.gz
# RUN tar -xzvf wildfly.tar.gz

#===============================================================================================================
# Download wildfly
#===============================================================================================================

RUN mkdir -p $JBOSS_HOME
WORKDIR ${JBOSS_HOME}/..
ADD https://download.jboss.org/wildfly/10.0.0.Final/wildfly-10.0.0.Final.tar.gz .
RUN tar -zxvf wildfly-10.0.0.Final.tar.gz

#===============================================================================================================
# Copy wildfly configuration
#===============================================================================================================

RUN mkdir "${JBOSS_HOME}/nedssdomain"
COPY "./config/wildfly" "${JBOSS_HOME}/nedssdomain/configuration"

#===============================================================================================================
# Copy web application from build dist to wildfly
#===============================================================================================================

COPY --from=nbs-source /home/NEDSSDev/build/ ${JBOSS_HOME}
ADD ./pagemanagement.zip ${NEDSS_DIR}/pagemanagement.zip 
RUN mkdir -p ${JBOSS_HOME}/nedssdomain/Nedss/
RUN unzip "${NEDSS_DIR}/pagemanagement.zip" -d "${JBOSS_HOME}/nedssdomain/Nedss/"


#===============================================================================================================
# FILE Modifications (Require Root Access due to file locations)
WORKDIR ${JBOSS_HOME}
# Create a copy for debugging and modify existing routes
RUN cp ./nedssdomain/configuration/standalone.xml ./nedssdomain/configuration/standalone-orginal.txt

# Replace nbs-mssql\\singhm with the route to your database (NOTE \\ is used to ensure a single \ is used)
RUN sed -i 's/localhost\\singhm/nbs-mssql\\singhm:1433/g' ./nedssdomain/configuration/standalone.xml

# A Bit messy, but add in required 
RUN sed -i '314i -Dnbs.dir=\\""\$NEDSS_DIR"\\" \\' ./bin/standalone.sh
#===============================================================================================================


# USER root
# RUN chown -R jboss:jboss .
RUN chmod +x bin/standalone.sh
# USER jboss:jboss

CMD ["./bin/standalone.sh", "-bmanagement", "0.0.0.0", "-b", "0.0.0.0","--debug"]
