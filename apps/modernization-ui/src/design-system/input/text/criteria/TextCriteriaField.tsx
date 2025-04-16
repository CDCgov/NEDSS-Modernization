import { useCallback } from 'react';
import { Field, Orientation, Sizing } from 'design-system/field';
import { Selectable } from 'options';
import {
    asTextCriteriaOrString,
    TextCriteria,
    TextOperation,
    asSelectableOperator,
    asTextCriteriaValue,
    asTextCriteria
} from 'options/operator';
import { TextInput } from '../TextInput';
import styles from './criteria.module.scss';
import { OperatorSelect } from './operator';

export type TextCriteriaFieldProps = {
    id: string;
    /* value is string like "Bob" or object like { "equals": "Bob" } */
    value?: string | TextCriteria | null;
    operator?: TextOperation;
    operationMode?: 'alpha' | 'all';
    label: string;
    helperText?: string;
    sizing?: Sizing;
    orientation?: Orientation;
    error?: string;
    /** When invoked, will pass either a string (if no operator selected) or an TextCriteria object */
    onChange: (value?: string | TextCriteria | null) => void;
};

type OperatorAndValue = {
    operator?: TextOperation;
    value?: string | null;
};

const asOperatorAndValue = (value?: string | TextCriteria | null, operator?: TextOperation): OperatorAndValue => {
    if (value) {
        const objValue: TextCriteria =
            typeof value === 'string' ? asTextCriteria(value, operator)! : (value as TextCriteria);
        if (typeof objValue === 'object' && Object.keys(objValue).length >= 1) {
            return { operator: Object.keys(objValue)[0] as TextOperation, value: asTextCriteriaValue(objValue) };
        }
    }
    return { operator: operator ?? 'equals', value: null };
};

export const TextCriteriaField = ({
    id,
    value,
    operationMode,
    operator = operationMode === 'alpha' ? 'equals' : 'startsWith',
    label,
    helperText,
    sizing,
    orientation,
    error,
    onChange
}: TextCriteriaFieldProps) => {
    const operatorValue = asOperatorAndValue(value, operator);
    const operatorSelectId = `${id}Operator`;
    const effectiveOperator = operatorValue.operator || operator;

    const onSelectionChange = useCallback(
        (selectedOperation?: Selectable) => {
            const criteriaValue = asTextCriteriaOrString(
                operatorValue.value,
                selectedOperation?.value as TextOperation
            );
            onChange(criteriaValue);
        },
        [onChange, operatorValue]
    );

    const onInputChange = useCallback(
        (value?: string) => {
            const criteriaValue = asTextCriteriaOrString(value, operatorValue.operator);
            onChange(criteriaValue);
        },
        [onChange, operatorValue]
    );

    return (
        <Field
            orientation={orientation}
            label={label}
            helperText={helperText}
            htmlFor={id}
            sizing={sizing}
            error={error}>
            <div className={styles.criteriaField}>
                <OperatorSelect
                    id={operatorSelectId}
                    value={asSelectableOperator(effectiveOperator)}
                    mode={operationMode}
                    onChange={onSelectionChange}
                    ariaLabel={`${label} search criteria operator`}
                />
                <TextInput onChange={onInputChange} type="text" name={id} value={operatorValue.value ?? ''} id={id} />
            </div>
        </Field>
    );
};
