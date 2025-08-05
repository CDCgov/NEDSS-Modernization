import classNames from 'classnames';
import { internalizeDate } from 'date';
import { Card, CardProps } from 'design-system/card';
import { OrElseNoData } from 'design-system/data';
import { AdministrativeInformation } from '../administrative';

import styles from './administrative-information-card.module.scss';

type AdministrativeInformationCardProps = {
    title?: string;
    data?: AdministrativeInformation;
} & Omit<CardProps, 'subtext' | 'children' | 'title'>;

const AdministrativeInformationCard = ({
    title = 'Administrative',
    collapsible = true,
    data,
    sizing,
    ...remaining
}: AdministrativeInformationCardProps) => {
    const subtext = data?.comment ? `As of ${internalizeDate(data?.asOf)}` : undefined;

    return (
        <Card title={title} subtext={subtext} collapsible={collapsible} open={Boolean(data?.comment)} {...remaining}>
            <div
                className={classNames(styles.content, {
                    [styles.small]: sizing === 'small',
                    [styles.medium]: sizing === 'medium',
                    [styles.large]: sizing === 'large'
                })}>
                <OrElseNoData>{data?.comment}</OrElseNoData>
            </div>
        </Card>
    );
};

export { AdministrativeInformationCard };
export type { AdministrativeInformationCardProps };
