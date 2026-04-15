import { Button } from 'design-system/button';
import { permissions, Permitted } from 'libs/permission';
import { ReportRunLayout } from './layout/ReportRunLayout';
import { FilterConfiguration, ReportConfiguration } from 'generated';
import { BasicFilter } from './filters/BasicFilter';
import { TextFilter } from './filters/TextFilter';
import { ReactComponentLike } from 'prop-types';
import { Card } from 'design-system/card';
import { Field } from 'design-system/field';

const FILTER_TYPE_MAP: Record<string, ReactComponentLike> = {
    BAS_TEXT: TextFilter,
};

const TEMP_DEFAULT_FILTER = ({ filter }: { filter: FilterConfiguration }) => (
    <Field htmlFor="dummy">
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
                                <BasicFilter
                                    key={`basic_filter_${i}`}
                                    FilterComponent={Filter}
                                    filter={filter}
                                    columns={config.reportColumns ?? []}
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
