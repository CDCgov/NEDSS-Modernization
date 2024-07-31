import { BasicInformation } from './BasicInformation';
import { Accordion } from 'components/Accordion/Accordion';
import styles from './patient-criteria.module.scss';
import { Address } from './Address';
import { Contact } from './Contact';
import { RaceEthnicity } from './RaceEthnicity';
import { Id } from './Id';

export const PatientCriteria = () => {
    return (
        <div className={styles.criteria}>
            <Accordion title="Basic information" open>
                <BasicInformation />
            </Accordion>
            <Accordion title="Address" open>
                <Address />
            </Accordion>
            <Accordion title="Contact" open>
                <Contact />
            </Accordion>
            <Accordion title="ID" open>
                <Id />
            </Accordion>
            <Accordion title="Race/Ethnicity" open>
                <RaceEthnicity />
            </Accordion>
        </div>
    );
};
