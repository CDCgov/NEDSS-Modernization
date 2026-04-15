import { FilterConfiguration, ReportColumn } from 'generated';
import { ReactComponentLike } from 'prop-types';

const BasicFilter = ({
    FilterComponent,
    filter,
}: {
    filter: FilterConfiguration;
    columns: ReportColumn[];
    FilterComponent: ReactComponentLike;
}) => {
    const isRequired = (filter.minValueCount ?? 1) > 0;
    return <FilterComponent orientation="horizontal" required={isRequired} filter={filter} />;
};

export { BasicFilter };
