import React from 'react';
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

const BASIC_SECTIONS = [
    {
        title: 'Time',
        id: 'basic-time',
        filterTypes: ['BAS_TIM_RANGE', 'BAS_TIM_RANGE_LIST', 'BAS_MM_YYYY_RANGE', 'BAS_TIM_RANGE_CUSTOM', 'BAS_DAYS'],
    },
    {
        title: 'Condition',
        id: 'basic-condition',
        filterTypes: ['BAS_CON_LIST', 'BAS_CVG_LIST'],
    },
    {
        title: 'Geographic area',
        id: 'basic-geography',
        filterTypes: ['BAS_JUR_LIST'],
    },
    {
        title: 'Other filters',
        id: 'basic-other',
        filterTypes: ['BAS_TXT', 'BAS_STD_HIV_WRKR'],
    },
];

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
            <Card id={id} title={title} collapsible={true}>
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
            </Card>
        ),
    },
];

const ReportConfigurationPage = ({
    config,
    handleSubmit,
}: {
    config: ReportConfiguration;
    handleSubmit: (e: React.BaseSyntheticEvent, isExport: boolean) => void;
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
