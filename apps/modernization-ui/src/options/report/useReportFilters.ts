import { cachedSelectableResolver, useSelectableOptions } from 'options';

const resolver = cachedSelectableResolver('report.filters.options', '/nbs/api/options/report/filters');

const useReportFilters = () => {
    const { options } = useSelectableOptions({ resolver });
    return options;
};

export { useReportFilters };
