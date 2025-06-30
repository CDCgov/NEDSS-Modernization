import { AgeResolver } from 'date';
import { Card, CardProps } from 'design-system/card';
import { SexBirthDemographic } from '../sexBirth';
import { SexBirthDemographicView } from './SexBirthDemographicView';

type SexBirthCardProps = {
    demographic?: SexBirthDemographic;
    title?: string;
    ageResolver: AgeResolver;
} & Omit<CardProps, 'title' | 'subtext' | 'children'>;

const SexBirthDemographicCard = ({
    title = 'Sex & birth',
    collapsible = true,
    demographic,
    ageResolver,
    sizing,
    ...remaining
}: SexBirthCardProps) => {
    return (
        <Card title={title} collapsible={collapsible} sizing={sizing} {...remaining}>
            <SexBirthDemographicView demographic={demographic} ageResolver={ageResolver} sizing={sizing} />
        </Card>
    );
};
export { SexBirthDemographicCard };
