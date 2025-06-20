import { internalizeDate } from 'date';
import { Card } from 'design-system/card';
import styles from './administrative-information-card.module.scss';
import { Sizing } from 'design-system/field';
import { AdministrativeInformation } from './AdministrativeInformation';
import { OrElseNoData } from 'design-system/data';

type AdministrativeInformationCardType = {
    collapsible?: boolean;
    data?: AdministrativeInformation;
    sizing?: Sizing;
};

export const AdministrativeInformationCard = ({
    collapsible = false,
    data,
    sizing = 'small'
}: AdministrativeInformationCardType) => {
    const subtext = data?.comment ? `As of ${internalizeDate(data?.asOf)}` : undefined;

    return (
        <Card
            id={'administrative-comments'}
            title={'Administrative comments'}
            subtext={subtext}
            collapsible={collapsible}
            open={Boolean(data?.comment)}
            sizing={sizing}>
            <OrElseNoData>
                <div className={styles.content}>{data?.comment}</div>
            </OrElseNoData>
        </Card>
    );
};
