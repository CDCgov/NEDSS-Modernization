import { cachedSelectableResolver } from 'options/cache/cachedSelectableResolver';
import { Selectable } from 'options/selectable';
import { SelectableOptionsInteraction, useSelectableOptions } from 'options/useSelectableOptions';

const resolver = (dataSource?: string | null) => {
    return dataSource
        ? cachedSelectableResolver(
              `report.datasource.columns.filterable.options.${dataSource}`,
              `/nbs/api/options/report/datasource/columns/filterable/${dataSource}`
          )()
        : Promise.resolve<Selectable[]>([]);
};

const useReportDataSourceFilterableColumnOptions = (): SelectableOptionsInteraction<string> => {
    return useSelectableOptions({ resolver, lazy: true });
};

export { useReportDataSourceFilterableColumnOptions };
