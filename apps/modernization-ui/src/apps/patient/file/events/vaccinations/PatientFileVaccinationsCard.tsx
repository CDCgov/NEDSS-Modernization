import { Suspense, useState } from 'react';
import { Await } from 'react-router';
import { internalizeDate, internalizeDateTime } from 'date';
import { MemoizedSupplier } from 'libs/supplying';
import { displayProvider } from 'libs/provider';
import { LoadingOverlay } from 'libs/loading';
import { permissions, Permitted } from 'libs/permission';
import { ClassicModalButton } from 'libs/classic';
import { Column } from 'design-system/table';
import { TableCard, TableCardProps } from 'design-system/card';
import { ColumnPreference } from 'design-system/table/preferences';
import { MaybeLabeledValue } from 'design-system/value';
import { Associations } from 'libs/events/investigations/associated';
import { PatientFileVaccinations } from './vaccinations';

import styles from './patient-file-vaccinations.module.scss';
import { Shown } from 'conditional-render';
import { exists } from 'utils';
import { displayNoData } from 'design-system/data';

const EVENT_ID = { id: 'local', name: 'Event ID' };
const DATE_RECEIVED = { id: 'created-on', name: 'Date created' };
const ORG_PROV = { id: 'organization-provider', name: 'Organization/provider' };
const DATE_ADMINISTERED = { id: 'date-administered', name: 'Date administered' };
const VACCINE_ADMINISTERED = { id: 'vaccine-administered', name: 'Vaccine administered' };
const ASSOCIATED_WITH = { id: 'associated-with', name: 'Associated with' };

const columnPreferences: ColumnPreference[] = [
    { ...EVENT_ID },
    { ...DATE_RECEIVED, moveable: true, toggleable: true },
    { ...ORG_PROV, moveable: true, toggleable: true },
    { ...DATE_ADMINISTERED, moveable: true, toggleable: true },
    { ...VACCINE_ADMINISTERED, moveable: true, toggleable: true },
    { ...ASSOCIATED_WITH, moveable: true, toggleable: true }
];

const columns = (onClose: () => void): Column<PatientFileVaccinations>[] => [
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
                url={`/nbs/api/profile/${value.patient}/vaccination/${value.id}`}
                onClose={onClose}>
                {value.local}
            </ClassicModalButton>
        )
    },
    {
        ...DATE_RECEIVED,
        className: styles['date-time-header'],
        sortable: true,
        value: (value) => value.createdOn,
        render: (value) => internalizeDateTime(value.createdOn),
        sortIconType: 'numeric'
    },
    {
        ...ORG_PROV,
        sortable: true,
        className: styles['text-header'],
        value: (value) => value.organization ?? displayProvider(value.provider),
        render: (value) => (
            <Shown when={exists(value.organization) || exists(value.provider)} fallback={displayNoData()}>
                <MaybeLabeledValue orientation="vertical" label="Organization">
                    {value.organization}
                </MaybeLabeledValue>
                <MaybeLabeledValue orientation="vertical" label="Provider">
                    {displayProvider(value.provider)}
                </MaybeLabeledValue>
            </Shown>
        )
    },
    {
        ...DATE_ADMINISTERED,
        className: styles['date-administered-header'],
        sortable: true,
        value: (value) => value.administeredOn,
        render: (value) => internalizeDate(value.administeredOn)
    },
    {
        ...VACCINE_ADMINISTERED,
        sortable: true,
        value: (value) => value.administered,
        render: (value) => <strong>{value.administered}</strong>
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
    data?: PatientFileVaccinations[];
    onClose: () => void;
} & Omit<
    TableCardProps<PatientFileVaccinations>,
    'columnPreferencesKey' | 'defaultColumnPreferences' | 'columns' | 'data' | 'title'
>;

const InternalCard = ({ patient, sizing, data = [], onClose, ...remaining }: InternalCardProps) => {
    return (
        <TableCard
            title="Vaccinations"
            sizing={sizing}
            data={data}
            columns={columns(onClose)}
            columnPreferencesKey="patient.file.vaccinations.preferences"
            defaultColumnPreferences={columnPreferences}
            actions={
                <Permitted permission={permissions.vaccination.add}>
                    <ClassicModalButton
                        url={`/nbs/api/profile/${patient}/vaccination`}
                        icon="add_circle"
                        secondary
                        sizing={sizing}
                        onClose={onClose}>
                        Add vaccination
                    </ClassicModalButton>
                </Permitted>
            }
            {...remaining}
        />
    );
};

type PatientFileVaccinationsCardProps = {
    provider: MemoizedSupplier<Promise<PatientFileVaccinations[]>>;
} & Omit<InternalCardProps, 'data' | 'onClose'>;

const PatientFileVaccinationsCard = ({ provider, ...remaining }: PatientFileVaccinationsCardProps) => {
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

export { PatientFileVaccinationsCard };
