import { SexAndBirthEntryFields } from 'apps/patient/data/sexAndBirth/SexAndBirthEntryFields';
import { Card } from '../../card/Card';

export const SexAndBirthCard = () => {
    return (
        <Card id="sexAndBirth" title="Sex & birth">
            <SexAndBirthEntryFields />
        </Card>
    );
};
