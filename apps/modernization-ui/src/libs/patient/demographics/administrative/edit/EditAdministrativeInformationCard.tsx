import { UseFormReturn } from 'react-hook-form';
import { Card, CardProps } from 'design-system/card';
import { EntryFieldsProps, Required } from 'design-system/entry';
import { HasAdministrativeInformation } from '../administrative';
import { AdministrativeInformationFields } from './AdministrativeInformationFields';

type EditAdministrativeInformationCardProps = {
    form: UseFormReturn<HasAdministrativeInformation>;
    title?: string;
} & EntryFieldsProps &
    Omit<CardProps, 'subtext' | 'children' | 'title'>;

const EditAdministrativeInformationCard = ({
    form,
    title = 'Administrative',
    sizing,
    orientation,
    ...remaining
}: EditAdministrativeInformationCardProps) => (
    <Card title={title} sizing={sizing} info={<Required />} {...remaining}>
        <AdministrativeInformationFields form={form} sizing={sizing} orientation={orientation} />
    </Card>
);

export { EditAdministrativeInformationCard };
