package gov.cdc.nbs.patientlistener.request;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "kafkadef.topics.patient")
public record PatientRequestProperties(

    String request,
    String status
) {

}
