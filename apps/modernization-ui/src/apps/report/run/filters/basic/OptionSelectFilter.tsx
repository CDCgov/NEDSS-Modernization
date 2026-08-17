import { useEffect } from 'react';

import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelect } from 'design-system/select';
import { BasicFilterConfiguration } from 'generated';
import { Selectable } from 'options';
import { useConceptOptions } from 'options/concepts';
import { useConditionOptions } from 'options/condition';
import { useCountyOptions, useStateOptions } from 'options/location';
import { useStdHivWorkerNameOptions } from 'options/person';
import { validateRequiredRule } from 'validation/entry';

import { BasicFilterComponent, BasicFilterProps } from './BasicFilter';
import { useCurrentState } from './useCurrentState';

export const STATE_FILTER_CODE = 'J_S01';
export const COUNTY_FILTER_CODE = 'J_C01';
export const CONDITION_FILTER_CODE = 'C_D01';
export const DISEASE_FILTER_CODE = 'CVG_CUSTOM_N01';
export const STD_HIV_WORKERS_FILTER_CODE = 'STD_HIV_WRKR';

const OptionSelectFilter: BasicFilterComponent = ({ filter, value, onChange, ...remaining }: BasicFilterProps) => {
    const options = useFilterOptions(filter);

    useEffect(() => {
        // options have changed and the value is no longer in the option set
        if (
            value?.length > 0 &&
            options.length > 0 &&
            !value.every((v: string) => options.find((o) => o.value === v))
        ) {
            onChange(null);
        }
    }, [options, value, onChange]);

    if (filter.selectType === BasicFilterConfiguration.selectType.SINGLE) {
        return (
            <SingleSelect
                value={value?.[0] ?? undefined}
                onChange={(event) => onChange(event.target.value ? [event.target.value] : null)}
                options={options}
                {...remaining}
            />
        );
    } else {
        const multiOnChange = (values: Selectable[]) => {
            onChange(values.map((v) => v.value));
        };
        const multiValue = options.filter((selectable) => value?.includes(selectable.value)) ?? [];
        return <MultiSelect value={multiValue} onChange={multiOnChange} options={options} {...remaining} />;
    }
};

// county options depend on the currently selected state basic filter
const useCurrentStateCountyOptions = () => {
    const state = useCurrentState();
    const { options, load } = useCountyOptions();

    useEffect(() => {
        load(state);
    }, [state, load]);

    return options;
};

const useDiseaseOptions = (filterCodeSetName: string) => {
    const { options } = useConceptOptions(filterCodeSetName, { lazy: false });
    return options;
};

const OPTIONS_HOOK_MAP: Record<string, (filterCodeSetName: string) => Selectable[]> = {
    [COUNTY_FILTER_CODE]: useCurrentStateCountyOptions,
    [STATE_FILTER_CODE]: useStateOptions,
    [CONDITION_FILTER_CODE]: () => {
        return useConditionOptions().options;
    },
    [DISEASE_FILTER_CODE]: (filterCodeSetName) => useDiseaseOptions(filterCodeSetName),
    [STD_HIV_WORKERS_FILTER_CODE]: useStdHivWorkerNameOptions,
};

const useFilterOptions = (filter: BasicFilterConfiguration): Selectable[] => {
    const filterCodeFull = filter?.filterType?.code ?? ''; // should never be empty in practice
    // ignore include nulls indicator here
    const filterCode = filterCodeFull.endsWith('_N') ? filterCodeFull.slice(0, -2) : filterCodeFull;
    const options = OPTIONS_HOOK_MAP[filterCode]?.(filter?.filterType?.codeSetName ?? '') ?? [];

    return options;
};

const getValueList = (filter: BasicFilterConfiguration) => {
    if (!filter.defaultValues || filter.defaultValues.length === 0) return null;

    return filter.defaultValues;
};

const optionSelectValidator = (filter: BasicFilterConfiguration, label: string) => {
    return (value?: (string | undefined)[]) => {
        // Base required check doesn't work well with lists
        if (!value || !value.length) {
            return filter.isRequired ? validateRequiredRule(label).required.message : true;
        }

        return true;
    };
};

export { OptionSelectFilter, getValueList, optionSelectValidator };
