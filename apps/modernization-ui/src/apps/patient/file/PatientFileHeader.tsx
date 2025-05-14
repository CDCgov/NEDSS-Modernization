import { ReactNode } from 'react';
import { displayName } from 'name';
import { mapOr } from 'utils/mapping';
import { defaultTo } from 'libs/supplying';
import { displayAgeAsOf, today } from 'date';
import { NoData } from 'components/NoData';
import { Heading } from 'components/heading';
import { Patient } from './patient';

import styles from './patient-file-header.module.scss';
import { Shown } from 'conditional-render';
import classNames from 'classnames';

const maybeDisplayName = mapOr(displayName('fullLastFirst'), '---');

const maybeDisplayBirthday = (birthday?: string, asOf?: string) => {
    if (birthday) {
        return `${birthday} (${displayAgeAsOf(birthday, defaultTo(today, asOf))})`;
    }
};

type ValueProps = {
    className?: string;
    children?: ReactNode | undefined;
};

const Value = ({ className, children }: ValueProps) => {
    return <span className={classNames(styles.value, className)}>{children || <NoData display="dashes" />}</span>;
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
                <span className={styles.values}>
                    <Value>{patient.sex}</Value>
                    <Value>{maybeDisplayBirthday(patient.birthday, patient.deceasedOn)}</Value>
                    <Value>Patient ID: {patient.patientId}</Value>
                    <Shown when={patient.status === 'INACTIVE'}>
                        <Value className={styles.inactive}>{patient.status}</Value>
                    </Shown>
                </span>
            </div>
            <div className={styles.actions}>{actions}</div>
        </header>
    );
};
