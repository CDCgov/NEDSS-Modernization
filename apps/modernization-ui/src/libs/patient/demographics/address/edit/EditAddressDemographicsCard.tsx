import { Controller, UseFormReturn } from 'react-hook-form';
import { HasAddressDemographics } from '../address';
import { AddressDemographicCard, AddressDemographicCardProps } from '../AddressDemographicCard';

type EditAddressDemographicsCardProps = {
    form: UseFormReturn<HasAddressDemographics>;
} & Omit<AddressDemographicCardProps, 'id' | 'collapsible'>;

const EditAddressDemographicsCard = ({ form, ...remaining }: EditAddressDemographicsCardProps) => {
    return (
        <Controller
            control={form.control}
            name="addresses"
            render={({ field: { onChange, value, name } }) => (
                <AddressDemographicCard {...remaining} id={name} collapsible={false} data={value} onChange={onChange} />
            )}
        />
    );
};

export { EditAddressDemographicsCard };
