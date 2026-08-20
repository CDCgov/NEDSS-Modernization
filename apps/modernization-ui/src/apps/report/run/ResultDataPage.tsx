import { useEffect, useId } from 'react';

import DOMPurify from 'dompurify';
import { marked } from 'marked';
import { parse } from 'papaparse';
import { LoaderFunction, useLoaderData } from 'react-router';

import { Card } from 'design-system/card';
import { ValueField } from 'design-system/field';
import { AlertMessage } from 'design-system/message';
import { DataTable } from 'design-system/table';
import { NoDataRow } from 'design-system/table/NoDataRow';

import { LOCAL_STORAGE_RESULT_PREFIX } from '../constants';
import { ReportLayout } from '../layout/ReportLayout';
import layoutStyes from '../layout/layout.module.scss';
import { fetchStoredData } from '../utils/openNewTab';

const SIZING = 'medium';
const dateFormatter = Intl.DateTimeFormat('en-US', {
    year: 'numeric',
    month: 'numeric',
    day: 'numeric',
    hour: 'numeric',
    minute: 'numeric',
});
const formatTimestamp = (timestamp: string) => dateFormatter.format(new Date(timestamp)).replace(',', '');

type Result = {
    result: {
        content: string;
        description?: string;
        context_header?: string;
        timestamp: string;
        query: string;
    };
    title: string;
    dataSourceName: string;
};

const loadReportResult: LoaderFunction = async (request): Promise<Result | null> => {
    const { resultId } = request.params;
    const resultKey = `${LOCAL_STORAGE_RESULT_PREFIX}.${resultId}`;
    return fetchStoredData<Result>(resultKey);
};

const ResultDataPage = () => {
    const id = useId();
    const result = useLoaderData();

    // Display a generic browser warning about losing info before navigating away.
    // Requires the user to have interacted with the page somewhat for it to trigger.
    useEffect(() => {
        if (result) {
            const handler = (event: BeforeUnloadEvent) => {
                event.preventDefault();
                // Required by Chrome and newer specifications
                event.returnValue = '';
            };
            window.addEventListener('beforeunload', handler);

            return () => window.removeEventListener('beforeunload', handler);
        }
    });

    if (result === null) {
        return (
            <AlertMessage type="warning" title="No result found" level={1} className="margin-2">
                <p>This can happen if the page was refreshed. Re-run the report to retrieve a new result.</p>
                <p>If this persists, contact your system administrator.</p>
            </AlertMessage>
        );
    }

    const {
        result: { content, description, context_header, timestamp, query },
        title,
        dataSourceName,
    } = result;

    const { data, errors, meta } = parse<Record<string, string>>(content, {
        header: true,
        skipEmptyLines: true,
        delimiter: ',',
    });

    const formattedTime = formatTimestamp(timestamp);
    // headers can't include new lines, so un-serialize for display
    const descriptionHtml = description
        ? DOMPurify.sanitize(marked.parse(description.trim().replaceAll('%n', '\n')) as string)
        : '';

    return (
        <ReportLayout title={title} subtitle={context_header} noSkipLink={true}>
            <div className={layoutStyes.columnContent}>
                {(errors?.length ?? 0) > 0 && (
                    <AlertMessage type="error" title="There were errors parsing the result:">
                        <ul>
                            {errors.map((e, i) => (
                                <li key={`error-${i}`}>{e.message}</li>
                            ))}
                        </ul>
                    </AlertMessage>
                )}

                <Card id="report-details" title="Report details" collapsible={true} open={false}>
                    <ValueField sizing={SIZING} label="Data source">
                        {dataSourceName}
                    </ValueField>
                    <ValueField sizing={SIZING} label="Report run date">
                        {formattedTime}
                    </ValueField>
                    <ValueField sizing={SIZING} label="Description">
                        {descriptionHtml && (
                            <div dangerouslySetInnerHTML={{ __html: descriptionHtml }} className="text-wrap" />
                        )}
                    </ValueField>
                </Card>

                <Card id="report-criteria" title="Report criteria" collapsible={true} open={false}>
                    <ValueField sizing={SIZING} label="Base SQL query WHERE">
                        {/* The uswds text-pre-line forces a sans font instead of respecting mono */}
                        {query && (
                            <span style={{ whiteSpace: 'pre-line' }} className="font-mono-xs">
                                {query}
                            </span>
                        )}
                    </ValueField>
                </Card>

                <Card
                    id="report-result"
                    title="Report result"
                    flair={`(${data.length} row${data.length === 1 ? '' : 's'})`}
                >
                    {data.length === 0 && (
                        <AlertMessage type="information">
                            <p className="font-sans-md margin-0 margin-top-1">No results match your criteria.</p>
                        </AlertMessage>
                    )}
                    {meta.fields && (
                        // set tab-index to ensure there's a focusable item on the page/enable keyboard scroll
                        <section className="overflow-auto" tabIndex={0}>
                            <DataTable
                                id={id}
                                fullWidth={false}
                                columns={meta.fields.map((colName) => ({
                                    id: colName,
                                    name: colName,
                                    value: (row: Record<string, string>) => row[colName],
                                }))}
                                data={data}
                                onEmpty={(columns: number) => <NoDataRow columns={columns}>No data found.</NoDataRow>}
                            />
                        </section>
                    )}
                </Card>
            </div>
        </ReportLayout>
    );
};

export { ResultDataPage, loadReportResult };
