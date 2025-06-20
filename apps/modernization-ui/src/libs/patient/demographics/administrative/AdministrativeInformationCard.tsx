import { internalizeDate } from 'date';
import { Card } from 'design-system/card';
import styles from './administrative-information-card.module.scss';
import { Sizing } from 'design-system/field';
import { NoData } from 'design-system/data';
import { AdministrativeInformation } from './AdministrativeInformation';

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
            open={data?.comment ? true : false}
            sizing={sizing}>
            <div className={styles.content}>{data?.comment ? data?.comment : <NoData />}</div>
        </Card>
    );
};
