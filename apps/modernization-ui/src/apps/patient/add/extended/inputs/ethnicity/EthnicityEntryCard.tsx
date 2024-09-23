import { EthnicityEntryFields } from 'apps/patient/data/ethnicity/EthnicityEntryFields';
import { Card } from '../../card/Card';

export const EthnicityEntryCard = () => {
    const info = <span className="required-before">All required fields for adding ethnicity</span>;
    return (
        <Card id="ethnicity" title="Ethnicity" info={info}>
            <EthnicityEntryFields />
        </Card>
    );
};
