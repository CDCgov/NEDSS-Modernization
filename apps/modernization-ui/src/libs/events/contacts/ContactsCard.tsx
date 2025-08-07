import { PatientFileContact, PatientFileContacts } from './contactsNamed';
import { Card, TableCardProps } from 'design-system/card';
import { Section } from 'design-system/card/section/Section';
import { Column, SortableDataTable } from 'design-system/table';
import { ColumnPreference, ColumnPreferenceProvider, ColumnPreferencesAction } from 'design-system/table/preferences';

import styles from './contacts-card.module.scss';
import { internalizeDate, internalizeDateTime } from 'date';
import { DisplayableName, displayName } from 'name';
import { AssociatedWith } from '../investigations/associated';
import { Tag } from 'design-system/tag';
import { Sizing } from 'design-system/field';
import { Patient } from 'apps/patient/file/patient';
import { MaybeLabeledValue } from 'design-system/value';
import { Suspense, useState } from 'react';
import { LoadingOverlay } from 'libs/loading';
import { Await } from 'react-router';
import { MemoizedSupplier } from 'libs/supplying';
import { ClassicModalButton } from 'libs/classic';
import { Shown } from 'conditional-render';
import { exists } from 'utils';
import { displayNoData } from 'design-system/data';

const EVENT_ID = { id: 'local', name: 'Event ID' };
const DATE_CREATED = { id: 'createx-on', name: 'Date created' };
const DATE_NAMED = { id: 'named-on', name: 'Date named' };
const NAME = { id: 'name', name: 'Name' };
const DESCRIPTION = { id: 'description', name: 'Description' };
const ASSOCIATED_WITH = { id: 'associated-with', name: 'Associated with' };

const columnPreferences: ColumnPreference[] = [
    { ...EVENT_ID },
    { ...DATE_CREATED, moveable: true, toggleable: true },
    { ...DATE_NAMED, moveable: true, toggleable: true },
    { ...NAME, moveable: true, toggleable: true },
    { ...DESCRIPTION, moveable: true, toggleable: true },
    { ...ASSOCIATED_WITH, moveable: true, toggleable: true }
];

const displayPatientName = (value?: DisplayableName) => <a href="patient....">{value && displayName('full')(value)}</a>;

const columns = (onClose: () => void): Column<PatientFileContact>[] => [
    {
        ...EVENT_ID,
        className: styles['local-header'],
        sortable: true,
        value: (value) => value.local,
        render: (value) => (
            <>
                <ClassicModalButton
                    tertiary
                    sizing="small"
                    className={styles['event-id']}
                    url={`/nbs/api/profile/${value.patient}/contact/${value.identifier}?condition=${value.associated?.id}`}
                    onClose={onClose}>
                    {value.local}
                </ClassicModalButton>
                <strong>{value.processingDecision}</strong>
                <br />
                {value.referralBasis && `(${value.referralBasis})`}
            </>
        )
    },
    {
        ...DATE_CREATED,
        className: styles['date-time-header'],
        sortable: true,
        value: (value) => value.createdOn,
        render: (value) => internalizeDateTime(value.createdOn),
        sortIconType: 'numeric'
    },
    {
        ...DATE_NAMED,
        className: styles['date-header'],
        sortable: true,
        value: (value) => value.namedOn,
        render: (value) => internalizeDate(value.namedOn)
    },
    {
        ...NAME,
        className: styles['text-header'],
        sortable: true,
        value: (value) => value.named?.first ?? value.named?.last ?? '',
        render: (value) => (
            <>
                <a href={`/patient/${value.named.patientId}`}>{displayPatientName(value.named)}</a>
            </>
        ),
        sortIconType: 'alpha'
    },
    {
        ...DESCRIPTION,
        sortable: true,
        value: (value) => value.associated?.condition ?? value.priority ?? value.disposition,
        render: (value) => (
            <Shown
                when={exists(value.associated) || exists(value.priority) || exists(value.disposition)}
                fallback={displayNoData()}>
                <Shown when={exists(value.associated)}>
                    <strong>{value.associated?.condition}</strong>
                </Shown>
                <MaybeLabeledValue label="Priority:" orientation="horizontal">
                    {value.priority}
                </MaybeLabeledValue>
                <MaybeLabeledValue label="Disposition:" orientation="horizontal">
                    {value.disposition}
                </MaybeLabeledValue>
            </Shown>
        )
    },
    {
        ...ASSOCIATED_WITH,
        className: styles['long-coded-header'],
        sortable: true,
        value: (value) => value.associated?.local,
        render: (value) =>
            value.associated && <AssociatedWith patient={value.patient}>{value.associated}</AssociatedWith>
    }
];

type InternalCardProps = {
    patient: Patient;
    data?: PatientFileContacts[];
    patientNamed?: boolean;
    onClose: () => void;
} & Omit<TableCardProps<PatientFileContacts>, 'data' | 'columns' | 'defaultColumnPreferences' | 'columnPreferencesKey'>;

const dataLength = (data: PatientFileContacts[]) => {
    let count = 0;
    data.map((cond) => {
        cond.contacts.map(() => {
            count++;
        });
    });
    return count;
};

const renderSubTitle = (patient: Patient, contact: PatientFileContacts, patientNamed?: boolean) => {
    return patientNamed
        ? `${patient.name && displayName('short')(patient.name)} was named as a contact in the following ${contact.condition}`
        : `The following contacts were named by ${patient.name && displayName('short')(patient.name)}'s investigation of ${contact.condition}`;
};

const InternalCard = ({
    patient,
    sizing,
    title,
    data = [],
    patientNamed,
    onClose,
    ...remaining
}: InternalCardProps) => {
    return (
        <ColumnPreferenceProvider id="key" defaults={columnPreferences}>
            {(apply) => (
                <Card
                    id={'patient-file-contact-named'}
                    title={title}
                    collapsible
                    flair={<Tag size={sizing}>{dataLength(data)}</Tag>}
                    className={styles.card}
                    actions={<ColumnPreferencesAction sizing={sizing} />}>
                    <div className={styles.content}>
                        {data.map((contact) => (
                            <Section
                                key={contact.condition}
                                title={renderSubTitle(patient, contact, patientNamed)}
                                id={`${contact.condition}-${title}`}
                                sizing={sizing}
                                className={styles.card}
                                subtext={`${contact.contacts.length} record${contact.contacts.length > 1 ? 's' : ''}`}>
                                <SortableDataTable
                                    columns={apply.apply(columns(onClose))}
                                    data={contact.contacts}
                                    {...remaining}
                                    sizing={sizing}
                                />
                            </Section>
                        ))}
                    </div>
                </Card>
            )}
        </ColumnPreferenceProvider>
    );
};

type ContactsCardProps = {
    provider: MemoizedSupplier<Promise<PatientFileContacts[]>>;
    sizing?: Sizing;
} & Omit<InternalCardProps, 'data' | 'columns' | 'defaultColumnPreferences' | 'columnPreferencesKey' | 'onClose'>;

const ContactsCard = ({ provider, ...remaining }: ContactsCardProps) => {
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
            <Await resolve={provider?.get()}>
                {(data) => <InternalCard data={data} onClose={handleClose} {...remaining} />}
            </Await>
        </Suspense>
    );
};

export { ContactsCard };

export type { ContactsCardProps };
