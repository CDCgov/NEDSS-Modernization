import { BlockingCriteria } from 'apps/dedup-config/context/PatientMatchContext';
import { SingleSelect } from 'design-system/select';
import { Icon } from '@trussworks/react-uswds';
import { availableMethods } from 'apps/dedup-config/context/PatientMatchContext';
import { Controller, useFormContext } from 'react-hook-form';
import styles from './blocking-criteria-row.module.scss';
import { usePatientMatchContext } from 'apps/dedup-config/context/PatientMatchContext';

export const BlockingCriteriaRow = ({ criteria }: { criteria: BlockingCriteria }) => {
    const { removeBlockingCriteria } = usePatientMatchContext();
    const form = useFormContext<any>();
    const options = availableMethods.map((method, index) => ({
        name: method.name,
        label: method.name,
        value: method.value,
        id: index
    }));
    console.log(criteria);
    return (
        <div className={styles.row}>
            <h5>{criteria.field.label}</h5>
            <Controller
                control={form.control}
                defaultValue={criteria.method.value}
                name={`blockingCriteria.${criteria.field.name}.method`}
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        id={`method-select-${criteria.field.name}`}
                        name={name}
                        label="Method"
                        options={options}
                        value={options.find((option) => option.value === value) || value}
                        onChange={onChange}
                        orientation="horizontal"
                        size={1}
                        sizing="compact"
                    />
                )}
            />
            <h5 className={styles.delete} onClick={() => removeBlockingCriteria(criteria.field.name)}>
                <Icon.Delete size={3} /> Remove
            </h5>
        </div>
    );
};
