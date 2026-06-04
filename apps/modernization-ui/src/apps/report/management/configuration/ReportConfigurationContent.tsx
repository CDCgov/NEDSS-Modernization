import { Card } from 'design-system/card';
import { NoData } from 'design-system/data';
import { RepeatingBlock } from 'design-system/entry/multi-value';
import { ValueField } from 'design-system/field';
import { TextInputField } from 'design-system/input';
import { TextAreaField } from 'design-system/input/text';
import { SingleSelect } from 'design-system/select';
import { HasValueFunction, NamedColumn } from 'design-system/table/header/column';
import { AdminReportRequest, BasicFilterConfiguration, ReportConfiguration } from 'generated';
import { Selectable } from 'options';
import { useReportDataSources, useReportLibraries, useReportSections } from 'options/report';
import { useUserOptions } from 'options/users';
import { ReactComponentLike } from 'prop-types';
import { useId } from 'react';
import { Controller } from 'react-hook-form';
import { validateRequiredRule } from 'validation/entry';

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

interface GroupSelectable {
    value: ReportConfiguration.group;
    name: string;
}

const GROUP_OPTIONS: GroupSelectable[] = [
    { value: ReportConfiguration.group.PUBLIC, name: 'Public' },
    { value: ReportConfiguration.group.PRIVATE, name: 'Private' },
    { value: ReportConfiguration.group.TEMPLATE, name: 'Template' },
    { value: ReportConfiguration.group.REPORTING_FACILITY, name: 'Reporting Facility' },
];

export type ConfigForm = {
    dataSourceId: Selectable;
    reportTitle: string;
    description?: string;
    ownerId: Selectable;
    group: GroupSelectable;
    sectionCode: Selectable;
    libraryId: Selectable;
    filterRequests: FilterConfig[];
};

const formToRequest = (data: ConfigForm): AdminReportRequest => {
    return {
        ...data,
        dataSourceId: parseInt(data.dataSourceId.value),
        libraryId: parseInt(data.libraryId.value),
        ownerId: parseInt(data.ownerId.value),
        group: data.group.value,
        sectionCode: data.sectionCode.value,
        filterRequests: [],
    };
};

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
                    // only editable on create
                    isEditable={isEditable && !config?.dataSource}
                    fieldName="dataSourceId"
                    EditComponent={SingleSelect}
                    label="Data source"
                    defaultValue={config?.dataSource.id.toString()}
                    getOptions={() =>
                        useReportDataSources().map(({ value, name, label }) => ({
                            value,
                            name: `${name} (${label})`,
                            label,
                        }))
                    }
                />
            </Card>
            <Card id="metadata" title="2. Report configuration" collapsible={false}>
                <Row
                    isEditable={isEditable}
                    fieldName="reportTitle"
                    EditComponent={TextInputField}
                    label="Name"
                    defaultValue={config?.title}
                    maxLength={100}
                />
                <Row
                    isEditable={isEditable}
                    fieldName="description"
                    EditComponent={TextAreaField}
                    label="Description"
                    defaultValue={config?.description}
                    required={false}
                    maxLength={300}
                />
                <Row
                    isEditable={isEditable}
                    fieldName="ownerId"
                    EditComponent={SingleSelect}
                    label="Owner"
                    defaultValue={config?.ownerUid.toString()}
                    getOptions={() => [{ value: '0', name: 'System' }, ...useUserOptions()]}
                    helperText="The user who can edit and delete this report."
                />
                <Row
                    isEditable={isEditable}
                    fieldName="group"
                    EditComponent={SingleSelect}
                    label="Group"
                    defaultValue={config?.group}
                    getOptions={() => GROUP_OPTIONS}
                    helperText="The level of visibility for the report. Templates are public."
                />
                <Row
                    isEditable={isEditable}
                    fieldName="sectionCode"
                    EditComponent={SingleSelect}
                    label="Section name"
                    defaultValue={config?.sectionCd}
                    getOptions={useReportSections}
                    helperText="The heading under which this report appears."
                />
                <Row
                    isEditable={isEditable}
                    fieldName="libraryId"
                    EditComponent={SingleSelect}
                    label="Report execution library"
                    defaultValue={config?.library.id.toString()}
                    getOptions={() => {
                        const libs = useReportLibraries();
                        const nbsCustom = libs.find(({ name }) => name === 'nbs_custom');
                        const options = libs
                            .filter(({ name }) => name !== 'nbs_custom')
                            .map(({ value, name, label }) => ({ value, name: `${name} (${label})`, label }));
                        if (!nbsCustom) return options;
                        const nbsCustomName = `${nbsCustom.name} (recommended default) (${nbsCustom.label})`;
                        return [{ value: nbsCustom.value, name: nbsCustomName, label: nbsCustom.value }, ...options];
                    }}
                    helperText="The query logic for the report"
                />
            </Card>
            <RepeatingBlock<FilterConfig>
                id="filter-config"
                title="3. Available filters"
                itemName="filter"
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
    fieldName,
    label,
    helperText,
    maxLength,
    required = true,
    getOptions,
    EditComponent,
}: {
    isEditable: boolean;
    defaultValue?: string;
    fieldName: string;
    label: string;
    helperText?: string;
    maxLength?: number;
    required?: boolean;
    EditComponent: ReactComponentLike;
    getOptions?: () => Selectable[];
}) => {
    const id = useId();
    const options = getOptions?.();

    const option = options ? options.find(({ value }) => value === defaultValue) : defaultValue;

    return isEditable ? (
        <Controller
            name={fieldName}
            rules={required ? validateRequiredRule(label) : undefined}
            defaultValue={defaultValue}
            // ignoring the ref as it does not pass down well and isn't critical
            // eslint-disable-next-line @typescript-eslint/no-unused-vars
            render={({ field: { ref, ...fieldValues }, fieldState: { error } }) => (
                <EditComponent
                    id={id}
                    orientation="horizontal"
                    sizing="medium"
                    required={required}
                    label={label}
                    helperText={helperText}
                    error={error?.message}
                    options={options}
                    maxLength={maxLength}
                    {...fieldValues}
                />
            )}
        />
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

export { ReportConfigurationContent, formToRequest };
