import React from 'react';
import { Button } from 'design-system/button';
import { permissions, Permitted } from 'libs/permission';
import { ReportRunLayout } from './layout/ReportRunLayout';
import { ReportConfiguration } from 'generated';
import { BasicFilter } from './filters/BasicFilter';
import { Card } from 'design-system/card';
import { Control } from 'react-hook-form';
import { ReportExecuteForm } from './ReportRunPage';

const ReportConfigurationPage = ({
    config,
    handleSubmit,
    formControl,
}: {
    config: ReportConfiguration;
    handleSubmit: (e: React.BaseSyntheticEvent, isExport: boolean) => void;
    formControl: Control<ReportExecuteForm>;
}) => {
    const basicFilters = config.filters.filter((filter) => filter.filterType.filterType?.startsWith('BAS_'));

    return (
        <ReportRunLayout
            config={config}
            actions={
                <>
                    <Permitted permission={permissions.reports.run}>
                        <Button onClick={(e) => handleSubmit(e, false)}>Run</Button>
                    </Permitted>
                    <Permitted permission={permissions.reports.export}>
                        <Button onClick={(e) => handleSubmit(e, true)}>Export</Button>
                    </Permitted>
                </>
            }>
            <form>
                {basicFilters.length > 0 && (
                    <Card id="basic-filters" title="Basic Filters" collapsible={true}>
                        {basicFilters.map((filter, i) => {
                            return (
                                <BasicFilter
                                    key={`basic_filter_${i}`}
                                    fieldIndex={i}
                                    filter={filter}
                                    columns={config.reportColumns ?? []}
                                    formControl={formControl}
                                />
                            );
                        })}
                    </Card>
                )}
                <details>
                    <summary>
                        <p>Config:</p>
                    </summary>
                    <pre>{config ? JSON.stringify(config, null, 2) : 'loading'}</pre>
                </details>
            </form>
        </ReportRunLayout>
    );
};

export { ReportConfigurationPage };
