import { Required } from 'design-system/entry';
import { Card, CardProps } from 'design-system/card';
import { useSexBirthOptions } from './useSexBirthOptions';
import { SexBirthDemographicFields, SexBirthDemographicFieldsProps } from './SexBirthDemographicFields';

type EditSexBirthDemographicCardProps = {
    title?: string;
} & Omit<SexBirthDemographicFieldsProps, 'options'> &
    Omit<CardProps, 'subtext' | 'children' | 'title'>;

const EditSexBirthDemographicCard = ({
    form,
    ageResolver,
    entry,
    title = 'Sex & birth',
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
                entry={entry}
                sizing={sizing}
                orientation={orientation}
                options={options}
            />
        </Card>
    );
};

export { EditSexBirthDemographicCard };
