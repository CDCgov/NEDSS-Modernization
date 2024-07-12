import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { PatientCriteriaEntry } from '../criteria';
import { SingleSelect } from 'design-system/select';
import { useConceptOptions } from 'options/concepts';
import styles from './id.module.scss';
import { Input } from 'components/FormInputs/Input';

export const Id = () => {
    const { control } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();
    const identificationType = useWatch({ control: control, name: 'identificationType' });

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
            {identificationType ? (
                <Controller
                    control={control}
                    name="identification"
                    render={({ field: { onChange, value, name } }) => (
                        <Input type="text" value={value} onChange={onChange} name={name} label="Id number" required />
                    )}
                />
            ) : null}
        </div>
    );
};
