import { Card, CardProps } from 'design-system/card';
import { PatientEthnicityDemographic } from 'generated';
import styles from './ethnicity-card.module.scss';

type EthnicityCardProps = {
    data?: PatientEthnicityDemographic;
} & Omit<CardProps, 'subtext' | 'children'>;

const EthnicityCard = ({ data, title = 'Ethnicity', collapsible = true, ...remaining }: EthnicityCardProps) => {
    return (
        <Card title={title} collapsible={collapsible} {...remaining}>
            <div className={styles.content}>{data?.asOf}</div>
        </Card>
    );
};

export { EthnicityCard };
