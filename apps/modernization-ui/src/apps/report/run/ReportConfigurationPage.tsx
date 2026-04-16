import React from 'react';
import { Button } from 'design-system/button';
import { permissions, Permitted } from 'libs/permission';
import { ReportRunLayout } from './layout/ReportRunLayout';
import { ReportConfiguration } from 'generated';
import { BasicFilter, BasicFilterComponent, BasicFilterProps } from './filters/BasicFilter';
import { TextFilter } from './filters/TextFilter';
import { Card } from 'design-system/card';
import { Field } from 'design-system/field';
import { Control, Controller } from 'react-hook-form';
import { ReportExecuteForm } from './ReportRunPage';
import { validateRequiredRule } from 'validation/entry';

const FILTER_TYPE_MAP: Record<string, BasicFilterComponent> = {
    BAS_TXT: TextFilter,
};

const TEMP_DEFAULT_FILTER: BasicFilterComponent = ({ filter, id, ...remaining }: BasicFilterProps) => (
    <Field htmlFor={id} {...remaining}>
        <p>{JSON.stringify(filter)}</p>
    </Field>
);

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
                            const Impl = FILTER_TYPE_MAP[filter.filterType.filterType || '']
                            const Filter = Impl || TEMP_DEFAULT_FILTER;
                            // Don't validate required-ness for uninmplemented filtrs
                            const isRequired = !!Impl && (filter.minValueCount ?? 1) > 0;
                            return (
                                <Controller
                                    key={`basic_filter_${i}`}
                                    control={formControl}
                                    name={`basicFilter.${i}`}
                                    rules={isRequired ? validateRequiredRule(filter.filterType) : undefined}
                                    // ignoring the ref as it does not pass down well and isn't critical
                                    // eslint-disable-next-line @typescript-eslint/no-unused-vars
                                    render={({ field: { ref, ...remaining }, fieldState: { error } }) => (
                                        <BasicFilter
                                            FilterComponent={Filter}
                                            filter={filter}
                                            columns={config.reportColumns ?? []}
                                            error={error?.message}
                                            {...remaining}
                                        />
                                    )}
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
