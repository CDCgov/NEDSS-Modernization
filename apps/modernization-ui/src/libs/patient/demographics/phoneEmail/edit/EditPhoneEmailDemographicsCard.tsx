import { Controller, UseFormReturn } from 'react-hook-form';
import { Sizing } from 'design-system/field';
import { HasPhoneEmailDemographics, initial, PhoneEmailDemographic } from '../phoneEmails';
import { PhoneEmailDemographicCardProps } from '../PhoneEmailDemographicCard';
import { PhoneEmailDemographicRepeatingBlock } from '../PhoneEmailDemographicRepeatingBlock';
import { PhoneEmailDemographicFields } from './PhoneEmailDemographicFields';
import { usePhoneEmailOptions } from './usePhoneEmailOptions';

const defaultValues = initial();

type EditPhoneEmailDemographicsCardProps = {
    form: UseFormReturn<HasPhoneEmailDemographics>;
} & Omit<PhoneEmailDemographicCardProps, 'id' | 'collapsible' | 'formRenderer' | 'editable' | 'defaultValues'>;

const EditPhoneEmailDemographicsCard = ({ form, ...remaining }: EditPhoneEmailDemographicsCardProps) => {
    const options = usePhoneEmailOptions();

    return (
        <Controller
            control={form.control}
            name="phoneEmails"
            render={({ field: { onChange, value, name } }) => (
                <PhoneEmailDemographicRepeatingBlock
                    {...remaining}
                    id={name}
                    collapsible={false}
                    data={value}
                    viewable
                    editable
                    defaultValues={defaultValues}
                    formRenderer={(_?: PhoneEmailDemographic, sizing?: Sizing) => (
                        <PhoneEmailDemographicFields sizing={sizing} options={options} />
                    )}
                    onChange={onChange}
                />
            )}
        />
    );
};

export { EditPhoneEmailDemographicsCard };
