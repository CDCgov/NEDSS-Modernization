import { internalizeDate } from 'date';
import { Card, CardProps } from 'design-system/card';
import styles from './administrative-information-card.module.scss';
import { Sizing } from 'design-system/field';
import { AdministrativeInformation } from './AdministrativeInformation';
import { OrElseNoData } from 'design-system/data';

type AdministrativeInformationCardType = {
    collapsible?: boolean;
    data?: AdministrativeInformation;
    sizing?: Sizing;
} & Omit<CardProps, 'children'>;

export const AdministrativeInformationCard = ({
    collapsible = false,
    data,
    sizing = 'small',
    ...remaining
}: AdministrativeInformationCardType) => {
    const subtext = data?.comment ? `As of ${internalizeDate(data?.asOf)}` : undefined;

    return (
        <Card subtext={subtext} collapsible={collapsible} open={Boolean(data?.comment)} sizing={sizing} {...remaining}>
            <div className={styles.content}>
                <OrElseNoData>{data?.comment}</OrElseNoData>
            </div>
        </Card>
    );
};
