package gov.cdc.nbs.patient.profile.investigation.delete;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
class DeletedInvestigationResponseHandler {

  Optional<ResponseEntity<Void>> handle(final ResponseEntity<Void> response) {

    if (response.getStatusCode().is3xxRedirection()) {
      URI location = response.getHeaders().getLocation();

      if (location != null) {

        URI redirected = UriComponentsBuilder.fromPath("/")
            .path(location.getPath())
            .query(location.getQuery())
            .build()
            .toUri();

        return Optional.of(
            ResponseEntity.status(HttpStatus.FOUND)
                .location(redirected)
                .build()
        );
      }

    }
    return Optional.empty();
  }
}
