import { Controller, useFormContext } from 'react-hook-form';
import { SingleSelect } from 'design-system/select';
import { useConceptOptions } from 'options/concepts';
import { SearchCriteria } from 'apps/search/criteria';
import { PatientCriteriaEntry } from 'apps/search/patient/criteria';

export const RaceEthnicity = () => {
    const { control } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();

    return (
        <SearchCriteria>
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
                        sizing="compact"
                        options={useConceptOptions('PHVS_ETHNICITYGROUP_CDC_UNK', { lazy: false }).options}
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
                        sizing="compact"
                        options={useConceptOptions('P_RACE_CAT', { lazy: false }).options}
                    />
                )}
            />
        </SearchCriteria>
    );
};
