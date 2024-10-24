import { Sizing } from 'components/Entry';
import { SingleSelect } from 'design-system/select';
import { Selectable } from 'options';
import { operators, defaultOperator } from 'options/operator';

export type OperatorSelectProps = {
    id: string;
    value?: Selectable | null;
    showLabel?: boolean;
    sizing?: Sizing;
    onChange: (value?: Selectable) => void;
};

export const OperatorSelect = ({ id, value, showLabel = false, sizing, onChange }: OperatorSelectProps) => {
    return (
        <SingleSelect
            value={value || defaultOperator}
            onChange={onChange}
            name={id}
            label={showLabel ? 'Operator' : ''}
            id={id}
            options={operators}
            sizing={sizing}
        />
    );
};
