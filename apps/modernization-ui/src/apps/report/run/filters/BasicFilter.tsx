import { Field } from 'design-system/field';
import { FilterConfiguration, ReportColumn } from 'generated';
import { ReactNode, useId } from 'react';

export type BasicFilterProps = {
    filter: FilterConfiguration;
    id: string;
} & Omit<Parameters<typeof Field>[0], 'htmlFor' | 'children'>

export type BasicFilterComponent = (props: BasicFilterProps) => ReactNode

const BasicFilter = ({
    filter,
    columns,
    FilterComponent,
}: {
    filter: FilterConfiguration;
    columns: ReportColumn[];
    FilterComponent: BasicFilterComponent;
}) => {
    const id = useId();
    const isRequired = (filter.minValueCount ?? 1) > 0;
    const column = columns.find((c) => c.id === filter.reportColumnUid);
    const filterDesc = filter.filterType.descTxt;
    const label = column?.columnTitle ?? filterDesc ?? ''; // empty string not possible in practice
    const helperText = column?.columnTitle ? filterDesc : undefined;

    return (
        <FilterComponent
            id={id}
            orientation="horizontal"
            sizing="medium"
            required={isRequired}
            filter={filter}
            label={label}
            helperText={helperText}
        />
    );
};

export { BasicFilter };
