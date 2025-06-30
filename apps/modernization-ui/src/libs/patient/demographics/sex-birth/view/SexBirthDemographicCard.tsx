import { Card, CardProps } from 'design-system/card';
import { SexBirthDemographicView } from './SexBirthDemographicView';
import { SexBirthDemographic } from '../sexBirth';
import { AgeResolver } from 'date';

type SexBirthDemographicCardProps = {
    title?: string;
    ageResolver: AgeResolver;
    demographic?: SexBirthDemographic;
} & Omit<CardProps, 'subtext' | 'children' | 'title'>;

const SexBirthDemographicCard = ({
    title = 'Sex & birth',
    ageResolver,
    demographic,
    collapsible = true,
    sizing,
    ...remaining
}: SexBirthDemographicCardProps) => {
    return (
        <Card title={title} collapsible={collapsible} sizing={sizing} {...remaining}>
            <SexBirthDemographicView demographic={demographic} ageResolver={ageResolver} sizing={sizing} />
        </Card>
    );
};

export { SexBirthDemographicCard };

export type { SexBirthDemographicCardProps };
