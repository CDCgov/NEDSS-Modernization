import { Card, CardProps } from 'design-system/card';
import { Ethnicity } from './Ethnicity';
import { EthnicityView } from './EthnicityView';
import { Sizing } from 'design-system/field';

type EthnicityCardProps = {
    data?: Ethnicity;
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
