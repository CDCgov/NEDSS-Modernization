import { ReactNode } from 'react';
import classNames from 'classnames';
import { Shown } from 'conditional-render';
import { mapOr } from 'utils/mapping';
import { equalsIgnoreCase, not } from 'utils/predicate';
import { defaultTo } from 'libs/supplying';
import { displayName, DisplayableName } from 'name';
import { displayAgeAsOf, today } from 'date';
import { NoData } from 'components/NoData';
import { Heading, HeadingLevel } from 'components/heading';

import styles from './patient-descriptor.module.scss';

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

type PatientDescription = {
    id: number;
    patientId: number;
    status: string;
    sex?: string;
    birthday?: string;
    deceasedOn?: string;
    name?: DisplayableName;
};

type PatientDescriptorProps = {
    headingLevel: HeadingLevel;
    patient: PatientDescription;
};

const notActive = not(equalsIgnoreCase('active'));

const PatientDescriptor = ({ patient, headingLevel }: PatientDescriptorProps) => (
    <div className={styles.title}>
        <Heading level={headingLevel}>{maybeDisplayName(patient.name)}</Heading>
        <span className={styles.values}>
            <Value>{patient.sex}</Value>
            <Value>{maybeDisplayBirthday(patient.birthday, patient.deceasedOn)}</Value>
            <Value>Patient ID: {patient.patientId}</Value>
            <Shown when={notActive(patient.status)}>
                <Value className={styles.inactive}>{patient.status}</Value>
            </Shown>
        </span>
    </div>
);

export { PatientDescriptor };
