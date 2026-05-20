import { useConfiguration } from 'configuration';
import { MultiSelect } from 'design-system/select';
import { LoadingIndicator } from 'libs/loading/indicator';
import { cachedSelectableResolver, Selectable } from 'options';
import { useEffect, useId, useState } from 'react';
import { FullField, ValueEditorProps } from 'react-querybuilder';
import { ValueSetMetadata } from './AdvancedFilter';
import { logErrorToUserConsole } from 'utils/logging';

const getValueSetMap = (state: string): Record<string, string> => {
    return {
        phc_type: '/conditions',
        condition_code: '/conditions',
        state_code: '/states',
        county_ccd: `/counties/${state}`,
        state_county_code_value: `/counties/${state}`,
        s_jurdic_c: '/jurisdictions',
        jurisdiction_code: '/jurisdictions',
        program_area_code: '/program-areas',
        s_progra_c: '/program-areas',
        naics_industry_code: '/occupations',
        language_code: '/language/primary',
        country_code: '/countries',
        place_list: '/places',
        race_code: '/races',
    };
};

const CODE_DESC_CD = {
    HARD_CODED: 'h',
    CODE: 'c',
    DESCRIPTION: 'd',
};

const ValueSetSelector = (props: ValueEditorProps<ValueSetMetadata & FullField>) => {
    const id = useId();
    const [options, setOptions] = useState<Selectable[] | null>(null);
    const { ready, properties } = useConfiguration();
    const { codeDescCd, codesetNm, columnUid } = props.schema.fieldMap[props.field] ?? {};

    useEffect(() => {
        const getValues = async (): Promise<Selectable[]> => {
            const valueSetMap = getValueSetMap(properties.entries.NBS_STATE_CODE);

            let cacheId = `report.valueset.${codesetNm ?? columnUid}`.toLowerCase();

            let endpoint = '';
            if (codeDescCd?.toLowerCase() === CODE_DESC_CD.HARD_CODED) {
                endpoint = `/report/distinct-values/${columnUid}`;
            } else if (codesetNm?.includes('.')) {
                const [table, valueSet] = codesetNm.split('.');
                if (table.toLowerCase() === 'code_value_general') {
                    endpoint = `/concepts/${valueSet}`;
                } else if (table.toLowerCase() === 'code_value_clinical') {
                    endpoint = `/clinical/concepts/${valueSet}`;
                } else {
                    logErrorToUserConsole(`unknown SRT table ${table}, returning no options for ${props.field}`);
                    return [];
                }
            } else if (codesetNm?.toLowerCase() ?? '' in valueSetMap) {
                endpoint = valueSetMap[codesetNm?.toLowerCase() ?? ''];
            }

            if (!endpoint) {
                logErrorToUserConsole(
                    `unable to determine value set for column ${columnUid} with codesetNm ${codesetNm}, 
                    returning no options for ${props.field} `
                );
                return [];
            }

            const options = await cachedSelectableResolver(cacheId, `/nbs/api/options${endpoint}`)();

            return options;
        };
        if (ready) {
            getValues()
                .then((res) => {
                    if ('options' in res) {
                        // the /concepts endpoint breaks the general pattern
                        setOptions(res.options as Selectable[]);
                    } else {
                        setOptions(res);
                    }
                })
                .catch((error) => {
                    logErrorToUserConsole({ error });
                    setOptions([]);
                });
        }
    }, [ready]);

    const getValue = (v: Selectable) => (codeDescCd?.toLowerCase() === CODE_DESC_CD.CODE ? v.value : v.name);

    const handleOnChange = (values: Selectable[]) => {
        props.handleOnChange(values.map(getValue));
    };

    const value = options?.filter((selectable) => props.value?.includes(getValue(selectable))) ?? [];

    return options === null ? (
        <LoadingIndicator />
    ) : (
        <MultiSelect
            id={id}
            label="Value"
            name="Value"
            orientation="vertical"
            sizing="medium"
            options={options ?? []}
            value={value}
            onChange={handleOnChange}
        />
    );
};

export { ValueSetSelector };
