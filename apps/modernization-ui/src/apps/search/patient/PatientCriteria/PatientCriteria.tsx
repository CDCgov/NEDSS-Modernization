import { Accordion } from 'components/Accordion';
import { Sizing } from 'design-system/field';

import { Address } from './Address';
import { BasicInformation } from './BasicInformation';
import { Contact } from './Contact';
import { Id } from './Id';
import { RaceEthnicity } from './RaceEthnicity';

type PatientCriteriaProps = {
    sizing?: Sizing;
};

export const PatientCriteria = ({ sizing }: PatientCriteriaProps) => {
    return (
        <>
            <Accordion title="Basic information" open={true}>
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
