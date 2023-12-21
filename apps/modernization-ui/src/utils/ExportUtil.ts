import { Filter } from 'filters';
import {
    ExportControllerService,
    InvestigationFilter as ExportInvestigation,
    LabReportFilter as ExportLabReport
} from '../generated';
import { OpenAPI } from '../generated/core/OpenAPI';
import { InvestigationFilter, LabReportFilter } from '../generated/graphql/schema';

export const downloadInvestigationSearchResultCsv = (investigationFilter: InvestigationFilter, token: string) => {
    ExportControllerService.generateInvestigationCsvUsingPost({
        filter: investigationFilter as ExportInvestigation,
        authorization: `Bearer ${token}`
    }).then((response) => {
        const url = window.URL.createObjectURL(new Blob([response]));
        const a = document.createElement('a');
        a.href = url;
        a.download = 'InvestigationSearchResults.csv';
        a.click();
    });
};

export const downloadInvestigationSearchResultPdf = (investigationFilter: InvestigationFilter, token: string) => {
    // auto generated methods dont allow direct conversion to blob
    fetch(`${OpenAPI.BASE}/investigation/export/pdf`, {
        method: 'POST',
        body: JSON.stringify(investigationFilter),
        headers: {
            Accept: 'application/pdf',
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`
        }
    })
        .then((response) => response.blob())
        .then((blob) => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'InvestigationSearchResults.pdf';
            a.click();
        });
};

export const downloadLabReportSearchResultCsv = (labReportFilter: LabReportFilter, token: string) => {
    ExportControllerService.generateLabReportCsvUsingPost({
        filter: labReportFilter as ExportLabReport,
        authorization: `Bearer ${token}`
    }).then((response) => {
        const url = window.URL.createObjectURL(new Blob([response]));
        const a = document.createElement('a');
        a.href = url;
        a.download = 'LabReportSearchResults.csv';
        a.click();
    });
};

export const downloadLabReportSearchResultPdf = (labReportFilter: LabReportFilter, token: string) => {
    // auto generated methods dont allow direct conversion to blob
    fetch(`${OpenAPI.BASE}/labreport/export/pdf`, {
        method: 'POST',
        body: JSON.stringify(labReportFilter),
        headers: {
            Accept: 'application/pdf',
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`
        }
    })
        .then((response) => response.blob())
        .then((blob) => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'LabReportSearchResults.pdf';
            a.click();
        });
};

export const downloadPageLibraryPdf = (authorization: string, search: string, filters: Filter[], sort?: string) => {
    // auto generated methods dont allow direct conversion to blob
    fetch(`${OpenAPI.BASE}/nbs/page-builder/api/v1/pages/pdf?sort=${sort ?? 'name,asc'}`, {
        method: 'POST',
        headers: {
            Accept: 'application/pdf',
            'Content-Type': 'application/json',
            Authorization: authorization
        },
        body: JSON.stringify({ search: search, filters: filters })
    })
        .then((response) => response.blob())
        .then((blob) => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'PageLibrary.pdf';
            a.click();
        });
};
