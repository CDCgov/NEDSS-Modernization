import classNames from 'classnames';
import { internalizeDate } from 'date';
import { Card, CardProps } from 'design-system/card';
import { AdministrativeInformation } from '../administrative';
import { defaultTo } from 'libs/supplying';

import styles from './administrative-information-card.module.scss';


const orElseNoData = defaultTo('No comments available.') 

type AdministrativeInformationCardProps = {
    title?: string;
    data?: Partial<AdministrativeInformation>;
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
               {orElseNoData(data?.comment)}
            </div>
        </Card>
    );
};

export { AdministrativeInformationCard };
export type { AdministrativeInformationCardProps };
