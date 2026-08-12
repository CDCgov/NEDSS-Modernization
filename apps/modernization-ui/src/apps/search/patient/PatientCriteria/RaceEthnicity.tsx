import { Controller, useFormContext } from 'react-hook-form';

import { SearchCriteria } from 'apps/search/criteria';
import { PatientCriteriaEntry } from 'apps/search/patient/criteria';
import { EntryFieldsProps } from 'design-system/entry';
import { SingleSelect } from 'design-system/select';
import { useConceptOptions } from 'options/concepts';
import { useRaceCategoryOptions } from 'options/race';

export const RaceEthnicity = ({ sizing, orientation }: EntryFieldsProps) => {
    const { control } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();

    const categories = useRaceCategoryOptions();
    const conceptOptions = useConceptOptions('PHVS_ETHNICITYGROUP_CDC_UNK', { lazy: false }).options;

    return (
        <SearchCriteria sizing={sizing}>
            <Controller
                control={control}
                name="ethnicity"
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        value={value}
                        onChange={onChange}
                        name={name}
                        label="Ethnicity"
                        id={name}
                        sizing={sizing}
                        orientation={orientation}
                        options={conceptOptions}
                    />
                )}
            />
            <Controller
                control={control}
                name="race"
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        value={value}
                        onChange={onChange}
                        name={name}
                        label="Race"
                        id={name}
                        sizing={sizing}
                        orientation={orientation}
                        options={categories}
                    />
                )}
            />
        </SearchCriteria>
    );
};
