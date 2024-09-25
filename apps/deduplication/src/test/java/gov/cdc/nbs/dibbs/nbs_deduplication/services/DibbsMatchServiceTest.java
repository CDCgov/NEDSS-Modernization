package gov.cdc.nbs.dibbs.nbs_deduplication.services;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.DedupMatchResponse;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.PersonContainer;
import gov.cdc.nbs.dibbs.nbs_deduplication.service.DibbsMatchService;
import gov.cdc.nbs.dibbs.nbs_deduplication.service.FhirConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class DibbsMatchServiceTest {

  @Mock
  private FhirConverter fhirConverter;

  @Mock
  private ObjectMapper objectMapper;

  @InjectMocks
  @Spy
  private DibbsMatchService dibbsMatchService;

  @Mock
  private HttpResponse<String> httpResponse;

  private final String linkageServiceUrl = "http://example.com/";

  String jsonResponse = """
      {
            "found_match": true,
            "updated_bundle": {
                "resourceType": "Bundle",
                "entry": [
                    {
                        "resource": {
                            "resourceType": "Patient",
                            "name": [
                                {
                                    "family": "Mann",
                                    "given": [
                                        "Valerie"
                                    ]
                                }
                            ],
                            "address": [
                                {
                                    "line": [
                                        "18812 Joshua Fall",
                                        ""
                                    ],
                                    "city": "Gentryville",
                                    "state": "MO",
                                    "postalCode": "99832",
                                    "country": "USA"
                                }
                            ],
                            "birthDate": "1938-08-05"
                        }
                    },
                    {
                        "fullUrl": "urn:uuid:8dda4442-f206-46cc-a6ff-d9f72f9da966",
                        "resource": {
                            "resourceType": "Person",
                            "id": "8dda4442-f206-46cc-a6ff-d9f72f9da966",
                            "link": [
                                {
                                    "target": {
                                        "reference": "Patient/"
                                    }
                                }
                            ]
                        },
                        "request": {
                            "method": "PUT",
                            "url": "Person/8dda4442-f206-46cc-a6ff-d9f72f9da966"
                        }
                    }
                ]
            },
            "message": "",
            "human_review_flag": "False"
        }
        """;


  @BeforeEach
  public void setUp() {
    ReflectionTestUtils.setField(dibbsMatchService, "linkageServiceUrl", linkageServiceUrl);
  }

  @Test
  void testMatchExact() throws Exception {
    PersonContainer personContainer = new PersonContainer();
    when(fhirConverter.convertPersonToFhirFormat(any())).thenReturn(jsonResponse);
    JsonNode jsonNode = new ObjectMapper().readTree(jsonResponse);
    when(objectMapper.readTree(any(String.class))).thenReturn(jsonNode);
    when(httpResponse.statusCode()).thenReturn(200);
    when(httpResponse.body()).thenReturn(jsonResponse);
    doReturn(httpResponse).when(dibbsMatchService).sendRequest(any(HttpClient.class), any(HttpRequest.class));

    ResponseEntity<DedupMatchResponse> response = dibbsMatchService.match(personContainer);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("8dda4442-f206-46cc-a6ff-d9f72f9da966", response.getBody().patient());
    assertEquals(DedupMatchResponse.MatchType.EXACT, response.getBody().matchType());
  }

  @Test
  void testMatchHumanReview() throws Exception {
    PersonContainer personContainer = new PersonContainer();
    String humanReviewJsonResponse = this.jsonResponse.
        replace("\"human_review_flag\": \"False\"", "\"human_review_flag\": \"True\"");
    when(fhirConverter.convertPersonToFhirFormat(any())).thenReturn(humanReviewJsonResponse);
    JsonNode jsonNode = new ObjectMapper().readTree(humanReviewJsonResponse);
    when(objectMapper.readTree(any(String.class))).thenReturn(jsonNode);
    when(httpResponse.statusCode()).thenReturn(200);
    when(httpResponse.body()).thenReturn(humanReviewJsonResponse);
    doReturn(httpResponse).when(dibbsMatchService).sendRequest(any(HttpClient.class), any(HttpRequest.class));

    ResponseEntity<DedupMatchResponse> response = dibbsMatchService.match(personContainer);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("8dda4442-f206-46cc-a6ff-d9f72f9da966", response.getBody().patient());
    assertEquals(DedupMatchResponse.MatchType.HUMAN_REVIEW, response.getBody().matchType());
  }

  @Test
  void testMatchNone() throws Exception {
    PersonContainer personContainer = new PersonContainer();
    String noMatchJsonResponse = this.jsonResponse.
        replace("\"found_match\": true", "\"found_match\": false");
    when(fhirConverter.convertPersonToFhirFormat(any())).thenReturn(noMatchJsonResponse);
    JsonNode jsonNode = new ObjectMapper().readTree(noMatchJsonResponse);
    when(objectMapper.readTree(any(String.class))).thenReturn(jsonNode);
    when(httpResponse.statusCode()).thenReturn(200);
    when(httpResponse.body()).thenReturn(noMatchJsonResponse);
    doReturn(httpResponse).when(dibbsMatchService).sendRequest(any(HttpClient.class), any(HttpRequest.class));

    ResponseEntity<DedupMatchResponse> response = dibbsMatchService.match(personContainer);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("8dda4442-f206-46cc-a6ff-d9f72f9da966", response.getBody().patient());
    assertEquals(DedupMatchResponse.MatchType.NONE, response.getBody().matchType());
  }


  void testMatchNullPatient(String noMatchJsonResponse) throws Exception {
    PersonContainer personContainer = new PersonContainer();
    when(fhirConverter.convertPersonToFhirFormat(any())).thenReturn(noMatchJsonResponse);
    JsonNode jsonNode = new ObjectMapper().readTree(noMatchJsonResponse);
    when(objectMapper.readTree(any(String.class))).thenReturn(jsonNode);
    when(httpResponse.statusCode()).thenReturn(200);
    when(httpResponse.body()).thenReturn(noMatchJsonResponse);
    doReturn(httpResponse).when(dibbsMatchService).sendRequest(any(HttpClient.class), any(HttpRequest.class));
    ResponseEntity<DedupMatchResponse> response = dibbsMatchService.match(personContainer);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNull(response.getBody().patient());
    assertEquals(DedupMatchResponse.MatchType.NONE, response.getBody().matchType());
  }


  @Test
  void testMatchNullBundle() throws Exception {
    String noMatchJsonResponse = "{\"found_match\": false,\"message\": \"\",\"human_review_flag\": \"False\"}";
    testMatchNullPatient(noMatchJsonResponse);
  }

  @Test
  void testMatchNullEntry() throws Exception {
    String noMatchJsonResponse = "{\"found_match\": false,\"updated_bundle\": " +
        "{\"resourceType\": \"Bundle\" },\"message\": \"\",\"human_review_flag\": \"False\"}";
    testMatchNullPatient(noMatchJsonResponse);
  }

  @Test
  void testMatchNullResource() throws Exception {
    String noMatchJsonResponse = "{\"found_match\": false,\"updated_bundle\": " +
        "{\"resourceType\": \"Bundle\",\"entry\": [{ \"NotResource\": {}}]},\"message\": \"\",\"human_review_flag\": \"False\" }";
    testMatchNullPatient(noMatchJsonResponse);
  }

  @Test
  void testMatchBadEntryElement() throws Exception {
    PersonContainer personContainer = new PersonContainer();
    String noMatchJsonResponse = "{\"found_match\": false,\"updated_bundle\": " +
        "{\"resourceType\": \"Bundle\",\"entry\": {}},\"message\": \"\",\"human_review_flag\": \"False\" }";
    when(fhirConverter.convertPersonToFhirFormat(any())).thenReturn(noMatchJsonResponse);
    JsonNode jsonNode = new ObjectMapper().readTree(noMatchJsonResponse);
    when(objectMapper.readTree(any(String.class))).thenReturn(jsonNode);
    when(httpResponse.statusCode()).thenReturn(200);
    when(httpResponse.body()).thenReturn(noMatchJsonResponse);
    doReturn(httpResponse).when(dibbsMatchService).sendRequest(any(HttpClient.class), any(HttpRequest.class));
    ResponseEntity<DedupMatchResponse> response = dibbsMatchService.match(personContainer);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNull(response.getBody().patient());
    assertEquals(DedupMatchResponse.MatchType.NONE, response.getBody().matchType());
  }

  @Test
  void testMatchBadResourceType() throws Exception {
    PersonContainer personContainer = new PersonContainer();
    String noMatchJsonResponse = this.jsonResponse
        .replace("\"found_match\": true", "\"found_match\": false")
        .replace("\"resourceType\": \"Person\"", "\"resourceType\": \"badResourceType\"");
    when(fhirConverter.convertPersonToFhirFormat(any())).thenReturn(noMatchJsonResponse);
    JsonNode jsonNode = new ObjectMapper().readTree(noMatchJsonResponse);
    when(objectMapper.readTree(any(String.class))).thenReturn(jsonNode);
    when(httpResponse.statusCode()).thenReturn(200);
    when(httpResponse.body()).thenReturn(noMatchJsonResponse);
    doReturn(httpResponse).when(dibbsMatchService).sendRequest(any(HttpClient.class), any(HttpRequest.class));

    ResponseEntity<DedupMatchResponse> response = dibbsMatchService.match(personContainer);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNull(response.getBody().patient());
    assertEquals(DedupMatchResponse.MatchType.NONE, response.getBody().matchType());
  }

  @Test
  void testMatchInternalServerError() throws Exception {
    PersonContainer personContainer = new PersonContainer();
    when(fhirConverter.convertPersonToFhirFormat(any(PersonContainer.class))).thenThrow(
        new RuntimeException("RuntimeException"));
    ResponseEntity<DedupMatchResponse> response = dibbsMatchService.match(personContainer);
    assertNotNull(response);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }


  @Test
  void testHandleResponseErrorStatus() throws Exception {
    PersonContainer personContainer = new PersonContainer();
    when(fhirConverter.convertPersonToFhirFormat(any())).thenReturn(jsonResponse);
    when(httpResponse.statusCode()).thenReturn(500);
    when(httpResponse.body()).thenReturn(jsonResponse);
    doReturn(httpResponse).when(dibbsMatchService).sendRequest(any(HttpClient.class), any(HttpRequest.class));
    ResponseEntity<DedupMatchResponse> response = dibbsMatchService.match(personContainer);
    assertNotNull(response);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

}
