import { Card } from 'design-system/card';
import { ValueField } from 'design-system/field';
import { AlertMessage } from 'design-system/message';
import { DataTable } from 'design-system/table';
import Papa from 'papaparse';
import { useId } from 'react';
import { ReportLayout } from '../layout/ReportLayout';
import { ReportExecutionResult } from 'generated';
import { marked } from 'marked';
import DOMPurify from 'dompurify';

import layoutStyes from '../layout/layout.module.scss';
import { NoDataRow } from 'design-system/table/NoDataRow';

const SIZING = 'medium';
const dateFormatter = Intl.DateTimeFormat('en-US', {
    year: 'numeric',
    month: 'numeric',
    day: 'numeric',
    hour: 'numeric',
    minute: 'numeric',
});
const formatTimestamp = (timestamp: string) => dateFormatter.format(new Date(timestamp)).replace(',', '');

const ResultDataPage = ({
    result: {
        query,
        timestamp,
        result: { subheader, description, content },
    },
    title,
    dataSourceName,
}: {
    result: ReportExecutionResult;
    title: string;
    dataSourceName: string;
}) => {
    const id = useId();
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
        <ReportLayout title={title} subtitle={subheader} noSkipLink={true}>
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
                        <section className="overflow-auto">
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
