import { useConfiguration } from 'configuration';
import { MultiSelect } from 'design-system/select';
import { LoadingIndicator } from 'libs/loading/indicator';
import { cachedSelectableResolver, Selectable } from 'options';
import { useEffect, useId, useState } from 'react';
import { ValueEditorProps } from 'react-querybuilder';

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
        race_Code: '/races',
    };
};

const ValueSetSelector = (props: ValueEditorProps) => {
    const id = useId();
    const [options, setOptions] = useState<Selectable[] | null>(null);
    const { ready, properties } = useConfiguration();
    const [valueSetMap, setValueSetMap] = useState<Record<string, string>>({});

    useEffect(() => {
        if (ready) {
            setValueSetMap(getValueSetMap(properties.entries.NBS_STATE_CODE));
        }
    }, [ready, properties]);

    useEffect(() => {
        const getValues = async (): Promise<Selectable[]> => {
            const field: Record<string, unknown> = props.schema.fieldMap[props.field] ?? {};
            // we shoved in this extra metadata, so need to tell typescript about it
            const codeDescCd = field.codeDescCd as string | undefined;
            const codesetNm = field.codesetNm as string | undefined;
            const columnUid = field.columnUid as number;

            let cacheId = `report.valueset.${codeDescCd}.${codesetNm}`;

            let endpoint = '';
            if (codeDescCd?.toLowerCase() === 'h') {
                endpoint = `/report/distinct/${columnUid}`;
                // code set desc/name not valid for the column content-based values
                cacheId = `report.valueset.${columnUid}`;
            } else if (codesetNm?.includes('.')) {
                const [table, valueSet] = codesetNm.split('.');
                if (table.toLowerCase() === 'code_value_general') {
                    endpoint = `/concepts/${valueSet}`;
                } else if (table.toLowerCase() === 'code_value_clinical') {
                    endpoint = `/clinical/concepts/${valueSet}`;
                } else {
                    console.error(`unknown SRT table ${table}, returning no options for ${props.field}`);
                    return [];
                }
            } else if (codesetNm?.toLowerCase() ?? '' in valueSetMap) {
                endpoint = valueSetMap[codesetNm?.toLowerCase() ?? ''];
            } else {
                console.error(
                    `unable to determine value set for column ${columnUid} with codesetNm ${codesetNm}, 
                    returning no options for ${props.field} `
                );

                return [];
            }

            if (!endpoint) {
                return [];
            }

            console.log({ endpoint, codesetNm, codeDescCd });

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
                    console.error({ error });
                    setOptions([]);
                });
        }
    }, [ready, valueSetMap]);

    return options === null ? (
        <LoadingIndicator />
    ) : (
        <MultiSelect id={id} label="Value" name="Value" orientation="vertical" options={options ?? []} />
    );
};

export { ValueSetSelector };
