import { ChangeEvent, useCallback } from 'react';
import { Grid } from '@trussworks/react-uswds';
import { Field, Orientation, Sizing } from 'design-system/field';
import { OperatorSelect } from 'design-system/select';
import { Input } from 'components/FormInputs/Input';
import { Selectable } from 'options';
import {
    asTextCriteriaOrString,
    TextCriteria,
    TextOperation,
    asSelectableOperator,
    asTextCriteriaValue,
    asTextCriteria
} from 'options/operator';
import styles from './operator.module.scss';

export type OperatorInputProps = {
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

export const OperatorInput = ({
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
}: OperatorInputProps) => {
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
        (event?: ChangeEvent<HTMLInputElement>) => {
            const value = event?.target.value;
            const criteriaValue = asTextCriteriaOrString(value, operatorValue.operator);
            onChange(criteriaValue);
        },
        [onChange, operatorValue]
    );

    return (
        <Field orientation={orientation} label={label} helperText={helperText} htmlFor={id} sizing={sizing}>
            <Grid col={12}>
                <Grid row>
                    <Grid col={5} className={styles.left}>
                        <OperatorSelect
                            id={operatorSelectId}
                            value={asSelectableOperator(effectiveOperator)}
                            mode={operationMode}
                            sizing={sizing}
                            onChange={onSelectionChange}
                        />
                    </Grid>
                    <Grid col={7} className={styles.right}>
                        <Input
                            onChange={onInputChange}
                            type="text"
                            name={id}
                            defaultValue={operatorValue.value}
                            htmlFor={id}
                            id={id}
                            sizing={sizing}
                            error={error}
                        />
                    </Grid>
                </Grid>
            </Grid>
        </Field>
    );
};
