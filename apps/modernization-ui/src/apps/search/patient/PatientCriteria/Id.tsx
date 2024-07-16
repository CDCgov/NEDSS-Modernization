import { Controller, useFormContext } from 'react-hook-form';
import { PatientCriteriaEntry } from '../criteria';
import { SingleSelect } from 'design-system/select';
import { useConceptOptions } from 'options/concepts';
import styles from './id.module.scss';

export const Id = () => {
    const { control } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();

    return (
        <div className={styles.id}>
            <Controller
                control={control}
                name="identificationType"
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        value={value}
                        onChange={onChange}
                        name={name}
                        label="ID"
                        id={name}
                        options={useConceptOptions('EI_TYPE_PAT', { lazy: false }).options}
                    />
                )}
            />
        </div>
    );
};
