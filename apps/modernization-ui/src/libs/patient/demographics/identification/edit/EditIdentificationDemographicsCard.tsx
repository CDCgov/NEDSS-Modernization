import { Controller, UseFormReturn } from 'react-hook-form';
import { Sizing } from 'design-system/field';
import { PatientDemographicsDefaults } from '../../demographics';
import { HasIdentificationDemographics, IdentificationDemographic, initial } from '../identifications';
import {
    IdentificationDemographicRepeatingBlock,
    IdentificationDemographicRepeatingBlockProps
} from '../IdentificationDemographicRepeatingBlock';
import { IdentificationDemographicFields } from './IdentificationDemographicFields';
import { useIdentificationOptions } from './useIdentificationOptions';

type EditIdentificationDemographicsCardProps = {
    form: UseFormReturn<HasIdentificationDemographics>;
    defaults: PatientDemographicsDefaults;
} & Omit<
    IdentificationDemographicRepeatingBlockProps,
    'id' | 'collapsible' | 'formRenderer' | 'editable' | 'defaultValues'
>;

const EditIdentificationDemographicsCard = ({
    form,
    defaults,
    ...remaining
}: EditIdentificationDemographicsCardProps) => {
    const options = useIdentificationOptions();

    return (
        <Controller
            control={form.control}
            name="identifications"
            render={({ field: { onChange, value, name } }) => (
                <IdentificationDemographicRepeatingBlock
                    {...remaining}
                    id={name}
                    collapsible={false}
                    data={value}
                    onChange={onChange}
                    editable
                    defaultValues={initial(defaults.asOf)}
                    formRenderer={(_?: IdentificationDemographic, sizing?: Sizing) => (
                        <IdentificationDemographicFields sizing={sizing} options={options} />
                    )}
                />
            )}
        />
    );
};

export { EditIdentificationDemographicsCard };
