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

const SIZING = 'medium';
const dateFormatter = Intl.DateTimeFormat('en-US', {
    dateStyle: 'short',
    timeStyle: 'short',
});

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
    const { data, errors, meta } = Papa.parse<Record<string, string>>(content, { header: true, skipEmptyLines: true });

    const formattedTime = dateFormatter.format(new Date(timestamp));
    const descriptionHtml = description ? DOMPurify.sanitize(marked.parse(description) as string) : '';

    return (
        <ReportLayout title={title} subtitle={subheader} noSkipLink={true}>
            <div className={layoutStyes.columnContent}>
                {(errors?.length ?? 0) > 0 && (
                    <AlertMessage type="error" title="There were errors parsing the result:">
                        <ul>
                            {errors.map((e) => (
                                <li>{e.message}</li>
                            ))}
                        </ul>
                    </AlertMessage>
                )}
                <Card id="report-details" title="Report details">
                    <ValueField sizing={SIZING} label="Data source">
                        {dataSourceName}
                    </ValueField>
                    <ValueField sizing={SIZING} label="Description">
                        <div dangerouslySetInnerHTML={{ __html: descriptionHtml }} className="text-wrap" />
                    </ValueField>
                    <ValueField sizing={SIZING} label="Report run date">
                        {formattedTime}
                    </ValueField>
                </Card>
                <Card id="report-data" title={title}>
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
                            />
                        </section>
                    )}
                </Card>

                <Card id="report-criteria" title="Report criteria">
                    <ValueField sizing={SIZING} label="Base SQL query">
                        {query}
                    </ValueField>
                </Card>
            </div>
        </ReportLayout>
    );
};

export { ResultDataPage };
