package gov.cdc.nbs.patient.events.report.morbidity;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import gov.cdc.nbs.patient.events.tests.ResultedTest;
import gov.cdc.nbs.patient.events.tests.ResultedTestResolver;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class MorbidityReportResultedTestResolver {

  private final AssociatedLabReportFinder associationFinder;
  private final ResultedTestResolver resultedTestResolver;

  MorbidityReportResultedTestResolver(
      final AssociatedLabReportFinder associationFinder,
      final ResultedTestResolver resultedTestResolver) {
    this.associationFinder = associationFinder;
    this.resultedTestResolver = resultedTestResolver;
  }

  public Map<Long, Collection<ResultedTest>> resolve(final Collection<Long> identifiers) {
    if (identifiers.isEmpty()) {
      return Collections.emptyMap();
    }

    //  find the morbidity report -> lab report association
    Multimap<Long, Long> associations = this.associationFinder.find(identifiers);

    if (associations.isEmpty()) {
      return Collections.emptyMap();
    }

    //  pass the identifiers of the lab reports to the resolver
    Collection<Long> labs = associations.values();

    Map<Long, Collection<ResultedTest>> tests = resultedTestResolver.resolve(labs);

    //  map the morbidity reports identifiers to the Resulted tests using the morbidity report ->
    // lab report association
    Multimap<Long, ResultedTest> resolved = ArrayListMultimap.create();

    for (Map.Entry<Long, Long> entry : associations.entries()) {

      Collection<ResultedTest> resultedTests = tests.get(entry.getValue());
      if (resultedTests != null && !resultedTests.isEmpty()) {
        resolved.putAll(entry.getKey(), resultedTests);
      }
    }

    return resolved.asMap();
  }
}
