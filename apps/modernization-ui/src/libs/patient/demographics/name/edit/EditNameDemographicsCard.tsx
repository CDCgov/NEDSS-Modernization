import { Sizing } from 'design-system/field';
import { Controller, UseFormReturn } from 'react-hook-form';

import { PatientDemographicsDefaults } from '../../demographics';
import { NameDemographicRepeatingBlock, NameDemographicRepeatingBlockProps } from '../NameDemographicRepeatingBlock';
import { HasNameDemographics, initial, NameDemographic } from '../names';

import { NameDemographicFields } from './NameDemographicFields';
import { useNameOptions } from './useNameOptions';

type EditNameDemographicsCardProps = {
    form: UseFormReturn<HasNameDemographics>;
    defaults: PatientDemographicsDefaults;
} & Omit<NameDemographicRepeatingBlockProps, 'collapsible' | 'formRenderer' | 'editable' | 'defaultValues'>;

const EditNameDemographicsCard = ({ form, defaults, ...remaining }: EditNameDemographicsCardProps) => {
    const options = useNameOptions();

    return (
        <Controller
            control={form.control}
            name="names"
            render={({ field: { onChange, value } }) => (
                <NameDemographicRepeatingBlock
                    {...remaining}
                    collapsible={false}
                    data={value}
                    onChange={onChange}
                    editable={true}
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
