import { selectableResolver, useSelectableOptions } from 'options';

const resolver = () => selectableResolver('/nbs/api/options/report/sections');

const useReportSections = () => {
    const { options } = useSelectableOptions({ resolver });
    return options;
};

export { useReportSections };
