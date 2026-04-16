import { Button } from 'design-system/button';
import { permissions, Permitted } from 'libs/permission';
import { ReportRunLayout } from './layout/ReportRunLayout';
import { AdvancedFilter as AdvancedFilterType, BasicFilter as BasicFilterType, ReportConfiguration } from 'generated';
import { BasicFilter, BasicFilterComponent, BasicFilterProps } from './filters/BasicFilter';
import { TextFilter } from './filters/TextFilter';
import { Card } from 'design-system/card';
import { Field } from 'design-system/field';
import { Controller, useForm } from 'react-hook-form';

type ReportExecuteForm = {
    basicFilters?: BasicFilterType[];
    advancedFilter?: AdvancedFilterType;
    columns?: string[];
};

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
}: {
    config: ReportConfiguration;
    handleSubmit: (isExport: boolean) => void;
}) => {
    const basicFilters = config.filters.filter((filter) => filter.filterType.filterType?.startsWith('BAS_'));

    const form = useForm<ReportExecuteForm>({
        mode: 'onBlur',
    });

    return (
        <ReportRunLayout
            config={config}
            actions={
                <>
                    <Permitted permission={permissions.reports.run}>
                        <Button onClick={() => handleSubmit(false)}>Run</Button>
                    </Permitted>
                    <Permitted permission={permissions.reports.export}>
                        <Button onClick={() => handleSubmit(true)}>Export</Button>
                    </Permitted>
                </>
            }>
            <div>
                {basicFilters.length > 0 && (
                    <Card id="basic-filters" title="Basic Filters" collapsible={true}>
                        {basicFilters.map((filter, i) => {
                            const Filter = FILTER_TYPE_MAP[filter.filterType.filterType || ''] || TEMP_DEFAULT_FILTER;
                            return (
                                <Controller
                                    control={form.control}
                                    render={({ field }) => (
                                        <BasicFilter
                                            key={`basic_filter_${i}`}
                                            FilterComponent={Filter}
                                            filter={filter}
                                            columns={config.reportColumns ?? []}
                                            {...field}
                                        />
                                    )}
                                />
                            );
                        })}
                    </Card>
                )}
                <p>Config:</p>
                <p>{config ? JSON.stringify(config) : 'loading'}</p>
            </div>
        </ReportRunLayout>
    );
};

export { ReportConfigurationPage };
