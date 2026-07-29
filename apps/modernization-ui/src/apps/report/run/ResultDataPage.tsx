import { Card } from 'design-system/card';
import { ValueField } from 'design-system/field';
import { AlertMessage } from 'design-system/message';
import { DataTable } from 'design-system/table';
import Papa from 'papaparse';
import { useEffect, useState } from 'react';
import { ReportLayout } from '../layout/ReportLayout';
import { ReportExecutionResult } from 'generated';
import { marked } from 'marked';
import DOMPurify from 'dompurify';
import { NoDataRow } from 'design-system/table/NoDataRow';
import { LoadingBlock } from 'libs/loading/block';
import { useParams } from 'react-router';

import layoutStyes from '../layout/layout.module.scss';

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
    result: ReportExecutionResult;
    title: string;
    dataSourceName: string;
};

const ResultDataPage = () => {
    const params = useParams();
    const resultId = params.resultId ?? '0';
    const [result, setResult] = useState<Result | undefined | null>(undefined);

    useEffect(() => {
        const rawData = localStorage.getItem(`reportResult.${resultId}`);
        if (rawData) {
            setResult(JSON.parse(rawData));
            // clean result up from local storage now that it's been viewed
            localStorage.removeItem(`reportResult.${resultId}`);

            // double-mount in dev mode can cause wiping out data if don't check defined-ness
        } else if (rawData === undefined) {
            setResult(null);
        }
    }, []);

    if (result === undefined) {
        return <LoadingBlock />;
    } else if (result === null) {
        return (
            <AlertMessage type="warning" title="No result found" level={1} className="margin-2">
                <p>This can happen if the page was refreshed. Re-run the report to retrieve a new result.</p>
                <p>If this persists, contact your system administrator</p>
            </AlertMessage>
        );
    }

    const {
        result: {
            result: { content, description, context_header },
            timestamp,
            query,
        },
        title,
        dataSourceName,
    } = result;

    const { data, errors, meta } = Papa.parse<Record<string, string>>(content, {
        header: true,
        skipEmptyLines: true,
        delimiter: ',',
    });

    const formattedTime = formatTimestamp(timestamp);
    const descriptionHtml = description ? DOMPurify.sanitize(marked.parse(description.trim()) as string) : '';

    const styledQuery = query
        .replace(' FROM ', '\nFROM ')
        .replace(' WHERE ', '\nWHERE ')
        .replace(' ORDER BY ', '\nORDER BY ');

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
                <Card id="report-details" title="Report details">
                    <ValueField sizing={SIZING} label="Data source">
                        {dataSourceName}
                    </ValueField>
                    <ValueField sizing={SIZING} label="Description">
                        {descriptionHtml && (
                            <div dangerouslySetInnerHTML={{ __html: descriptionHtml }} className="text-wrap" />
                        )}
                    </ValueField>
                    <ValueField sizing={SIZING} label="Report run date">
                        {formattedTime}
                    </ValueField>
                </Card>
                <Card
                    id="report-result"
                    title="Report result"
                    flair={`(${data.length} row${data.length === 1 ? '' : 's'})`}
                >
                    {meta.fields && (
                        // set tab-index to ensure there's a focusable item on the page/enable keyboard scroll
                        <section className="overflow-auto" tabIndex={0}>
                            <DataTable
                                id={resultId}
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

                <Card id="report-criteria" title="Report criteria">
                    <ValueField sizing={SIZING} label="Base SQL query">
                        {/* The uswds text-pre-line forces a sans font instead of respecting mono */}
                        <span style={{ whiteSpace: 'pre-line' }} className="font-mono-xs">
                            {styledQuery}
                        </span>
                    </ValueField>
                </Card>
            </div>
        </ReportLayout>
    );
};

export { ResultDataPage };
