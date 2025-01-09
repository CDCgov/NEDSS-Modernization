import { Sizing } from 'components/Entry';
import { SingleSelect } from 'design-system/select';
import { Selectable } from 'options';
import { textOperators, defaultTextOperator, textAlphaOperators } from 'options/operator';

export type OperatorSelectProps = {
    id: string;
    value?: Selectable | null;
    mode?: 'alpha' | 'all';
    showLabel?: boolean;
    sizing?: Sizing;
    onChange: (value?: Selectable) => void;
};

export const OperatorSelect = ({ id, value, mode, showLabel = false, sizing, onChange }: OperatorSelectProps) => {
    return (
        <SingleSelect
            value={value || defaultTextOperator}
            onChange={onChange}
            name={id}
            label={showLabel ? 'Operator' : ''}
            id={id}
            options={mode === 'alpha' ? textAlphaOperators : textOperators}
            sizing={sizing}
            placeholder=""
        />
    );
};
