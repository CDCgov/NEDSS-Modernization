package gov.cdc.nbs.dibbs.nbs_deduplication.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.DedupMatchResponse;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.DedupMatchResponse.MatchType;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.PersonContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;


@Component
@Slf4j
@RequiredArgsConstructor
public class DibbsMatchService {

  @Value("${linkageServiceUrl}")
  String linkageServiceUrl;

  private final FhirConverter fhirConverter;
  private final ObjectMapper mapper;


  public ResponseEntity<DedupMatchResponse> match(PersonContainer personContainer)
      throws InterruptedException {
    try {
      return callRecordLinkageApi(personContainer);
    } catch (InterruptedException e) {
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  public ResponseEntity<DedupMatchResponse> callRecordLinkageApi(PersonContainer personContainer)
      throws IOException, InterruptedException {
    String requestJson = fhirConverter.convertPersonToFhirFormat(personContainer);

    HttpClient client = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_1_1)
        .build();
    HttpRequest request = createHttpRequest(requestJson);
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    return handleResponse(response);
  }

  HttpRequest createHttpRequest(String requestJson) {
    return HttpRequest.newBuilder()
        .uri(URI.create(linkageServiceUrl))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(requestJson, StandardCharsets.UTF_8))
        .build();
  }

  public ResponseEntity<DedupMatchResponse> handleResponse(HttpResponse<String> response) throws IOException {
    log.info(response.body());
    if (response.statusCode() == 200) {
      JsonNode jsonResponse = mapper.readTree(response.body());
      String foundMatchStr = jsonResponse.get("found_match").asText();
      String needHumanReview = jsonResponse.get("human_review_flag").asText();
      String patientId = getPatientId(jsonResponse);
      MatchType matchType = getMatchType(Boolean.parseBoolean(foundMatchStr), Boolean.parseBoolean(needHumanReview));
      return ResponseEntity.ok(new DedupMatchResponse(patientId, matchType));
    }
    return ResponseEntity.status(response.statusCode()).build();
  }

  private MatchType getMatchType(boolean matchFound, boolean needHumanReview) {
    if (matchFound) {
      return needHumanReview ? MatchType.HUMAN_REVIEW : MatchType.EXACT;
    }
    return MatchType.NONE;
  }

  private String getPatientId(JsonNode jsonNode) {
    JsonNode updatedBundleNode = jsonNode.get("updated_bundle");
    if (updatedBundleNode != null) {
      JsonNode entryArray = updatedBundleNode.get("entry");
      if (entryArray != null && entryArray.isArray()) {
        for (JsonNode entryNode : entryArray) {
          JsonNode resourceNode = entryNode.get("resource");
          if (resourceNode != null && "Person".equals(resourceNode.get("resourceType").asText())) {
            return resourceNode.get("id").asText();
          }
        }
      }
    }
    return null;
  }



}
