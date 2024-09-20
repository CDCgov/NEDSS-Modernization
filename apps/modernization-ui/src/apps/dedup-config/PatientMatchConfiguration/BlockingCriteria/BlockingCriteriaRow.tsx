import { SingleSelect } from 'design-system/select';
import { Icon } from '@trussworks/react-uswds';
import { availableMethods } from 'apps/dedup-config/context/PatientMatchContext';
import { Controller, useFormContext } from 'react-hook-form';
import styles from './blocking-criteria-row.module.scss';
import { usePatientMatchContext } from 'apps/dedup-config/context/PatientMatchContext';
import { BlockingCriteria } from 'apps/dedup-config/types';

type CriteriaOption = {
    name: string;
    label: string;
    value: string;
    id: number;
};

export const BlockingCriteriaRow = ({
    criteria,
    criteriaIndex
}: {
    criteria: BlockingCriteria;
    criteriaIndex: number;
}) => {
    const { removeBlockingCriteria, blockingCriteria, setBlockingCriteria } = usePatientMatchContext();
    const form = useFormContext<any>();
    const options: CriteriaOption[] = availableMethods.map((method, index) => ({
        name: method.name,
        label: method.name,
        value: method.value,
        id: index
    }));

    const handleChange = (e: any) => {
        const method = { name: e.name, value: e.value };
        const criteriaCopy = { ...criteria, method };
        const updatedCriteria = [...blockingCriteria];
        updatedCriteria[criteriaIndex] = criteriaCopy;
        setBlockingCriteria(updatedCriteria);
        form.setValue(`blockingCriteria[${criteriaIndex}].method`, method);
    };

    return (
        <div className={styles.row}>
            <h5>{criteria.field.label}</h5>
            <Controller
                control={form.control}
                defaultValue={criteria.method.value}
                name={`blockingCriteria[${criteriaIndex}].method`}
                render={({ field: { value, name } }) => (
                    <SingleSelect
                        id={`method-select-${criteria.field.name}`}
                        name={name}
                        label="Method"
                        options={options}
                        value={options.find((option) => option.value === value) || value}
                        onChange={(e) => handleChange(e)}
                        orientation="horizontal"
                        size={1}
                        sizing="compact"
                    />
                )}
            />
            <div className={styles.gap}></div>
            <h5 className={styles.delete} onClick={() => removeBlockingCriteria(criteria.field.name)}>
                <Icon.Delete size={3} /> Remove
            </h5>
        </div>
    );
};
