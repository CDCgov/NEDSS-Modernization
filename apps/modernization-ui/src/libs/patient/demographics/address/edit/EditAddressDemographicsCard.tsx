import { Controller, UseFormReturn } from 'react-hook-form';
import { Sizing } from 'design-system/field';
import { AddressDemographic, HasAddressDemographics, initial } from '../address';
import {
    AddressDemographicRepeatingBlock,
    AddressDemographicRepeatingBlockProps
} from '../AddressDemographicRepeatingBlock';
import { AddressDemographicFields } from './AddressDemographicFields';
import { useAddressOptions } from './useAddressOptions';

const defaultValues = initial();

type EditAddressDemographicsCardProps = {
    form: UseFormReturn<HasAddressDemographics>;
} & Omit<AddressDemographicRepeatingBlockProps, 'id' | 'collapsible' | 'formRenderer' | 'editable' | 'defaultValues'>;

const EditAddressDemographicsCard = ({ form, ...remaining }: EditAddressDemographicsCardProps) => {
    const options = useAddressOptions();

    return (
        <Controller
            control={form.control}
            name="addresses"
            render={({ field: { onChange, value, name } }) => (
                <AddressDemographicRepeatingBlock
                    {...remaining}
                    id={name}
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
