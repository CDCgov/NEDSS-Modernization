import { Card, CardProps } from 'design-system/card';
import { MortalityDemographicView } from './MortalityDemographicView';
import { MortalityDemographic } from '../mortality';

type MortalityDemographicCardProps = {
    title?: string;
    demographic?: MortalityDemographic;
} & Omit<CardProps, 'subtext' | 'children' | 'title'>;

const MortalityDemographicCard = ({
    title = 'Mortality',
    demographic,
    collapsible = true,
    sizing,
    ...remaining
}: MortalityDemographicCardProps) => {
    return (
        <Card title={title} collapsible={collapsible} sizing={sizing} {...remaining}>
            <MortalityDemographicView demographic={demographic} sizing={sizing} />
        </Card>
    );
};

export { MortalityDemographicCard };

export type { MortalityDemographicCardProps };
