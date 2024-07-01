import { Control } from 'react-hook-form';
import { PatientCriteriaEntry } from '../criteria';
import { BasicInformation } from './BasicInformation';
import { Accordion } from 'components/Accordion/Accordion';
import { Selectable } from 'options';
import styles from './patient-criteria.module.scss';
import { Address } from './Address';

type PatientCriteriaFormProps = {
    control: Control<PatientCriteriaEntry>;
    handleRecordStatusChange: (
        value: Selectable[],
        status: Selectable,
        isChecked: boolean,
        onChange: (status: Selectable[]) => void
    ) => void;
};

export const PatientCriteria = ({ control, handleRecordStatusChange }: PatientCriteriaFormProps) => {
    return (
        <div className={styles.criteria}>
            <Accordion title="Basic information" open>
                <BasicInformation control={control} handleRecordStatusChange={handleRecordStatusChange} />
            </Accordion>
            <Accordion title="Address" open>
                <Address control={control} />
            </Accordion>
        </div>
    );
};
