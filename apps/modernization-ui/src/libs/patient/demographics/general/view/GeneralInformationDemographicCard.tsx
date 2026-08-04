import { Card, CardProps } from 'design-system/card';

import { GeneralInformationDemographic } from '../general';

import { GeneralInformationDemographicView } from './GeneralInformationDemographicView';

type GeneralInformationDemographicCardProps = {
    title?: string;
    demographic?: GeneralInformationDemographic;
} & Omit<CardProps, 'subtext' | 'children' | 'title'>;

const GeneralInformationDemographicCard = ({
    title = 'General patient information',
    demographic,
    collapsible = true,
    sizing,
    ...remaining
}: GeneralInformationDemographicCardProps) => (
    <Card title={title} collapsible={collapsible} sizing={sizing} {...remaining}>
        <GeneralInformationDemographicView demographic={demographic} sizing={sizing} />
    </Card>
);

export { GeneralInformationDemographicCard };

export type { GeneralInformationDemographicCardProps };
