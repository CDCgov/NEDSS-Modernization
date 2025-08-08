import { Controller, UseFormReturn } from 'react-hook-form';
import { HasIdentificationDemographics } from '../identifications';
import { IdentificationDemographicCard, IdentificationDemographicCardProps } from '../IdentificationDemographicCard';

type EditIdentificationDemographicsCardProps = {
    form: UseFormReturn<HasIdentificationDemographics>;
} & Omit<IdentificationDemographicCardProps, 'id' | 'collapsible'>;

const EditIdentificationDemographicsCard = ({ form, ...remaining }: EditIdentificationDemographicsCardProps) => {
    return (
        <Controller
            control={form.control}
            name="identifications"
            render={({ field: { onChange, value, name } }) => (
                <IdentificationDemographicCard
                    {...remaining}
                    id={name}
                    collapsible={false}
                    data={value}
                    onChange={onChange}
                />
            )}
        />
    );
};

export { EditIdentificationDemographicsCard };
