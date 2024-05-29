package gov.cdc.nbs.gateway.classic.report;

import org.springframework.http.ResponseCookie;

/**
 * The report selected by the user to be run.
 *
 * @param report The unique name of the path that identifies the report.
 */
public record NBSReportCookie(String report) {

  private static final NBSReportCookie EMPTY = new NBSReportCookie("");

  public static final String NAME = "NBS-Report";

  public static NBSReportCookie empty() {
    return EMPTY;
  }

  public ResponseCookie toResponseCookie() {
    ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from(NAME, report())
        .httpOnly(true)
        .secure(true)
        .path("/nbs")
        .sameSite("Strict");

    if (report().isEmpty()) {
      builder.maxAge(0);
    }

    return builder
        .build();
  }
}
