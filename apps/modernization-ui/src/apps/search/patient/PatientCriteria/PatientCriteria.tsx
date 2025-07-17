import { Sizing } from 'design-system/field';
import { Accordion } from 'components/Accordion';
import { BasicInformation } from './BasicInformation';
import { Address } from './Address';
import { Contact } from './Contact';
import { RaceEthnicity } from './RaceEthnicity';
import { Id } from './Id';

type PatientCriteriaProps = {
    sizing?: Sizing;
};

export const PatientCriteria = ({ sizing }: PatientCriteriaProps) => {
    return (
        <>
            <Accordion title="Basic information" id="basic-information" open>
                <BasicInformation sizing={sizing} />
            </Accordion>
            <Accordion title="Address" id="address">
                <Address sizing={sizing} />
            </Accordion>
            <Accordion title="Contact" id="contact">
                <Contact sizing={sizing} />
            </Accordion>
            <Accordion title="ID" id="id">
                <Id sizing={sizing} />
            </Accordion>
            <Accordion title="Race/Ethnicity" id="race-ethnicity">
                <RaceEthnicity sizing={sizing} />
            </Accordion>
        </>
    );
};
