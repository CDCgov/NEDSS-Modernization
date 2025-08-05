import { Controller, UseFormReturn } from 'react-hook-form';
import { HasPhoneEmailDemographics } from '../phoneEmails';
import { PhoneEmailDemographicCard, PhoneEmailDemographicCardProps } from '../PhoneEmailDemographicCard';

type EditPhoneEmailDemographicsCardProps = {
    form: UseFormReturn<HasPhoneEmailDemographics>;
} & Omit<PhoneEmailDemographicCardProps, 'id' | 'collapsible'>;

const EditPhoneEmailDemographicsCard = ({ form, ...remaining }: EditPhoneEmailDemographicsCardProps) => {
    return (
        <Controller
            control={form.control}
            name="phoneEmails"
            render={({ field: { onChange, value, name } }) => (
                <PhoneEmailDemographicCard
                    {...remaining}
                    id={name}
                    collapsible={false}
                    data={value}
                    onChange={onChange}
                />
            )}
        />
    );
};

export { EditPhoneEmailDemographicsCard };
