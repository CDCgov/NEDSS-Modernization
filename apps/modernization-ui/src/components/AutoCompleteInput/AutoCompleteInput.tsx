import { ComboBox } from '@trussworks/react-uswds';
import { EntryWrapper } from 'components/Entry';

type AutoCompleteProps = {
    label: string;
    name: string;
    value: string | null | undefined;
    onChange: (event: any) => void;
    options: { value: string; label: string }[];
    orientation?: 'vertical' | 'horizontal';
};

export const AutoCompleteInput = ({ label, name, value, onChange, options, orientation }: AutoCompleteProps) => {
    return (
        <EntryWrapper label={label || ''} htmlFor={name || ''} orientation={orientation || 'vertical'}>
            <ComboBox
                data-testid={name || 'combo-box'}
                id={name}
                name={name}
                defaultValue={value || ''}
                onChange={onChange}
                options={options}
            />
        </EntryWrapper>
    );
};
