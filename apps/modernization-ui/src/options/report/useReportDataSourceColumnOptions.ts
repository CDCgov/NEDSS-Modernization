import { selectableResolver, SelectableOptionsInteraction, useSelectableOptions } from 'options';

const resolver = async (dataSource?: string | null) => {
    if (!dataSource) {
        return [];
    }
    return selectableResolver(`/nbs/api/options/report/datasource/${dataSource}/columns/filterable`);
};

const useReportDataSourceFilterableColumnOptions = (): SelectableOptionsInteraction<string> => {
    return useSelectableOptions({ resolver, lazy: true });
};

export { useReportDataSourceFilterableColumnOptions };
