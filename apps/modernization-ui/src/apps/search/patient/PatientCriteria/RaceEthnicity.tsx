import { useConceptOptions } from 'options/concepts';
import { Controller, useFormContext } from 'react-hook-form';
import { PatientCriteriaEntry } from '../criteria';
import { SingleSelect } from 'design-system/select';
import styles from './race-ethnicity.module.scss';

export const RaceEthnicity = () => {
    const { control } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();

    return (
        <div className={styles.race}>
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
                        options={useConceptOptions('RACE_CALCULATED', { lazy: false }).options}
                    />
                )}
            />
        </div>
    );
};
