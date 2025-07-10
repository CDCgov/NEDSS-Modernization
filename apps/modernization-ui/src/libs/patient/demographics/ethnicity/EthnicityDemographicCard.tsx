import { Card, CardProps } from 'design-system/card';
import { EthnicityDemographic } from './ethnicity';
import { EthnicityView } from './EthnicityView';

type EthnicityDemographicCardProps = {
    title?: string;
    demographic?: EthnicityDemographic;
} & Omit<CardProps, 'subtext' | 'children' | 'title'>;

const EthnicityDemographicCard = ({
    demographic,
    title = 'Ethnicity',
    collapsible = true,
    sizing,
    ...remaining
}: EthnicityDemographicCardProps) => {
    return (
        <Card title={title} collapsible={collapsible} sizing={sizing} {...remaining}>
            <EthnicityView data={demographic} sizing={sizing} />
        </Card>
    );
};

export { EthnicityDemographicCard };

export type { EthnicityDemographicCardProps };
