import { ComboBox, Label } from '@trussworks/react-uswds';
import { InputMaybe } from 'generated/graphql/schema';

type AutoCompleteProps = {
    label: string;
    name: string;
    value: InputMaybe<string> | undefined;
    onChange: (event: any) => void;
    options: any;
};

export const AutoCompleteInput = ({ label, name, value, onChange, options }: AutoCompleteProps) => {
    return (
        <>
            <Label htmlFor={name}>{label}</Label>
            <ComboBox
                data-testid={name || 'combo-box'}
                id={name}
                name={name}
                defaultValue={value || ''}
                onChange={onChange}
                options={options}
            />
        </>
    );
};
