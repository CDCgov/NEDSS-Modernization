import { Card, CardProps } from 'design-system/card';
import { Required } from 'design-system/entry';

import { MortalityDemographicFields, MortalityDemographicFieldsProps } from './MortalityDemographicFields';
import { useMortalityOptions } from './useMortalityOptions';

type EditMortalityDemographicCardProps = {
    title?: string;
} & Omit<MortalityDemographicFieldsProps, 'options'> &
    Omit<CardProps, 'subtext' | 'children' | 'title'>;

const EditMortalityDemographicCard = ({
    form,
    entry,
    title = 'Mortality',
    sizing,
    orientation = 'horizontal',
    ...remaining
}: EditMortalityDemographicCardProps) => {
    const options = useMortalityOptions();

    return (
        <Card title={title} info={<Required />} {...remaining}>
            <MortalityDemographicFields
                form={form}
                sizing={sizing}
                orientation={orientation}
                options={options}
                entry={entry}
            />
        </Card>
    );
};

export { EditMortalityDemographicCard };
