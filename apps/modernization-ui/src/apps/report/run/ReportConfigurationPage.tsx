import React, { ReactNode } from 'react';
import { Button } from 'design-system/button';
import { permissions, Permitted } from 'libs/permission';
import { ReportLayout } from '../layout/ReportLayout';
import { ReportConfiguration } from 'generated';
import { BasicFilter } from './filters/basic/BasicFilter';
import { Card } from 'design-system/card';
import { STATE_FILTER_CODE } from './filters/basic/OptionSelectFilter';
import { CurrentStateProvider } from './filters/basic/useCurrentState';
import { AdvancedFilter, parseAdvancedFilterErrors } from './filters/advanced/AdvancedFilter';
import { ColumnSelector } from './columns/ColumnSelector';

import layoutStyles from '../layout/layout.module.scss';
import { Required } from 'design-system/entry';
import { InPageNavigation } from 'design-system/inPageNavigation';
import { SortSelector } from './columns/SortSelector';
import { NBS_MANAGE_REPORT_PAGE } from '../constants';
import { ReportExecuteForm } from './ReportRunPage';
import { FieldErrors, useFormState } from 'react-hook-form';
import { ValidationErrorBanner, ValidationErrorSection } from 'design-system/errors/ValidationError';
import { Heading } from 'components/heading';
import { usePermissions } from 'libs/permission/usePermissions';

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
        hasError: (errors: FieldErrors<ReportExecuteForm>, config: ReportConfiguration): boolean =>
            Object.entries(errors.basicFilter ?? {}).some(([k, _e]) =>
                filterTypes.includes(
                    config.basicFilters.find((f) => f.reportFilterUid === parseInt(k.slice(3)))?.filterType.type ?? ''
                )
            ),
        getValidationMessages: (
            errors: FieldErrors<ReportExecuteForm>,
            config: ReportConfiguration
        ): string[] | undefined =>
            Object.entries(errors.basicFilter ?? {})
                .filter(([k, _e]) =>
                    filterTypes.includes(
                        config.basicFilters.find((f) => f.reportFilterUid === parseInt(k.slice(3)))?.filterType.type ??
                            ''
                    )
                )
                .map(([_k, e]) => e?.value?.message)
                .filter((v) => typeof v === 'string'),
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
        hasError: (errors: FieldErrors<ReportExecuteForm>): boolean => !!errors?.advancedFilter?.message,
        getValidationMessages: (errors: FieldErrors<ReportExecuteForm>): string[] | undefined =>
            errors?.advancedFilter?.message ? parseAdvancedFilterErrors(errors.advancedFilter.message) : undefined,
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
        hasError: (errors: FieldErrors<ReportExecuteForm>): boolean => !!errors?.columns?.message,
        getValidationMessages: (errors: FieldErrors<ReportExecuteForm>): string[] | undefined =>
            errors?.columns?.message ? [errors.columns.message] : undefined,
    },
];

const ReportConfigurationPage = ({
    config,
    handleSubmit,
}: {
    config: ReportConfiguration;
    handleSubmit: (e: React.BaseSyntheticEvent, isExport: boolean) => void;
    error?: ReactNode;
}) => {
    const { errors } = useFormState<ReportExecuteForm>();
    const { allows } = usePermissions();
    const canRunReport = allows(permissions.reports.run);

    const sectionData = SECTIONS.filter(({ hasData }) => hasData(config));
    const sectionErrors = SECTIONS.filter(({ hasError }) => hasError(errors, config));

    const actions = (
        <>
            <Permitted permission={permissions.reports.export}>
                <Button
                    type={canRunReport ? 'button' : 'submit'}
                    onClick={(e) => handleSubmit(e, true)}
                    secondary={canRunReport}
                >
                    Export
                </Button>
            </Permitted>
            <Permitted permission={permissions.reports.run}>
                <Button type="submit" onClick={(e) => handleSubmit(e, false)}>
                    Run
                </Button>
            </Permitted>
        </>
    );

    return (
        <ReportLayout title={config.title} startHref={NBS_MANAGE_REPORT_PAGE} startPage="reports" actions={actions}>
            {!sectionData.length ? (
                <div className={layoutStyles.fullPageBlock}>
                    <Heading level={2}>No filters available</Heading>
                    <p className="maxw-mobile-lg text-center">
                        This report will return all available results, which might be a large dataset. If you have{' '}
                        <strong>Report Management</strong> permission, you can add filters by editing the report.
                    </p>
                    <div className="display-flex flex-row" style={{ gap: '0.5rem' }}>
                        {actions}
                    </div>
                </div>
            ) : (
                <>
                    <aside>
                        <InPageNavigation sections={sectionData.map(({ id, title }) => ({ id, label: title }))} />
                    </aside>
                    <div className={layoutStyles.columnContent}>
                        {!!sectionErrors.length && (
                            <ValidationErrorBanner level={2}>
                                {sectionErrors.map(({ id, title, getValidationMessages }) => (
                                    <ValidationErrorSection id={id} key={id} title={title}>
                                        {getValidationMessages(errors, config)!.map((message, i) => (
                                            <li key={`validation-message-${i}`}>{message}</li>
                                        ))}
                                    </ValidationErrorSection>
                                ))}
                            </ValidationErrorBanner>
                        )}
                        <CurrentStateProvider
                            stateFilter={config.basicFilters.find((f) =>
                                f.filterType.code?.startsWith(STATE_FILTER_CODE)
                            )}
                        >
                            {sectionData.map(({ id, title, Component }) => (
                                <Component key={id} config={config} id={id} title={title} />
                            ))}
                        </CurrentStateProvider>
                    </div>
                </>
            )}
        </ReportLayout>
    );
};

export { ReportConfigurationPage };
