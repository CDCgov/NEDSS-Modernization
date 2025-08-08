import { Controller, UseFormReturn } from 'react-hook-form';
import { HasNameDemographics } from '../names';
import { NameDemographicCard, NameDemographicCardProps } from '../NameDemographicCard';

type EditNameDemographicsCardProps = {
    form: UseFormReturn<HasNameDemographics>;
} & Omit<NameDemographicCardProps, 'id' | 'collapsible'>;

const EditNameDemographicsCard = ({ form, ...remaining }: EditNameDemographicsCardProps) => {
    return (
        <Controller
            control={form.control}
            name="names"
            render={({ field: { onChange, value, name } }) => (
                <NameDemographicCard {...remaining} id={name} collapsible={false} data={value} onChange={onChange} />
            )}
        />
    );
};

export { EditNameDemographicsCard };
