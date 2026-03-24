package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.exception.BadRequestException;
import gov.cdc.nbs.report.models.ReportSpec;
import gov.cdc.nbs.repository.DataSourceColumnRepository;
import java.util.List;
import java.util.stream.Collectors;
import gov.cdc.nbs.report.models.ReportSpec;
import org.springframework.stereotype.Component;

@Component
public class ReportSpecBuilder {
  private final DataSourceColumnRepository dataSourceColumnRepository;
  private List<DataSourceColumn> columns;

  @SuppressWarnings("FieldCanBeLocal")
  private String selectClause;

  @SuppressWarnings("FieldCanBeLocal")
  private String fromClause;

  @SuppressWarnings("FieldCanBeLocal")
  private String whereClause;

  @SuppressWarnings("FieldCanBeLocal")
  private String orderByClause;

  public ReportSpecBuilder(final DataSourceColumnRepository dataSourceColumnRepository) {
    this.dataSourceColumnRepository = dataSourceColumnRepository;
  }

  public ReportSpecBuilder addColumns(List<Long> columnUids) {
    if (columnUids.isEmpty()) {
      throw new BadRequestException("No column UIDs specified");
    }

    List<DataSourceColumn> dataSourceColumns = dataSourceColumnRepository.findAllById(columnUids);
    if (dataSourceColumns.size() != columnUids.size()) {
      throw new IllegalArgumentException("One or more of the columns provided is invalid");
    }

    columns = dataSourceColumns;
    return this;
  }

  public ReportSpec build() {
    selectClause =
        "SELECT "
            + columns.stream()
                .map(column -> column.getColumnName() + " AS " + column.getColumnTitle())
                .collect(Collectors.joining(", "));
    fromClause = "FROM [NBS_ODSE].[dbo].[NBS_configuration]";
    whereClause = "";
    orderByClause = "";

    String subsetQuery =
        String.join(" ", selectClause, fromClause, whereClause, orderByClause).trim();

    return new ReportSpec(
        1, true, true, "Test Report", "nbs_custom", "nbs_rdb.investigation", subsetQuery, null);
  }
}
