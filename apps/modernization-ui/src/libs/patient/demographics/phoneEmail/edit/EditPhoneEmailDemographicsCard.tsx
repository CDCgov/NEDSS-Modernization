import { Controller, UseFormReturn } from 'react-hook-form';
import { Sizing } from 'design-system/field';
import { PatientDemographicsDefaults } from '../../demographics';
import { HasPhoneEmailDemographics, initial, PhoneEmailDemographic } from '../phoneEmails';
import { PhoneEmailDemographicCardProps } from '../PhoneEmailDemographicCard';
import { PhoneEmailDemographicRepeatingBlock } from '../PhoneEmailDemographicRepeatingBlock';
import { PhoneEmailDemographicFields } from './PhoneEmailDemographicFields';
import { usePhoneEmailOptions } from './usePhoneEmailOptions';

type EditPhoneEmailDemographicsCardProps = {
    form: UseFormReturn<HasPhoneEmailDemographics>;
    defaults: PatientDemographicsDefaults;
} & Omit<PhoneEmailDemographicCardProps, 'collapsible' | 'formRenderer' | 'editable' | 'defaultValues'>;

const EditPhoneEmailDemographicsCard = ({ form, defaults, ...remaining }: EditPhoneEmailDemographicsCardProps) => {
    const options = usePhoneEmailOptions();

    return (
        <Controller
            control={form.control}
            name="phoneEmails"
            render={({ field: { onChange, value } }) => (
                <PhoneEmailDemographicRepeatingBlock
                    {...remaining}
                    collapsible={false}
                    data={value}
                    viewable
                    editable
                    defaultValues={initial(defaults.asOf)}
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
