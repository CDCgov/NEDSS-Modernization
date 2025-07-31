import { ContactNamed, PatientFileContactsNamed } from './contactsNamed';
import { Card, TableCardProps } from 'design-system/card';
import { Section } from 'design-system/card/section/Section';
import { Column, SortableDataTable } from 'design-system/table';
import { ColumnPreference, ColumnPreferenceProvider, ColumnPreferencesAction } from 'design-system/table/preferences';

import styles from './contacts-card.module.scss';
import { internalizeDate, internalizeDateTime } from 'date';
import { DisplayableName, displayName } from 'name';
import { Associations } from '../investigations/associated';
import { Tag } from 'design-system/tag';
import { Sizing } from 'design-system/field';

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

const columns: Column<ContactNamed>[] = [
    {
        ...EVENT_ID,
        className: styles['local-header'],
        sortable: true,
        value: (value) => value.local,
        render: (value) => (
            <>
                <a href={``}>{value.local}</a>
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
        className: styles['text-header'],
        sortable: true,
        value: (value) => value.namedOn,
        render: (value) => internalizeDate(value.namedOn)
    },
    {
        ...NAMED_BY,
        className: styles['date-time-header'],
        sortable: true,
        value: (value) => value.name?.first ?? value.name?.last ?? '',
        render: (value) => displayPatientName(value.name),
        sortIconType: 'alpha'
    },
    {
        ...DESCRIPTION,
        sortable: true,
        render: () => <>need to render description</>
    },
    {
        ...ASSOCIATED_WITH,
        className: styles['local-header'],
        sortable: true,
        value: (value) => value.associations?.[0]?.local,
        render: (value) => <Associations patient={value.patient}>{value.associations}</Associations>
    }
];

type InternalCardProps = {
    patient: number;
    data?: PatientFileContactsNamed[];
} & Omit<
    TableCardProps<PatientFileContactsNamed>,
    'data' | 'columns' | 'defaultColumnPreferences' | 'columnPreferencesKey'
>;

const InternalCard = ({ patient, sizing, title, data = [], ...remaining }: InternalCardProps) => {
    return (
        <ColumnPreferenceProvider id="key" defaults={columnPreferences}>
            <Card
                id={'patient-file-contact-named'}
                title={title}
                collapsible
                flair={<Tag size={sizing}>{data.length}</Tag>}
                actions={<ColumnPreferencesAction sizing={sizing} />}>
                {data.map((contact) => (
                    <Section
                        title={`${title} ${contact.condition}`}
                        id={`${contact.condition}-${title}`}
                        sizing={sizing}
                        subtext={`${contact.contacts.length} records`}>
                        <SortableDataTable columns={columns} data={contact.contacts} {...remaining} sizing={sizing} />
                    </Section>
                ))}
            </Card>
        </ColumnPreferenceProvider>
    );

    // <TableCard title={title} sizing={sizing} data={data} {...remaining} />
};

type ContactsCardProps = {
    provider?: PatientFileContactsNamed[];
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
