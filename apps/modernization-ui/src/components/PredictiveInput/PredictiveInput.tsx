import { Input } from 'components/FormInputs/Input';

type Options = {
    value: string;
    label: string;
};

type PredictiveInputProps = {
    key: string;
    id: string;
    name: string;
    options?: Options[];
    defaultValue: string;
    onChange: (string: any) => void;
};

export const PredictiveInput = ({ key, id, name, options, defaultValue, onChange }: PredictiveInputProps) => {
    return (
        <div className="predictive-input" id={id} key={key}>
            <div className="predictive-input__label">
                <h4>{name}</h4>
            </div>
            <Input
                type="text"
                defaultValue={defaultValue}
                onChange={(e: any) => {
                    onChange(e.target.value);
                    console.log(e.target.value);
                }}
            />
            <div className="predictive-input__list">
                {options?.map((option: any) => (
                    <li key={option.value}>{option.label}</li>
                ))}
            </div>
        </div>
    );
};
