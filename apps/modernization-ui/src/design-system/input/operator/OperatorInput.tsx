import { Grid } from '@trussworks/react-uswds';
import { EntryWrapper, Sizing } from 'components/Entry';
import { Input } from 'components/FormInputs/Input';
import { OperatorSelect } from 'design-system/select';
import { Selectable } from 'options';
import {
    asTextCriteriaOrString,
    TextCriteria,
    TextOperation,
    asSelectableOperator,
    asTextCriteriaValue,
    asTextCriteria
} from 'options/operator';
import { ChangeEvent, useCallback } from 'react';

export type OperatorInputProps = {
    id: string;
    /* value is string like "Bob" or object like { "equals": "Bob" } */
    value?: string | TextCriteria | null;
    operator?: TextOperation;
    operationMode?: 'alpha' | 'all';
    label: string;
    sizing?: Sizing;
    orientation?: 'vertical' | 'horizontal';
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
    operator = 'startsWith',
    operationMode,
    label,
    sizing = 'compact',
    orientation = 'vertical',
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
        <EntryWrapper orientation={orientation} label={label} htmlFor={id} sizing={sizing}>
            <Grid col={12}>
                <Grid row>
                    <Grid col={5} className="padding-right-1">
                        <OperatorSelect
                            id={operatorSelectId}
                            value={asSelectableOperator(effectiveOperator)}
                            mode={operationMode}
                            sizing={sizing}
                            onChange={onSelectionChange}
                        />
                    </Grid>
                    <Grid col={7}>
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
        </EntryWrapper>
    );
};
