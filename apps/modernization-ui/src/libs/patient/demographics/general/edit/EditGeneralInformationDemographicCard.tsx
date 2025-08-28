import { Card, CardProps } from 'design-system/card';
import { EntryFieldsProps, Required } from 'design-system/entry';
import { UseFormReturn } from 'react-hook-form';
import { HasGeneralInformationDemographic } from '../general';
import { GeneralInformationDemographicFields } from './GeneralInformationDemographicFields';
import { useGeneralInformationOptions } from './useGeneralInformationOptions';

type EditGeneralInformationDemographicCardProps = {
    form: UseFormReturn<HasGeneralInformationDemographic>;
    title?: string;
} & EntryFieldsProps &
    Omit<CardProps, 'subtext' | 'children' | 'title'>;

const EditGeneralInformationDemographicCard = ({
    form,
    title = 'General patient information',
    sizing,
    orientation = 'horizontal',
    ...remaining
}: EditGeneralInformationDemographicCardProps) => {
    const options = useGeneralInformationOptions();

    return (
        <Card title={title} info={<Required />} {...remaining}>
            <GeneralInformationDemographicFields
                form={form}
                sizing={sizing}
                orientation={orientation}
                options={options}
            />
        </Card>
    );
};

export { EditGeneralInformationDemographicCard };
