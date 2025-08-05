import { UseFormReturn } from 'react-hook-form';
import { EntryFieldsProps, Required } from 'design-system/entry';
import { Card, CardProps } from 'design-system/card';
import { MortalityDemographic } from '../mortality';
import { useMortalityOptions } from './useMortalityOptions';
import { MortalityDemographicFields } from './MortalityDemographicFields';

type EditMortalityDemographicCardProps = {
    form: UseFormReturn<{ mortality?: MortalityDemographic }>;
    title?: string;
} & EntryFieldsProps &
    Omit<CardProps, 'subtext' | 'children' | 'title'>;

const EditMortalityDemographicCard = ({
    form,
    title = 'Mortality',
    sizing,
    orientation = 'horizontal',
    ...remaining
}: EditMortalityDemographicCardProps) => {
    const options = useMortalityOptions();

    return (
        <Card title={title} info={<Required />} {...remaining}>
            <MortalityDemographicFields form={form} sizing={sizing} orientation={orientation} options={options} />
        </Card>
    );
};

export { EditMortalityDemographicCard };
