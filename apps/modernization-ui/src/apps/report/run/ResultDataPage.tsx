import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { DataTable } from 'design-system/table';
import { ReportResult } from 'generated';
import Papa from 'papaparse';
import { useId } from 'react';

const ResultDataPage = ({ result: { header, subheader, description, content } }: { result: ReportResult }) => {
    const id = useId();
    const { data, errors, meta } = Papa.parse<Record<string, string>>(content, { header: true, skipEmptyLines: true });

    return (
        <main>
            {(errors?.length ?? 0) > 0 && <AlertBanner type="error">{JSON.stringify(errors)}</AlertBanner>}
            <h1>{header}</h1>
            <h2>{subheader}</h2>
            <section>{description}</section>
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
        </main>
    );
};

export { ResultDataPage };
