import { cachedSelectableResolver, useSelectableOptions } from 'options';

const resolver = cachedSelectableResolver('report.sections.options', '/nbs/api/options/report/sections');

const useReportSections = () => {
    const { options } = useSelectableOptions({ resolver });
    return options;
};

export { useReportSections };
