import { Investigation } from 'generated/graphql/schema';
import { internalizeDate } from 'date';
import { SelectableResolver } from 'options';
import { Column, DataTable } from 'design-system/table';
import { ColumnPreference, useColumnPreferences } from 'design-system/table/preferences';
import { withPatient, displayProfileLink, displayGender } from 'apps/search/basic';
import {
    displayInvestigationLink,
    displayInvestigator,
    displayNotificationStatus,
    displayStatus,
    getPatient
} from 'apps/search/investigation/result';

const LEGAL_NAME = { id: 'legalName', name: 'Legal name' };
const DATE_OF_BIRTH = { id: 'birthday', name: 'Date of birth' };
const SEX = { id: 'sex', name: 'Current sex' };
const PATIENT_ID = { id: 'patientid', name: 'Patient ID' };

const CONDITION = { id: 'condition', name: 'Condition' };
const START_DATE = { id: 'startDate', name: 'Start date' };
const JURSIDICTION = { id: 'jurisdiction', name: 'Jurisdiction' };
const INVESTIGATOR = { id: 'investigator', name: 'Investigator' };
const INVESTIGATION_ID = { id: 'investigationId', name: 'Investigation ID' };
const STATUS = { id: 'status', name: 'Status' };
const NOTIFICATION = { id: 'notification', name: 'Notification' };

const columns = (notificationStatusResolver: SelectableResolver): Column<Investigation>[] => [
    {
        ...LEGAL_NAME,
        fixed: true,
        sortable: true,
        render: withPatient(getPatient, displayProfileLink)
    },
    {
        ...DATE_OF_BIRTH,
        sortable: true,
        render: (row) => internalizeDate(getPatient(row)?.birthTime)
    },
    {
        ...SEX,
        sortable: true,
        render: withPatient(getPatient, displayGender)
    },
    {
        ...PATIENT_ID,
        sortable: true,
        render: (row) => getPatient(row)?.shortId
    },
    {
        ...CONDITION,
        sortable: true,
        render: (row) => displayInvestigationLink(row)
    },
    {
        ...START_DATE,
        sortable: true,
        render: (row) => internalizeDate(row.startedOn)
    },
    {
        ...JURSIDICTION,
        sortable: true,
        render: (row) => row.jurisdictionCodeDescTxt
    },
    {
        ...INVESTIGATOR,
        sortable: true,
        render: displayInvestigator
    },
    {
        ...INVESTIGATION_ID,
        sortable: true,
        render: (row) => row.localId
    },
    {
        ...STATUS,
        sortable: true,
        render: displayStatus
    },
    {
        ...NOTIFICATION,
        sortable: true,
        render: displayNotificationStatus(notificationStatusResolver)
    }
];

const preferences: ColumnPreference[] = [
    { ...LEGAL_NAME },
    { ...DATE_OF_BIRTH },
    { ...SEX },
    { ...PATIENT_ID },
    { ...CONDITION, moveable: true, toggleable: true },
    { ...START_DATE, moveable: true, toggleable: true },
    { ...JURSIDICTION, moveable: true, toggleable: true },
    { ...INVESTIGATOR, moveable: true, toggleable: true },
    { ...INVESTIGATION_ID, moveable: true, toggleable: true },
    { ...STATUS, moveable: true, toggleable: true },
    { ...NOTIFICATION, moveable: true, toggleable: true }
];

type Props = {
    results: Investigation[];
    notificationStatusResolver: SelectableResolver;
};

const InvestigationSearchResultsTable = ({ results, notificationStatusResolver }: Props) => {
    const { apply } = useColumnPreferences();

    return (
        <DataTable<Investigation>
            id="investigation-search-results"
            columns={apply(columns(notificationStatusResolver))}
            data={results}
            options={{ stickyHeaders: true }}
        />
    );
};

export { InvestigationSearchResultsTable, preferences };
