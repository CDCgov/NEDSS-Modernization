import { internalizeDate } from 'date';
import { Card, CardProps } from 'design-system/card';
import { OrElseNoData } from 'design-system/data';
import { AdministrativeInformation } from './AdministrativeInformation';

import styles from './administrative-information-card.module.scss';

type AdministrativeInformationCardProps = {
    title?: string;
    data?: AdministrativeInformation;
} & Omit<CardProps, 'subtext' | 'children' | 'title'>;

const AdministrativeInformationCard = ({
    title = 'Administrative',
    data,
    ...remaining
}: AdministrativeInformationCardProps) => {
    const subtext = data?.comment ? `As of ${internalizeDate(data?.asOf)}` : undefined;

    return (
        <Card title={title} subtext={subtext} open={Boolean(data?.comment)} {...remaining}>
            <div className={styles.content}>
                <OrElseNoData>{data?.comment}</OrElseNoData>
            </div>
        </Card>
    );
};

export { AdministrativeInformationCard };
export type { AdministrativeInformationCardProps };
