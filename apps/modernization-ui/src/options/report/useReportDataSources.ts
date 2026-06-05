import { cachedSelectableResolver, useSelectableOptions } from 'options';

const resolver = cachedSelectableResolver('report.datasources.options', '/nbs/api/options/report/datasources');

const useReportDataSources = () => {
    const { options } = useSelectableOptions({ resolver });
    return options;
};

export { useReportDataSources };
