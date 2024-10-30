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
    asTextCriteria
} from 'options/operator';
import { ChangeEvent, useState } from 'react';

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

const asOperatorAndValue = (value?: string | TextCriteria | null): OperatorAndValue => {
    if (typeof value === 'string') {
        return { operator: 'equals', value };
    }
    if (value != null && typeof value === 'object' && Object.keys(value).length >= 1) {
        return { operator: Object.keys(value)[0] as TextOperation, value: asTextCriteria(value) };
    }
    return { operator: 'equals', value: null };
};

export const OperatorInput = ({
    id,
    value,
    operator = 'equals',
    operationMode,
    label,
    sizing = 'compact',
    orientation = 'vertical',
    error,
    onChange
}: OperatorInputProps) => {
    const initialValue = asOperatorAndValue(value);
    const [combinedValue, setCombinedValue] = useState<OperatorAndValue>(initialValue);
    const operatorSelectId = `${id}Operator`;
    const effectiveOperator = combinedValue.operator || operator;

    const onSelectionChange = (selectedOperation?: Selectable) => {
        setCombinedValue((cur) => ({ ...cur, operator: selectedOperation?.value as TextOperation }));
        const criteriaValue = asTextCriteriaOrString(combinedValue.value, selectedOperation?.value as TextOperation);
        onChange(criteriaValue);
    };

    const onInputChange = (event?: ChangeEvent<HTMLInputElement>) => {
        const value = event?.target.value;
        setCombinedValue((cur) => ({ ...cur, value }));
        const criteriaValue = asTextCriteriaOrString(value, combinedValue.operator);
        onChange(criteriaValue);
    };

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
                            defaultValue={combinedValue.value}
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
