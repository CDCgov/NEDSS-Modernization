import { Controller, UseFormReturn } from 'react-hook-form';
import { Sizing } from 'design-system/field';
import { HasIdentificationDemographics, IdentificationDemographic, initial } from '../identifications';
import {
    IdentificationDemographicRepeatingBlock,
    IdentificationDemographicRepeatingBlockProps
} from '../IdentificationDemographicRepeatingBlock';
import { IdentificationDemographicFields } from './IdentificationDemographicFields';
import { useIdentificationOptions } from './useIdentificationOptions';

const defaultValues = initial();

type EditIdentificationDemographicsCardProps = {
    form: UseFormReturn<HasIdentificationDemographics>;
} & Omit<
    IdentificationDemographicRepeatingBlockProps,
    'id' | 'collapsible' | 'formRenderer' | 'editable' | 'defaultValues'
>;

const EditIdentificationDemographicsCard = ({ form, ...remaining }: EditIdentificationDemographicsCardProps) => {
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
                    defaultValues={defaultValues}
                    formRenderer={(_?: IdentificationDemographic, sizing?: Sizing) => (
                        <IdentificationDemographicFields sizing={sizing} options={options} />
                    )}
                />
            )}
        />
    );
};

export { EditIdentificationDemographicsCard };
