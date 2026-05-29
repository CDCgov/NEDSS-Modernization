import { Card } from 'design-system/card';
import { ValueView } from 'design-system/data-display/ValueView';
import { RepeatingBlock } from 'design-system/entry/multi-value';
import { ValueField } from 'design-system/field';
import { HasValueFunction, NamedColumn } from 'design-system/table/header/column';
import { BasicFilterConfiguration, ReportConfiguration } from 'generated';

interface FilterConfig {
    id?: number;
    name: string;
    type?: string;
    columnId?: number;
    isRequired: string;
}

const sizing = 'medium';

// TODO: Move this to the API once #3238 is merged
const formatType = ({ minValueCount, maxValueCount }: BasicFilterConfiguration) => {
    if (minValueCount === 1) {
        if (maxValueCount === -1) return 'Multi';
        if (maxValueCount === 1) return 'Single';
    }
};

type FilterColumn = NamedColumn<FilterConfig> & HasValueFunction<FilterConfig, string>;

const ReportConfigurationContent = ({ config, isEditable }: { config?: ReportConfiguration; isEditable: boolean }) => {
    const filterColumns: FilterColumn[] = [
        { id: 'filter', name: 'Filter', value: (v) => v.name },
        { id: 'type', name: 'Type', value: (v) => v.type },
        {
            id: 'column',
            name: 'Associated column',
            value: (v) => config?.columns.find(({ id }) => id === v.columnId)?.title,
        },
        { id: 'filter-required', name: 'Required as basic filter?', value: (v) => v.isRequired },
    ];

    const filterData: FilterConfig[] =
        config?.basicFilters.map((f) => ({
            id: f.reportFilterUid,
            name: f.filterType.name ?? '',
            type: formatType(f),
            columnId: f.reportColumnUid,
            isRequired: f.isRequired ? 'Yes' : 'No',
        })) ?? [];

    if (config?.advancedFilter) {
        filterData.push({ id: config.advancedFilter.reportFilterUid, name: 'Where Clause Builder', isRequired: 'No' });
    }

    return (
        <>
            <Card id="report-source" title="1. Report source" collapsible={false}>
                <Row isEditable={isEditable} label="Data source" defaultValue={config?.dataSource.name} />
            </Card>
            <Card id="metadata" title="2. Report configuration" collapsible={false}>
                <Row isEditable={isEditable} label="Name" defaultValue={config?.title} />
                <Row isEditable={isEditable} label="Description" defaultValue={config?.description} />
                <Row
                    isEditable={isEditable}
                    label="Owner"
                    defaultValue={config?.ownerUid.toString()}
                    helperText="The user who can edit and delete this report."
                />
                <Row
                    isEditable={isEditable}
                    label="Group"
                    defaultValue={config?.group}
                    helperText="The level of visibility for the report. Templates are public."
                />
                <Row
                    isEditable={isEditable}
                    label="Section name"
                    defaultValue={config?.sectionCd}
                    helperText="The heading under which this report appears."
                />
                <Row
                    isEditable={isEditable}
                    label="Report execution library"
                    defaultValue={config?.library.name}
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
                        <ValueView title={fc.name} value={fc.value(entry)} sizing={sizing} required />
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
    required = true,
}: {
    isEditable: boolean;
    defaultValue?: string;
    label: string;
    helperText?: string;
    required?: boolean;
}) => {
    return isEditable ? (
        <div>TO DO - {required}</div>
    ) : (
        <ValueField label={label} helperText={helperText}>
            {defaultValue}
        </ValueField>
    );
};

export { ReportConfigurationContent };
