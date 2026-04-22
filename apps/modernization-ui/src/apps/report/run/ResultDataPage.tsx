import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { DataTable } from 'design-system/table';
import { ReportResult } from 'generated';
import Papa from 'papaparse';
import { useId } from 'react';

import styles from './result-data-table.module.scss';
import classNames from 'classnames';

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
                <section className={classNames('overflow-auto', styles.table)}>
                    <DataTable
                        id={id}
                        columns={meta.fields.map((c) => ({
                            id: c,
                            name: c,
                            value: (row: Record<string, string>) => row[c],
                        }))}
                        data={data}
                    />
                </section>
            )}
        </main>
    );
};

export { ResultDataPage };
