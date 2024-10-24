import { Accordion } from 'components/Accordion';
import { BasicInformation } from './BasicInformation';
import { Address } from './Address';
import { Contact } from './Contact';
import { RaceEthnicity } from './RaceEthnicity';
import { Id } from './Id';

export const PatientCriteria = () => {
    return (
        <>
            <Accordion title="Basic information" open>
                <BasicInformation />
            </Accordion>
            <Accordion title="Address">
                <Address />
            </Accordion>
            <Accordion title="Contact">
                <Contact />
            </Accordion>
            <Accordion title="ID">
                <Id />
            </Accordion>
            <Accordion title="Race/Ethnicity">
                <RaceEthnicity />
            </Accordion>
        </>
    );
};
