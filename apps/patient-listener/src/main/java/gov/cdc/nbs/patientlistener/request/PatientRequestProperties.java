package gov.cdc.nbs.patientlistener.request;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The properties to configure the topics for patient requests
 * @param request
 * @param status
 */
@ConfigurationProperties(prefix = "kafkadef.topics.patient")
public record PatientRequestProperties(

    String request,
    String status
) {

  private static final String DEFAULT_REQUEST = "patient";
  private static final String DEFAULT_STATUS = "patient-status";

  public PatientRequestProperties() {
    this(DEFAULT_REQUEST, DEFAULT_STATUS);
  }

  public PatientRequestProperties(String request, String status) {
    this.request = request == null ? DEFAULT_REQUEST : request;
    this.status = status == null ? request + DEFAULT_STATUS : status;
  }
}
