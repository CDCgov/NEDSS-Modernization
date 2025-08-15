import { Controller, UseFormReturn } from 'react-hook-form';
import { Sizing } from 'design-system/field';
import { PatientDemographicsDefaults } from '../../demographics';
import { HasRaceDemographics, initial, RaceDemographic } from '../race';
import { RaceDemographicRepeatingBlock, RaceDemographicRepeatingBlockProps } from '../RaceDemographicRepeatingBlock';
import { RaceDemographicFields } from './RaceDemographicFields';
import { categoryValidator } from './categoryValidator';
import { useRaceOptions } from './useRaceOptions';

type EditRaceDemographicsCardProps = {
    form: UseFormReturn<HasRaceDemographics>;
    defaults: PatientDemographicsDefaults;
} & Omit<RaceDemographicRepeatingBlockProps, 'collapsible' | 'formRenderer' | 'editable' | 'defaultValues'>;

const EditRaceDemographicsCard = ({ form, defaults, ...remaining }: EditRaceDemographicsCardProps) => {
    const options = useRaceOptions();

    return (
        <Controller
            control={form.control}
            name="races"
            render={({ field: { onChange, value } }) => (
                <RaceDemographicRepeatingBlock
                    {...remaining}
                    collapsible={false}
                    data={value}
                    viewable
                    editable
                    defaultValues={initial(defaults.asOf)}
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
