import React, { ReactNode } from 'react';
import { Button } from 'design-system/button';
import { permissions, Permitted } from 'libs/permission';
import { ReportLayout } from '../layout/ReportLayout';
import { ReportConfiguration } from 'generated';
import { BasicFilter } from './filters/basic/BasicFilter';
import { Card } from 'design-system/card';
import { STATE_FILTER_CODE } from './filters/basic/OptionSelectFilter';
import { CurrentStateProvider } from './filters/basic/useCurrentState';
import { AdvancedFilter } from './filters/advanced/AdvancedFilter';
import { ColumnSelector } from './columns/ColumnSelector';

import layoutStyles from '../layout/layout.module.scss';
import { Required } from 'design-system/entry';
import { InPageNavigation } from 'design-system/inPageNavigation';
import { SortSelector } from './columns/SortSelector';
import { BASIC_SECTIONS } from '../constants';

const SECTIONS = [
    ...BASIC_SECTIONS.map(({ title, id, filterTypes }) => ({
        title,
        id,
        hasData: (config: ReportConfiguration) =>
            config.basicFilters.some((f) => filterTypes.includes(f.filterType.type!)),
        Component: ({ config, id, title }: { config: ReportConfiguration; id: string; title: string }) => (
            <Card id={id} title={title} collapsible={true}>
                {config.basicFilters
                    .filter((filter) => filterTypes.includes(filter.filterType.type!))
                    .map((filter, i) => (
                        <BasicFilter key={`basic_filter_${i}`} filter={filter} columns={config.columns} />
                    ))}
            </Card>
        ),
    })),
    {
        title: 'Advanced filter',
        id: 'advanced-filter',
        hasData: (config: ReportConfiguration) => !!config.advancedFilter,
        Component: ({ config, id, title }: { config: ReportConfiguration; id: string; title: string }) => (
            <Card
                id={id}
                title={title}
                collapsible={true}
                subtext={
                    <span>
                        Add <strong>rules</strong> and <strong>rule groups</strong> to narrow or broaden your results.
                        Use <strong>AND</strong> to require all connected rules or groups to match, or{' '}
                        <strong>OR</strong> to require only one to match. Your advanced filter combines with your basic
                        filters using <strong>AND</strong> logic. The <strong>WHERE</strong> clause preview shows your
                        advanced filter as you build it.
                    </span>
                }
                contentMaxWidth="widescreen"
            >
                <AdvancedFilter filter={config.advancedFilter!} columns={config.columns} />
            </Card>
        ),
    },
    {
        title: 'Column selection',
        id: 'column-selection',
        hasData: (config: ReportConfiguration) => config.library.allowColumnSelection,
        Component: ({ config, id, title }: { config: ReportConfiguration; id: string; title: string }) => (
            <Card
                id={id}
                title={title}
                required={true}
                subtext="Select the column variables you would like to include in this report."
                actions={<Required />}
                collapsible={true}
            >
                <ColumnSelector columns={config.columns} defaultColumns={config.defaultColumnUids} />
                <SortSelector
                    columns={config.columns}
                    defaultSort={config.defaultSort}
                    defaultColumns={config.defaultColumnUids}
                />
            </Card>
        ),
    },
];

const ReportConfigurationPage = ({
    config,
    handleSubmit,
    error,
}: {
    config: ReportConfiguration;
    handleSubmit: (e: React.BaseSyntheticEvent, isExport: boolean) => void;
    error?: ReactNode;
}) => {
    const sectionData = SECTIONS.filter(({ hasData }) => hasData(config));

    return (
        <ReportLayout
            title={config.title}
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
            <aside>
                <InPageNavigation sections={sectionData.map(({ id, title }) => ({ id, label: title }))} />
            </aside>
            <form className={layoutStyles.columnContent}>
                {error}
                <CurrentStateProvider
                    stateFilter={config.basicFilters.find((f) => f.filterType.code?.startsWith(STATE_FILTER_CODE))}
                >
                    {sectionData.map(({ id, title, Component }) => (
                        <Component key={id} config={config} id={id} title={title} />
                    ))}
                </CurrentStateProvider>
            </form>
        </ReportLayout>
    );
};

export { ReportConfigurationPage };
