import { Suspense, useState } from 'react';
import { Await, NavLink } from 'react-router';
import { MemoizedSupplier } from 'libs/supplying';
import { internalizeDate, internalizeDateTime } from 'date';
import { exists } from 'utils';
import { LoadingOverlay } from 'libs/loading';
import { Shown } from 'conditional-render';
import { Sizing } from 'design-system/field';
import { displayNoData } from 'design-system/data';
import { Card, TableCardProps } from 'design-system/card';
import { Tag } from 'design-system/tag';
import { MaybeLabeledValue } from 'design-system/value';
import { Section } from 'design-system/card';
import { Column, SortableDataTable } from 'design-system/table';
import { ColumnPreference, ColumnPreferenceProvider, ColumnPreferencesAction } from 'design-system/table/preferences';

import { maybeDisplayName } from 'name';
import { ClassicModalButton } from 'libs/classic';
import { AssociatedWith } from 'libs/events/investigations/associated';
import { PatientFileContact, PatientFileContacts } from './contacts';

import styles from './contacts-card.module.scss';
import { maybeMap } from 'utils/mapping';

const EVENT_ID = { id: 'local', name: 'Event ID' };
const DATE_CREATED = { id: 'created-on', name: 'Date created' };
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

const displayReferralBasis = maybeMap((value) => ` (${value})`);

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
                    url={`/nbs/api/profile/${value.patient}/contact/${value.identifier}?condition=${value.condition}`}
                    onClose={onClose}>
                    {value.local}
                </ClassicModalButton>
                <br />
                <strong>
                    {value.processingDecision}
                    {displayReferralBasis(value.referralBasis)}
                </strong>
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
        className: styles['date-time-header'],
        sortable: true,
        value: (value) => value.namedOn,
        render: (value) => internalizeDate(value.namedOn)
    },
    {
        ...NAME,
        className: styles['text-header'],
        sortable: true,
        value: (value) => maybeDisplayName(value.named),
        render: (value) => <NavLink to={`/patient/${value.named.patientId}`}>{maybeDisplayName(value.named)}</NavLink>
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

type TitleResolver = (condition: string) => string;

type InternalCardProps = {
    data?: PatientFileContacts[];
    titleResolver: TitleResolver;
    onClose: () => void;
} & Omit<TableCardProps<PatientFileContacts>, 'data' | 'columns' | 'defaultColumnPreferences' | 'columnPreferencesKey'>;

const dataLength = (data: PatientFileContacts[]) => data.reduce((current, next) => current + next.contacts.length, 0);

const InternalCard = ({ sizing, title, data = [], onClose, titleResolver, ...remaining }: InternalCardProps) => {
    const total = dataLength(data);
    return (
        <ColumnPreferenceProvider id="key" defaults={columnPreferences}>
            {(apply) => (
                <Card
                    id={'patient-file-contact-named'}
                    title={title}
                    collapsible
                    open={data.length > 0}
                    flair={<Tag size={sizing}>{total}</Tag>}
                    className={styles.card}
                    actions={<ColumnPreferencesAction sizing={sizing} />}>
                    <Shown when={data.length > 0} fallback={<Empty />}>
                        <div className={styles.content}>
                            {data.map((contact) => (
                                <Section
                                    key={contact.condition}
                                    title={titleResolver(contact.condition)}
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
                    </Shown>
                </Card>
            )}
        </ColumnPreferenceProvider>
    );
};

const Empty = () => <div className={styles.empty}>No data has been added.</div>;

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
