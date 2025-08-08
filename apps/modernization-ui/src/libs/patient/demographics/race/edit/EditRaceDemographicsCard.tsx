import { Controller, UseFormReturn } from 'react-hook-form';
import { HasRaceDemographics, initial, RaceDemographic } from '../race';
import { RaceDemographicCard, RaceDemographicCardProps } from '../RaceDemographicCard';
import { useRaceCategoryOptions } from 'options/race';
import { RaceDemographicFields } from '../RaceDemographicFields';
import { categoryValidator } from './categoryValidator';
import { Sizing } from 'design-system/field';

const defaultValues = initial();

type EditRaceDemographicsCardProps = {
    form: UseFormReturn<HasRaceDemographics>;
} & Omit<RaceDemographicCardProps, 'id' | 'collapsible' | 'formRenderer' | 'editable' | 'defaultValues'>;

const EditRaceDemographicsCard = ({ form, ...remaining }: EditRaceDemographicsCardProps) => {
    const categories = useRaceCategoryOptions();

    return (
        <Controller
            control={form.control}
            name="races"
            render={({ field: { onChange, value, name } }) => (
                <RaceDemographicCard
                    {...remaining}
                    id={name}
                    collapsible={false}
                    data={value}
                    viewable
                    editable
                    defaultValues={defaultValues}
                    formRenderer={(demographic?: RaceDemographic, sizing?: Sizing) => (
                        <RaceDemographicFields
                            categories={categories}
                            categoryValidator={categoryValidator(value ?? [])}
                            entry={demographic}
                            sizing={sizing}
                        />
                    )}
                    onChange={onChange}
                />
            )}
        />
    );
};

export { EditRaceDemographicsCard };
