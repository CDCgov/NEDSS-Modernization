import { Controller, UseFormReturn } from 'react-hook-form';
import { Sizing } from 'design-system/field';
import { PatientDemographicsDefaults } from '../../demographics';
import { AddressDemographic, HasAddressDemographics, initial } from '../address';
import {
    AddressDemographicRepeatingBlock,
    AddressDemographicRepeatingBlockProps
} from '../AddressDemographicRepeatingBlock';
import { AddressDemographicFields } from './AddressDemographicFields';
import { useAddressOptions } from './useAddressOptions';

type EditAddressDemographicsCardProps = {
    form: UseFormReturn<HasAddressDemographics>;
    defaults: PatientDemographicsDefaults;
} & Omit<AddressDemographicRepeatingBlockProps, 'collapsible' | 'formRenderer' | 'editable' | 'defaultValues'>;

const EditAddressDemographicsCard = ({ form, defaults, ...remaining }: EditAddressDemographicsCardProps) => {
    const options = useAddressOptions();
    const defaultValues = initial(defaults?.asOf, defaults?.address);

    return (
        <Controller
            control={form.control}
            name="addresses"
            render={({ field: { onChange, value } }) => (
                <AddressDemographicRepeatingBlock
                    {...remaining}
                    collapsible={false}
                    data={value}
                    viewable
                    editable
                    defaultValues={defaultValues}
                    formRenderer={(_?: AddressDemographic, sizing?: Sizing) => (
                        <AddressDemographicFields sizing={sizing} options={options} />
                    )}
                    onChange={onChange}
                />
            )}
        />
    );
};

export { EditAddressDemographicsCard };
