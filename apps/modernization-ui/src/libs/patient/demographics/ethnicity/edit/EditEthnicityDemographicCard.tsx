import { Card, CardProps } from 'design-system/card';
import { EntryFieldsProps, Required } from 'design-system/entry';
import { UseFormReturn } from 'react-hook-form';
import { HasEthnicityDemographic } from '../ethnicity';
import { EthnicityDemographicsFields } from './EthnicityDemographicFields';
import { useEthnicityOptions } from './useEthnicityOptions';

type EditEthnicityDemographicCardProps = {
    form: UseFormReturn<HasEthnicityDemographic>;
    title?: string;
} & EntryFieldsProps &
    Omit<CardProps, 'subtext' | 'children' | 'title'>;

const EditEthnicityDemographicCard = ({
    form,
    title = 'Ethnicity',
    sizing,
    orientation,
    ...remaining
}: EditEthnicityDemographicCardProps) => {
    const options = useEthnicityOptions();

    return (
        <Card title={title} sizing={sizing} info={<Required />} {...remaining}>
            <EthnicityDemographicsFields form={form} options={options} sizing={sizing} orientation={orientation} />
        </Card>
    );
};

export { EditEthnicityDemographicCard };
