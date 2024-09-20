import { EthnicityEntryFields } from 'apps/patient/data/ethnicity/EthnicityEntryFields';
import { Card } from '../../card/Card';

export const EthnicityEntryCard = () => {
    return (
        <Card id="ethnicity" title="Ethnicity">
            <EthnicityEntryFields />
        </Card>
    );
};
