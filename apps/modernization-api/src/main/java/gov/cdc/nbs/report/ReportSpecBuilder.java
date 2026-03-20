package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.exception.BadRequestException;
import gov.cdc.nbs.report.models.ReportSpec;
import gov.cdc.nbs.repository.DataSourceColumnRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ReportSpecBuilder {
  private final DataSourceColumnRepository dataSourceColumnRepository;

  private String selectQuery;

  public ReportSpecBuilder(final DataSourceColumnRepository dataSourceColumnRepository) {
    this.dataSourceColumnRepository = dataSourceColumnRepository;
  }

  public ReportSpecBuilder addColumns(List<Long> columnUids) {
    if (columnUids.isEmpty()) {
      throw new BadRequestException("No column UIDs specified");
    }

    List<DataSourceColumn> columns = dataSourceColumnRepository.findAllById(columnUids);
    if (columns.size() != columnUids.size()) {
      throw new IllegalArgumentException("One or more of the columns provided is invalid");
    }

    Map<String, String> map =
        columns.stream()
            .collect(
                Collectors.toMap(
                    DataSourceColumn::getColumnName, DataSourceColumn::getColumnTitle));

    selectQuery =
        "SELECT "
            + columns.stream()
                .map(column -> column.getColumnName() + " AS " + column.getColumnTitle())
                .collect(Collectors.joining(", "));

    return this;
  }

  public ReportSpec build() {
    return new ReportSpec(
        1,
        true,
        true,
        "Test Report",
        "nbs_custom",
        "nbs_rdb.investigation",
        selectQuery + " FROM [NBS_ODSE].[dbo].[NBS_configuration]",
        null);
  }
}
