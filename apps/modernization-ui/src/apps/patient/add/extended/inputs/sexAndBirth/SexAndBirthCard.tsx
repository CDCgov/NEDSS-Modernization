import { SexAndBirthEntryFields } from 'apps/patient/data/sexAndBirth/SexAndBirthEntryFields';
import { Card } from '../../card/Card';

export const SexAndBirthCard = () => {
    const info = <span className="required-before">All required fields for adding sex and birth</span>;
    return (
        <Card id="sexAndBirth" title="Sex & birth" info={info}>
            <SexAndBirthEntryFields />
        </Card>
    );
};
