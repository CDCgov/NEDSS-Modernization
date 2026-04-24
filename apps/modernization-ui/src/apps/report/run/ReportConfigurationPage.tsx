import React from 'react';
import { Button } from 'design-system/button';
import { permissions, Permitted } from 'libs/permission';
import { ReportRunLayout } from './layout/ReportRunLayout';
import { ReportConfiguration } from 'generated';
import { BasicFilter } from './filters/BasicFilter';
import { Card } from 'design-system/card';
import { STATE_FILTER_CODE } from './filters/OptionSelectFilter';
import { CurrentStateProvider } from './filters/useCurrentState';

const ReportConfigurationPage = ({
    config,
    handleSubmit,
}: {
    config: ReportConfiguration;
    handleSubmit: (e: React.BaseSyntheticEvent, isExport: boolean) => void;
}) => {
    const basicFilters = config.basicFilters;
    // the state drives other filter options, so need to pull it out
    const stateFilter = config.basicFilters.find((f) =>
        f.filterType.code?.startsWith(STATE_FILTER_CODE)
    );

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
            }
        >
            <form>
                {basicFilters.length > 0 && (
                    <CurrentStateProvider stateFilter={stateFilter}>
                        <Card id="basic-filters" title="Basic Filters" collapsible={true}>
                            {basicFilters.map((filter, i) => (
                                <BasicFilter
                                    key={`basic_filter_${i}`}
                                    filter={filter}
                                    columns={config.reportColumns ?? []}
                                />
                            ))}
                        </Card>
                    </CurrentStateProvider>
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
