import { internalizeDate } from 'date';
import { Card } from 'design-system/card';
import { AdministrativeInformation } from './AdministrativeInformation';
import styles from './administrative-information-card.module.scss';
import { Sizing } from 'design-system/field';
import { NoData } from 'components/NoData';

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
            <div className={styles.content}>{data?.comment ? data?.comment : <NoData display="dashes" />}</div>
        </Card>
    );
};
