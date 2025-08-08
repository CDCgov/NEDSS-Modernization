import { Controller, UseFormReturn } from 'react-hook-form';
import { HasRaceDemographics } from '../race';
import { RaceDemographicCard, RaceDemographicCardProps } from '../RaceDemographicCard';

type EditRaceDemographicsCardProps = {
    form: UseFormReturn<Partial<HasRaceDemographics>>;
} & Omit<RaceDemographicCardProps, 'id' | 'collapsible'>;

const EditRaceDemographicsCard = ({ form, ...remaining }: EditRaceDemographicsCardProps) => {
    return (
        <Controller
            control={form.control}
            name="races"
            render={({ field: { onChange, value, name } }) => (
                <RaceDemographicCard {...remaining} id={name} collapsible={false} data={value} onChange={onChange} />
            )}
        />
    );
};

export { EditRaceDemographicsCard };
