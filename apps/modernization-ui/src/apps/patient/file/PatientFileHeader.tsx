import { ReactNode } from 'react';
import { displayName } from 'name';
import { mapOr } from 'utils/mapping';
import { displayAgeAsOfToday } from 'date';
import { NoData } from 'components/NoData';
import { Heading } from 'components/heading';
import { Patient } from './patient';

import styles from './patient-file-header.module.scss';

const maybeDisplayName = mapOr(displayName('fullLastFirst'), '---');
const maybeDisplayAge = mapOr((birthday: string) => `${birthday} (${displayAgeAsOfToday(birthday)})`, undefined);

type ValueProps = {
    children?: ReactNode | undefined;
};

const Value = ({ children }: ValueProps) => {
    return <span className={styles.value}>{children || <NoData display="dashes" />}</span>;
};

type PatientFileHeaderProps = {
    patient: Patient;
    actions: ReactNode;
};

export const PatientFileHeader = ({ patient, actions }: PatientFileHeaderProps) => {
    return (
        <header className={styles.header}>
            <div className={styles.title}>
                <Heading level={1}>{maybeDisplayName(patient.name)}</Heading>
                <Value>{patient.sex}</Value>
                <Value>{maybeDisplayAge(patient.birthday)}</Value>
                <Value>Patient ID: {patient.id}</Value>
            </div>
            <div className={styles.actions}>{actions}</div>
        </header>
    );
};
