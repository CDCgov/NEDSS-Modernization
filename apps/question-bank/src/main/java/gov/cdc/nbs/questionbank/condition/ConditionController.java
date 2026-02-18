package gov.cdc.nbs.questionbank.condition;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.condition.model.Condition;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.condition.request.ReadConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.ConditionStatusResponse;
import java.util.List;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/conditions/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class ConditionController {
  private final ConditionCreator conditionCreator;
  private final ConditionReader conditionReader;
  private final UserDetailsProvider userDetailsProvider;
  private final ConditionStatus conditionStatus;
  private final ConditionSearcher searcher;

  public ConditionController(
      final ConditionCreator conditionCreator,
      final ConditionReader conditionReader,
      final UserDetailsProvider userDetailsProvider,
      final ConditionStatus conditionStatus,
      final ConditionSearcher searcher) {
    this.conditionCreator = conditionCreator;
    this.conditionReader = conditionReader;
    this.userDetailsProvider = userDetailsProvider;
    this.conditionStatus = conditionStatus;
    this.searcher = searcher;
  }

  @PostMapping
  public Condition createCondition(@RequestBody CreateConditionRequest request) {
    Long userId = userDetailsProvider.getCurrentUserDetails().getId();
    return conditionCreator.createCondition(request, userId);
  }

  @GetMapping("all")
  public List<Condition> findAllConditions() {
    return conditionReader.findAllConditions();
  }

  @GetMapping("available")
  public List<Condition> findConditionsNotInUse(@RequestParam final Optional<Long> page) {
    return searcher.findAvailable(page.orElse(null));
  }

  @GetMapping
  public Page<Condition> findConditions(
      @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
    return conditionReader.findConditions(pageable);
  }

  @PostMapping("/search")
  public Page<Condition> searchConditions(
      @RequestBody ReadConditionRequest search,
      @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
    return searcher.search(search, pageable);
  }

  @PatchMapping("/{id}")
  @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
  public ConditionStatusResponse activateCondition(@PathVariable String id) {
    return conditionStatus.activateCondition(id);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
  public ConditionStatusResponse inactivateCondition(@PathVariable String id) {
    return conditionStatus.inactivateCondition(id);
  }
}
