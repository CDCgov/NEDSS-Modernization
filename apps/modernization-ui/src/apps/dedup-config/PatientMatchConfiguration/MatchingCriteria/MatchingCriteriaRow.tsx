import React from 'react';
import { SingleSelect } from 'design-system/select';
import { Icon } from '@trussworks/react-uswds';
import { useFormContext, Controller } from 'react-hook-form';
import { MatchingCriteria } from '../../types';
import { usePatientMatchContext } from '../../context/PatientMatchContext';
import styles from './matching-criteria-row.module.scss';

export const MatchingCriteriaRow = ({
    criteria,
    criteriaIndex
}: {
    criteria: MatchingCriteria;
    criteriaIndex: number;
}) => {
    const { availableMethods, removeMatchingCriteria, setMatchingCriteria, matchingCriteria } =
        usePatientMatchContext();
    const form = useFormContext<any>();
    const options = availableMethods.map((method, index) => ({
        name: method.name,
        label: method.name,
        value: method.value,
        id: index
    }));

    const handleChange = (e: any) => {
        const method = { name: e.name, value: e.value };
        const criteriaCopy = { ...criteria, method };
        const updatedCriteria = [...matchingCriteria];
        updatedCriteria[criteriaIndex] = criteriaCopy;
        setMatchingCriteria(updatedCriteria);
        form.setValue(`matchingCriteria[${criteriaIndex}].method`, method);
    };

    return (
        <div className={styles.row}>
            <h5>{criteria.field.label}</h5>
            <Controller
                control={form.control}
                defaultValue={criteria.method.value}
                name={`matchingCriteria[${criteriaIndex}].method`}
                render={({ field: { name, value } }) => (
                    <SingleSelect
                        id={`method-select-${criteria.field.name}`}
                        name={name}
                        label="Method"
                        options={options}
                        value={options.find((option) => option.value === value) || value}
                        onChange={(e) => handleChange(e)}
                        sizing="compact"
                        orientation="horizontal"
                    />
                )}
            />
            <p className={styles.logOdds}>Log odds &nbsp; {criteria.field.logOdds}</p>
            <h5 className={styles.delete} onClick={() => removeMatchingCriteria(criteria.field.name)}>
                <Icon.Delete size={3} /> Remove
            </h5>
        </div>
    );
};
