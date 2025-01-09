package gov.cdc.nbs.patient;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
class NBS6PatientFileRedirection {

  private final NBS6PatientFileRedirectResolver resolver;

  NBS6PatientFileRedirection(final NBS6PatientFileRedirectResolver resolver) {
    this.resolver = resolver;
  }

  @GetMapping("/nbs/api/patient/{patient}/file/redirect")
  ResponseEntity<Void> profile(@PathVariable final long patient) {
    return resolver.resolve(patient);
  }

}
