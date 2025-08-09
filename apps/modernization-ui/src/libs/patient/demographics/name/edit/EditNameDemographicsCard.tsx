import { Controller, UseFormReturn } from 'react-hook-form';
import { HasNameDemographics, initial, NameDemographic } from '../names';
import { NameDemographicCardProps } from '../NameDemographicCard';
import { NameDemographicRepeatingBlock } from '../NameDemographicRepeatingBlock';
import { NameDemographicFields } from './NameDemographicFields';
import { Sizing } from 'design-system/field';
import { useNameOptions } from './useNameOptions';

const defaultValues = initial();

type EditNameDemographicsCardProps = {
    form: UseFormReturn<HasNameDemographics>;
} & Omit<NameDemographicCardProps, 'id' | 'collapsible' | 'formRenderer' | 'editable' | 'defaultValues'>;

const EditNameDemographicsCard = ({ form, ...remaining }: EditNameDemographicsCardProps) => {
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
                    defaultValues={defaultValues}
                    formRenderer={(_?: NameDemographic, sizing?: Sizing) => (
                        <NameDemographicFields sizing={sizing} options={options} />
                    )}
                />
            )}
        />
    );
};

export { EditNameDemographicsCard };
