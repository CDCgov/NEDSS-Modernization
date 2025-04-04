import { Selectable } from 'options';
import { textOperators, defaultTextOperator, textAlphaOperators } from 'options/operator';
import Select from 'design-system/select/single/Select';

export type OperatorSelectProps = {
    id: string;
    value?: Selectable | null;
    mode?: 'alpha' | 'all';
    onChange: (value?: Selectable) => void;
};

export const OperatorSelect = ({ id, value, mode, onChange }: OperatorSelectProps) => {
    return (
        <Select
            value={value || defaultTextOperator}
            onChange={onChange}
            name={id}
            id={id}
            options={mode === 'alpha' ? textAlphaOperators : textOperators}
            placeholder=""
        />
    );
};
