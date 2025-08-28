import { Suspense, useState } from 'react';
import { Await } from 'react-router';
import { internalizeDateTime } from 'date';
import { MemoizedSupplier } from 'libs/supplying';
import { LoadingOverlay } from 'libs/loading';
import { permissions, Permitted } from 'libs/permission';
import { ClassicModalButton } from 'libs/classic';
import { Column } from 'design-system/table';
import { TableCard, TableCardProps } from 'design-system/card';
import { ColumnPreference } from 'design-system/table/preferences';
import { Associations } from 'libs/events/investigations/associated';
import { PatientFileBirthRecord } from './birth-record';
import { maybeDisplayMotherInformation } from './displayMotherInformation';

import styles from './patient-file-birth-record.module.scss';

const EVENT_ID = { id: 'local', name: 'Event ID' };
const DATE_RECEIVED = { id: 'received-on', name: 'Date received' };
const BIRTH_FACILITY = { id: 'birth-facility', name: 'Birth facility' };
const REPORT_DATE = { id: 'date-reported', name: 'Report date' };
const MOTHER_INFORMATION = { id: 'mother-information', name: 'Mother information' };
const BIRTH_CERTIFICATE = { id: 'birth-certificate', name: 'Birth certificate number' };
const ASSOCIATED_WITH = { id: 'associated-with', name: 'Associated with' };

const columnPreferences: ColumnPreference[] = [
    { ...EVENT_ID },
    { ...DATE_RECEIVED, moveable: true, toggleable: true },
    { ...BIRTH_FACILITY, moveable: true, toggleable: true },
    { ...REPORT_DATE, moveable: true, toggleable: true },
    { ...MOTHER_INFORMATION, moveable: true, toggleable: true },
    { ...BIRTH_CERTIFICATE, moveable: true, toggleable: true },
    { ...ASSOCIATED_WITH, moveable: true, toggleable: true }
];

const columns = (onClose: () => void): Column<PatientFileBirthRecord>[] => [
    {
        ...EVENT_ID,
        className: styles['local-header'],
        sortable: true,
        value: (value) => value.local,
        render: (value) => (
            <ClassicModalButton
                tertiary
                sizing="small"
                className={styles['event-id']}
                url={`/nbs/api/patients/${value.patient}/records/birth/${value.id}`}
                onClose={onClose}>
                {value.local}
            </ClassicModalButton>
        )
    },
    {
        ...DATE_RECEIVED,
        className: styles['date-time-header'],
        sortable: true,
        sortIconType: 'numeric',
        value: (value) => value.receivedOn,
        render: (value) => internalizeDateTime(value.receivedOn)
    },
    {
        ...BIRTH_FACILITY,
        sortable: true,
        className: styles['text-header'],
        value: (value) => value.facility
    },
    {
        ...REPORT_DATE,
        sortable: true,
        sortIconType: 'numeric',
        value: (value) => value.collectedOn
    },
    {
        ...MOTHER_INFORMATION,
        sortable: true,
        value: (value) => maybeDisplayMotherInformation(value.mother)
    },
    {
        ...BIRTH_CERTIFICATE,
        sortable: true,
        value: (value) => value.certificate
    },
    {
        ...ASSOCIATED_WITH,
        sortable: true,
        className: styles['long-coded-header'],
        value: (value) => value.associations?.[0]?.local,
        render: (value) => <Associations patient={value.patient}>{value.associations}</Associations>
    }
];

type InternalCardProps = {
    patient: number;
    data?: PatientFileBirthRecord[];
    onClose: () => void;
} & Omit<
    TableCardProps<PatientFileBirthRecord>,
    'columnPreferencesKey' | 'defaultColumnPreferences' | 'columns' | 'data' | 'title'
>;

const InternalCard = ({ patient, sizing, data = [], onClose, ...remaining }: InternalCardProps) => {
    return (
        <TableCard
            title="Birth records"
            sizing={sizing}
            data={data}
            columns={columns(onClose)}
            columnPreferencesKey="patient.file.birth-record.preferences"
            defaultColumnPreferences={columnPreferences}
            actions={
                <Permitted permission={permissions.birthRecord.add}>
                    <ClassicModalButton
                        url={`/nbs/api/patients/${patient}/records/birth/add`}
                        icon="add_circle"
                        secondary
                        sizing={sizing}
                        onClose={onClose}>
                        Add birth record
                    </ClassicModalButton>
                </Permitted>
            }
            {...remaining}
        />
    );
};

type PatientFileBirthRecordCardProps = {
    provider: MemoizedSupplier<Promise<PatientFileBirthRecord[]>>;
} & Omit<InternalCardProps, 'data' | 'onClose'>;

const PatientFileBirthRecordCard = ({ provider, ...remaining }: PatientFileBirthRecordCardProps) => {
    const [key, setKey] = useState<number>(0);

    const handleClose = () => {
        provider.reset();
        setKey((current) => current + 1);
    };

    return (
        <Suspense
            key={key}
            fallback={
                <LoadingOverlay>
                    <InternalCard {...remaining} onClose={handleClose} />
                </LoadingOverlay>
            }>
            <Await resolve={provider.get()}>
                {(data) => <InternalCard data={data} onClose={handleClose} {...remaining} />}
            </Await>
        </Suspense>
    );
};

export { PatientFileBirthRecordCard };
