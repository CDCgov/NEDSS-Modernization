import { cachedSelectableResolver, useSelectableOptions } from 'options';

const resolver = cachedSelectableResolver('report.libraries.options', '/nbs/api/options/report/libraries');

const useReportLibraries = () => {
    const { options } = useSelectableOptions({ resolver });
    return options;
};

export { useReportLibraries };
