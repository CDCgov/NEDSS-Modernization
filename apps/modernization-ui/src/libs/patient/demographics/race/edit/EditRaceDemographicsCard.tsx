import { Controller, UseFormReturn } from 'react-hook-form';
import { Sizing } from 'design-system/field';
import { HasRaceDemographics, initial, RaceDemographic } from '../race';
import { RaceDemographicRepeatingBlock, RaceDemographicRepeatingBlockProps } from '../RaceDemographicRepeatingBlock';
import { RaceDemographicFields } from './RaceDemographicFields';
import { categoryValidator } from './categoryValidator';
import { useRaceOptions } from './useRaceOptions';

const defaultValues = initial();

type EditRaceDemographicsCardProps = {
    form: UseFormReturn<HasRaceDemographics>;
} & Omit<RaceDemographicRepeatingBlockProps, 'id' | 'collapsible' | 'formRenderer' | 'editable' | 'defaultValues'>;

const EditRaceDemographicsCard = ({ form, ...remaining }: EditRaceDemographicsCardProps) => {
    const options = useRaceOptions();

    return (
        <Controller
            control={form.control}
            name="races"
            render={({ field: { onChange, value, name } }) => (
                <RaceDemographicRepeatingBlock
                    {...remaining}
                    id={name}
                    collapsible={false}
                    data={value}
                    viewable
                    editable
                    defaultValues={defaultValues}
                    formRenderer={(demographic?: RaceDemographic, sizing?: Sizing) => (
                        <RaceDemographicFields
                            options={options}
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
