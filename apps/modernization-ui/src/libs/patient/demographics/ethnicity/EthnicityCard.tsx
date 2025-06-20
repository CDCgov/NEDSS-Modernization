import { Card, CardProps } from 'design-system/card';
import { EthnicityView } from './EthnicityView';
import { PatientEthnicityDemographic } from 'generated';

type EthnicityCardProps = {
    data?: PatientEthnicityDemographic;
    title?: string;
} & Omit<CardProps, 'subtext' | 'children' | 'title'>;

const EthnicityCard = ({ data, title = 'Ethnicity', collapsible = true, sizing, ...remaining }: EthnicityCardProps) => {
    return (
        <Card title={title} collapsible={collapsible} sizing={sizing} {...remaining}>
            <EthnicityView data={data} sizing={sizing} />
        </Card>
    );
};

export { EthnicityCard };

export type { EthnicityCardProps };
