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

const EVENT_ID = { id: 'local', name: 'Event ID' };
const DATE_CREATED = { id: 'createx-on', name: 'Date created' };
const DATE_NAMED = { id: 'named-on', name: 'Date named' };
const NAMED_BY = { id: 'named-by', name: 'Named by' };
const DESCRIPTION = { id: 'description', name: 'Description' };
const ASSOCIATED_WITH = { id: 'associated-with', name: 'Associated with' };

const columnPreferences: ColumnPreference[] = [
    { ...EVENT_ID },
    { ...DATE_CREATED, moveable: true, toggleable: true },
    { ...DATE_NAMED, moveable: true, toggleable: true },
    { ...NAMED_BY, moveable: true, toggleable: true },
    { ...DESCRIPTION, moveable: true, toggleable: true },
    { ...ASSOCIATED_WITH, moveable: true, toggleable: true }
];

const displayPatientName = (value?: DisplayableName) => <a href="patient....">{value && displayName('full')(value)}</a>;

const columns: Column<PatientFileContact>[] = [
    {
        ...EVENT_ID,
        className: styles['local-header'],
        sortable: true,
        value: (value) => value.local,
        render: (value) => (
            <>
                <a href={`/nbs/api/profile/${value.patient}/contact/${value.identifier}`}>{value.local}</a>
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
        ...NAMED_BY,
        className: styles['text-header'],
        sortable: true,
        value: (value) => value.named?.first ?? value.named?.last ?? '',
        render: (value) => (
            <>
                <a href={`/patient/${value.patient}`}> {displayPatientName(value.named)}</a>
            </>
        ),
        sortIconType: 'alpha'
    },
    {
        ...DESCRIPTION,
        sortable: true,
        render: (value) => (
            <>
                <strong>{value.associated?.condition}</strong>
                <MaybeLabeledValue label="Priority:" orientation="horizontal">
                    {value.priority}
                </MaybeLabeledValue>
                <MaybeLabeledValue label="Disposition:" orientation="horizontal">
                    {value.disposition}
                </MaybeLabeledValue>
            </>
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

const InternalCard = ({ patient, sizing, title, data = [], ...remaining }: InternalCardProps) => {
    const subTitle = 'was named as a contact in the following';
    return (
        <ColumnPreferenceProvider id="key" defaults={columnPreferences}>
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
                            title={`${patient.name && displayName('short')(patient.name)} ${subTitle} ${contact.condition}`}
                            id={`${contact.condition}-${title}`}
                            sizing={sizing}
                            className={styles.card}
                            subtext={`${contact.contacts.length} records`}>
                            <SortableDataTable
                                columns={columns}
                                data={contact.contacts}
                                {...remaining}
                                sizing={sizing}
                            />
                        </Section>
                    ))}
                </div>
            </Card>
        </ColumnPreferenceProvider>
    );
};

type ContactsCardProps = {
    provider?: PatientFileContacts[];
    sizing?: Sizing;
} & Omit<InternalCardProps, 'data' | 'columns' | 'defaultColumnPreferences' | 'columnPreferencesKey'>;

const ContactsCard = ({ provider, ...remaining }: ContactsCardProps) => {
    return (
        // <Suspense
        //     fallback={
        //         <LoadingOverlay>
        //             <InternalCard {...remaining} />
        //         </LoadingOverlay>
        //     }>
        //     <Await resolve={provider?.get()}>{(data) => <InternalCard data={data} {...remaining} />}</Await>
        // </Suspense>
        <InternalCard data={provider} {...remaining} />
    );
};

export { ContactsCard };

export type { ContactsCardProps };
