import { selectableResolver, useSelectableOptions } from 'options';

const resolver = () => selectableResolver('/nbs/api/options/report/datasources');

const useReportDataSources = () => {
    const { options } = useSelectableOptions({ resolver });
    return options;
};

export { useReportDataSources };
