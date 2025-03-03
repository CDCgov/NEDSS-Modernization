/* eslint-disable */

// import { ActivityLog } from '../schema';
import { Column, DataTable } from 'design-system/table';
import { ColumnPreference, useColumnPreferences } from 'design-system/table/preferences';
import { Sizing } from "design-system/field";

import styles from '../../activity-log-search-result-table.module.scss';
import {ActivityFilterEntry} from "../../../ActivityLogFormTypes";

const PROCESSED_TIME = { id: 'processedTime', name: 'Processed Time' };
// const RECORD_STATUS = { id: 'recordStatusCd', name: 'Record Status' };
const ALGORITHM_NAME = { id: 'algorithmName', name: 'Algorithm Name' };
const ACTION = { id: 'algorithmAction', name: 'Action' };
const MESSAGE_ID = { id: 'messageId', name: 'Message ID' };
const REPORTING_FACILITY = { id: 'sourceNm', name: 'Reporting Facility' };
const PATIENT_NAME = { id: 'patientNm', name: 'Patient Name' };
const OBSERVATION_ID = { id: 'observationId', name: 'Observation Id' };
const ACCESSION_NBR = { id: 'accessionNbr', name: 'Accession#' };
// const EXCEPTION_TEXT = { id: 'exceptionTxt', name: 'Exception Text' };

console.log("Inside Results Table...");

const columns: Column<ActivityFilterEntry>[] = [
    {
        ...PROCESSED_TIME,
        fixed: true,
        sortable: true,
        className: styles['col-processed-time'],
        render: (row) => row.processedTime,
    },
    // {
    //     ...RECORD_STATUS,
    //     sortable: true,
    //     className: styles['col-record-status'],
    //     render: (row) => row.status?.toString(),
    // },
    {
        ...ALGORITHM_NAME,
        sortable: true,
        className: styles['col-algorithm-name'],
        render: (row) => row.algorithmName,
    },
    {
        ...ACTION,
        sortable: true,
        className: styles['col-action'],
        render: (row) => row.action,
    },
    {
        ...MESSAGE_ID,
        sortable: true,
        className: styles['col-message-id'],
        render: (row) => row.messageId,
    },
    {
        ...REPORTING_FACILITY,
        sortable: true,
        className: styles['col-reporting-facility'],
        render: (row) => row.sourceNm?.name,
    },
    {
        ...PATIENT_NAME,
        sortable: true,
        className: styles['col-patient-name'],
        render: (row) => row.entityNm,
    },
    {
        ...OBSERVATION_ID,
        sortable: true,
        className: styles['col-observation-id'],
        render: (row) => row.observationId,
    },
    {
        ...ACCESSION_NBR,
        sortable: true,
        className: styles['col-accession-nbr'],
        render: (row) => row.accessionNumber,
    },
    // {
    //     ...EXCEPTION_TEXT,
    //     sortable: true,
    //     className: styles['col-exception-text'],
    //     render: (row) => row.exceptionText,
    // },
];

const preferences: ColumnPreference[] = [
    { ...PROCESSED_TIME },
    // { ...RECORD_STATUS, moveable: true, toggleable: true },
    { ...ALGORITHM_NAME, moveable: true, toggleable: true },
    { ...ACTION, moveable: true, toggleable: true },
    { ...MESSAGE_ID, moveable: true, toggleable: true },
    { ...REPORTING_FACILITY, moveable: true, toggleable: true },
    { ...PATIENT_NAME, moveable: true, toggleable: true },
    { ...OBSERVATION_ID, moveable: true, toggleable: true },
    { ...ACCESSION_NBR, moveable: true, toggleable: true },
    // { ...EXCEPTION_TEXT, moveable: true, toggleable: true },
];

type Props = {
    results: ActivityFilterEntry[];
    sizing?: Sizing;
};

const ElrActivityLogSearchResultsTable = ({ results, sizing }: Props) => {
    const { apply } = useColumnPreferences();

    return (
        <DataTable<ActivityFilterEntry>
            id="activitylog-search-results"
            className={styles.activity_results}
            columns={apply(columns)}
            data={results}
            sizing={sizing}
            rowHeightConstrained={false}
        />
    );
};

export { ElrActivityLogSearchResultsTable, preferences };
