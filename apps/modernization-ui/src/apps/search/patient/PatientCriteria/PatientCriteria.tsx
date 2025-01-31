import { Accordion } from 'components/Accordion';
import { BasicInformation } from './BasicInformation';
import { Address } from './Address';
import { Contact } from './Contact';
import { RaceEthnicity } from './RaceEthnicity';
import { Id } from './Id';
import { useComponentSizing } from 'design-system/sizing';

export const PatientCriteria = () => {
    const sizing = useComponentSizing();

    return (
        <>
            <Accordion title="Basic information" open>
                <BasicInformation sizing={sizing} />
            </Accordion>
            <Accordion title="Address">
                <Address sizing={sizing} />
            </Accordion>
            <Accordion title="Contact">
                <Contact sizing={sizing} />
            </Accordion>
            <Accordion title="ID">
                <Id sizing={sizing} />
            </Accordion>
            <Accordion title="Race/Ethnicity">
                <RaceEthnicity sizing={sizing} />
            </Accordion>
        </>
    );
};
