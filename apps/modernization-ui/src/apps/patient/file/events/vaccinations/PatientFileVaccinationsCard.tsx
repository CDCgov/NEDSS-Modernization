import { LinkButton } from 'design-system/button';
import { TableCard, TableCardProps } from 'design-system/card';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { permissions, Permitted } from 'libs/permission';
import { MemoizedSupplier } from 'libs/supplying';

import styles from './patient-file-vaccinations.module.scss';
import { internalizeDate, internalizeDateTime } from 'date';
import { MaybeLabeledValue } from 'design-system/value';
import { displayProvider } from 'libs/provider';
import { Associations } from 'libs/events/investigations/associated';
import { LoadingOverlay } from 'libs/loading';
import { Suspense } from 'react';
import { Await } from 'react-router';
import { PatientFileVaccinations } from '.';

const EVENT_ID = { id: 'local', name: 'Event ID' };
const DATE_RECEIVED = { id: 'created-on', name: 'Date created' };
const ORG_PROV = { id: 'organization-provider', name: 'Organization/Provider' };
const DATE_ADMINISTRATED = { id: 'date-administrated', name: 'Date administrated' };
const VACCINE_ADMINISTRATED = { id: 'vaccine-administrated', name: 'Vaccine administrated' };
const ASSOCIATED_WITH = { id: 'associated-with', name: 'Associated with' };

const columnPreferences: ColumnPreference[] = [
    { ...EVENT_ID },
    { ...DATE_RECEIVED, moveable: true, toggleable: true },
    { ...ORG_PROV, moveable: true, toggleable: true },
    { ...DATE_ADMINISTRATED, moveable: true, toggleable: true },
    { ...VACCINE_ADMINISTRATED, moveable: true, toggleable: true },
    { ...ASSOCIATED_WITH, moveable: true, toggleable: true }
];

const columns: Column<PatientFileVaccinations>[] = [
    {
        ...EVENT_ID,
        className: styles['event-header'],
        sortable: true,
        value: (value) => value.local,
        render: (value) => <a href={`/nbs/api/profile/${value.patient}/vaccination/${value.id}`}>{value.local}</a>
    },
    {
        ...DATE_RECEIVED,
        className: styles['date-header'],
        sortable: true,
        value: (value) => value.createdOn,
        render: (value) => internalizeDateTime(value.createdOn),
        sortIconType: 'numeric'
    },
    {
        ...ORG_PROV,
        className: styles['org-provider-header'],
        sortable: true,
        value: (value) => value.organization,
        render: (value) => (
            <>
                <MaybeLabeledValue orientation="vertical" label="Organization">
                    {value.organization}
                </MaybeLabeledValue>
                <MaybeLabeledValue orientation="vertical" label="Provider">
                    {displayProvider(value.provider)}
                </MaybeLabeledValue>
            </>
        ),
        sortIconType: 'alpha'
    },
    {
        ...DATE_ADMINISTRATED,
        className: styles['date-header'],
        sortable: true,
        value: (value) => value.administratedOn,
        render: (value) => internalizeDate(value.administratedOn)
    },
    {
        ...VACCINE_ADMINISTRATED,
        className: styles['text-header'],
        sortable: true,
        value: (value) => value.administered,
        sortIconType: 'alpha'
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
    data?: PatientFileVaccinations[];
} & Omit<
    TableCardProps<PatientFileVaccinations>,
    'columnPreferencesKey' | 'defaultColumnPreferences' | 'columns' | 'data' | 'title'
>;

const InternalCard = ({ patient, sizing, data = [], ...remaining }: InternalCardProps) => (
    <TableCard
        title="Vaccinations"
        sizing={sizing}
        data={data}
        columns={columns}
        columnPreferencesKey="patient.file.vaccinations.preferences"
        defaultColumnPreferences={columnPreferences}
        actions={
            <Permitted permission={permissions.vaccination.add}>
                <LinkButton
                    secondary
                    sizing={sizing}
                    icon="add_circle"
                    href={`/nbs/api/profile/${patient}/vaccination`}>
                    Add vaccination
                </LinkButton>
            </Permitted>
        }
        {...remaining}
    />
);

type PatientFileVaccinationsCardProps = {
    provider: MemoizedSupplier<Promise<PatientFileVaccinations[]>>;
} & Omit<InternalCardProps, 'data'>;

const PatientFileVaccinationsCard = ({ provider, ...remaining }: PatientFileVaccinationsCardProps) => {
    return (
        <Suspense
            fallback={
                <LoadingOverlay>
                    <InternalCard {...remaining} />
                </LoadingOverlay>
            }>
            <Await resolve={provider.get()}>{(data) => <InternalCard data={data} {...remaining} />}</Await>
        </Suspense>
    );
};

export { PatientFileVaccinationsCard };
