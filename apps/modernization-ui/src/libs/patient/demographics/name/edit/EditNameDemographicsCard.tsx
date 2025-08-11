import { Controller, UseFormReturn } from 'react-hook-form';
import { Sizing } from 'design-system/field';
import { PatientDemographicsDefaults } from '../../demographics';
import { HasNameDemographics, initial, NameDemographic } from '../names';
import { NameDemographicCardProps } from '../NameDemographicCard';
import { NameDemographicRepeatingBlock } from '../NameDemographicRepeatingBlock';
import { NameDemographicFields } from './NameDemographicFields';
import { useNameOptions } from './useNameOptions';

type EditNameDemographicsCardProps = {
    form: UseFormReturn<HasNameDemographics>;
    defaults: PatientDemographicsDefaults;
} & Omit<NameDemographicCardProps, 'id' | 'collapsible' | 'formRenderer' | 'editable' | 'defaultValues'>;

const EditNameDemographicsCard = ({ form, defaults, ...remaining }: EditNameDemographicsCardProps) => {
    const options = useNameOptions();

    return (
        <Controller
            control={form.control}
            name="names"
            render={({ field: { onChange, value, name } }) => (
                <NameDemographicRepeatingBlock
                    {...remaining}
                    id={name}
                    collapsible={false}
                    data={value}
                    onChange={onChange}
                    editable
                    defaultValues={initial(defaults.asOf)}
                    formRenderer={(_?: NameDemographic, sizing?: Sizing) => (
                        <NameDemographicFields sizing={sizing} options={options} />
                    )}
                />
            )}
        />
    );
};

export { EditNameDemographicsCard };
