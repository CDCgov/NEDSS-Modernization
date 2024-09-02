package gov.cdc.nbs.dibbs.nbs_deduplication.controller;

import gov.cdc.nbs.dibbs.nbs_deduplication.model.DedupMatchResponse;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.PersonContainer;
import gov.cdc.nbs.dibbs.nbs_deduplication.service.DibbsMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

  private final DibbsMatchService dibbsMatchService;

  @PostMapping("/match")
  public ResponseEntity<DedupMatchResponse> match(@RequestBody PersonContainer personContainer)
      throws InterruptedException {
    return dibbsMatchService.match(personContainer);
  }



}
