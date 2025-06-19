import { Card, CardProps } from 'design-system/card';
import { EthnicityView } from './EthnicityView';
import { Sizing } from 'design-system/field';
import { PatientEthnicityDemographic } from 'generated';

type EthnicityCardProps = {
    data?: PatientEthnicityDemographic;
    sizing?: Sizing;
} & Omit<CardProps, 'subtext' | 'children'>;

const EthnicityCard = ({ data, title = 'Ethnicity', collapsible = true, sizing, ...remaining }: EthnicityCardProps) => {
    return (
        <Card title={title} collapsible={collapsible} {...remaining}>
            <EthnicityView data={data} sizing={sizing} />
        </Card>
    );
};

export { EthnicityCard };
