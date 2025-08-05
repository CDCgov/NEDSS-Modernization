import { UseFormReturn } from 'react-hook-form';
import { EntryFieldsProps, Required } from 'design-system/entry';
import { Card, CardProps } from 'design-system/card';
import { HasSexBirthDemographic } from '../sexBirth';
import { useSexBirthOptions } from './useSexBirthOptions';
import { SexBirthDemographicFields } from './SexBirthDemographicFields';
import { AgeResolver } from 'date';

type EditSexBirthDemographicCardProps = {
    form: UseFormReturn<HasSexBirthDemographic>;
    ageResolver: AgeResolver;
    title?: string;
} & EntryFieldsProps &
    Omit<CardProps, 'subtext' | 'children' | 'title'>;

const EditSexBirthDemographicCard = ({
    form,
    ageResolver,
    title = 'SexBirth',
    sizing,
    orientation = 'horizontal',
    ...remaining
}: EditSexBirthDemographicCardProps) => {
    const options = useSexBirthOptions();

    return (
        <Card title={title} info={<Required />} {...remaining}>
            <SexBirthDemographicFields
                form={form}
                ageResolver={ageResolver}
                sizing={sizing}
                orientation={orientation}
                options={options}
            />
        </Card>
    );
};

export { EditSexBirthDemographicCard };
