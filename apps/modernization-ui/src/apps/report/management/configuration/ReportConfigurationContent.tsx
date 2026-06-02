import { Card } from 'design-system/card';
import { NoData } from 'design-system/data';
import { RepeatingBlock } from 'design-system/entry/multi-value';
import { ValueField } from 'design-system/field';
import { HasValueFunction, NamedColumn } from 'design-system/table/header/column';
import { BasicFilterConfiguration, ReportConfiguration } from 'generated';
import { Selectable } from 'options';
import { useReportDataSources, useReportLibraries, useReportSections } from 'options/report';
import { useUserOptions } from 'options/users';

interface FilterConfig {
    id?: number;
    name: string;
    type?: string;
    columnId?: number;
    requiredInd: string;
}

const sizing = 'medium';

// TODO: Move this to the API once #3238 is merged
const formatType = ({ minValueCount, maxValueCount }: BasicFilterConfiguration) => {
    if (minValueCount === 1) {
        if (maxValueCount === -1) return 'Multi';
        if (maxValueCount === 1) return 'Single';
    }
};

const GROUP_OPTIONS = [
    { value: 'S', name: 'Public' },
    { value: 'P', name: 'Private' },
    { value: 'T', name: 'Template' },
    { value: 'R', name: 'Reporting Facility' },
];

type FilterColumn = NamedColumn<FilterConfig, string> & HasValueFunction<FilterConfig, string>;

const ReportConfigurationContent = ({ config, isEditable }: { config?: ReportConfiguration; isEditable: boolean }) => {
    const filterColumns: FilterColumn[] = [
        // TODO: This should be derived from the filter ID once we make thing editable
        { id: 'filter', name: 'Filter', value: (v) => v.name },
        { id: 'type', name: 'Type', value: (v) => v.type },
        {
            id: 'column',
            name: 'Associated column',
            value: (v) => config?.columns.find(({ id }) => id === v.columnId)?.title,
        },
        { id: 'filter-required', name: 'Required as basic filter?', value: (v) => v.requiredInd },
    ];

    const filterData: FilterConfig[] =
        config?.basicFilters.map((f) => ({
            id: f.reportFilterUid,
            // TODO: This should be derived from the filter ID once we make thing editable
            name: f.filterType.name ?? '',
            type: formatType(f),
            columnId: f.reportColumnUid,
            requiredInd: f.isRequired ? 'Yes' : 'No',
        })) ?? [];

    if (config?.advancedFilter) {
        filterData.push({ id: config.advancedFilter.reportFilterUid, name: 'Where Clause Builder', requiredInd: 'No' });
    }

    return (
        <>
            <Card id="report-source" title="1. Report source" collapsible={false}>
                <Row
                    isEditable={isEditable}
                    label="Data source"
                    defaultValue={config?.dataSource.id.toString()}
                    getOptions={useReportDataSources}
                />
            </Card>
            <Card id="metadata" title="2. Report configuration" collapsible={false}>
                <Row isEditable={isEditable} label="Name" defaultValue={config?.title} />
                <Row isEditable={isEditable} label="Description" defaultValue={config?.description} />
                <Row
                    isEditable={isEditable}
                    label="Owner"
                    defaultValue={config?.ownerUid.toString()}
                    getOptions={() => [{ value: '0', name: 'System' }, ...useUserOptions()]}
                    helperText="The user who can edit and delete this report."
                />
                <Row
                    isEditable={isEditable}
                    label="Group"
                    defaultValue={config?.group}
                    getOptions={() => GROUP_OPTIONS}
                    helperText="The level of visibility for the report. Templates are public."
                />
                <Row
                    isEditable={isEditable}
                    label="Section name"
                    defaultValue={config?.sectionCd}
                    getOptions={useReportSections}
                    helperText="The heading under which this report appears."
                />
                <Row
                    isEditable={isEditable}
                    label="Report execution library"
                    defaultValue={config?.library.id.toString()}
                    getOptions={useReportLibraries}
                    helperText="The query logic for the report"
                />
            </Card>
            <RepeatingBlock<FilterConfig>
                id="filter-config"
                title="3. Available filters"
                columns={filterColumns}
                sizing={sizing}
                editable={isEditable}
                data={filterData}
                viewRenderer={(entry: FilterConfig) =>
                    filterColumns.map((fc) => (
                        <ValueField key={fc.id} label={fc.name} sizing={sizing}>
                            {fc.value(entry)}
                        </ValueField>
                    ))
                }
            />
        </>
    );
};

const Row = ({
    isEditable,
    defaultValue,
    label,
    helperText,
    getOptions,
    required = true,
}: {
    isEditable: boolean;
    defaultValue?: string;
    label: string;
    getOptions?: () => Selectable[];
    helperText?: string;
    required?: boolean;
}) => {
    const options = getOptions?.();

    const option = options ? options.find(({ value }) => value === defaultValue) : defaultValue;

    return isEditable ? (
        <div>TO DO - {required}</div>
    ) : (
        <ValueField label={label} helperText={helperText}>
            <Option option={option} />
        </ValueField>
    );
};

const Option = ({ option }: { option?: Selectable | string }) => {
    if (!option) return <NoData />;
    if (typeof option === 'string') return option;
    if (option.name === option.label || !option.label) return option.name;
    return (
        <span>
            {option.name} <em className="text-base">({option.label})</em>
        </span>
    );
};

export { ReportConfigurationContent };
