import { ModalRef } from '@trussworks/react-uswds';
import { Shown } from 'conditional-render';
import { ConfirmationModal } from 'confirmation';
import { Button } from 'design-system/button';
import { Card } from 'design-system/card';
import { NoData } from 'design-system/data';
import { ValueField } from 'design-system/field';
import { TextInputField } from 'design-system/input';
import { TextAreaField } from 'design-system/input/text';
import { RadioGroup } from 'design-system/radio';
import { SingleSelect } from 'design-system/select';
import { AdminReportRequest, ReportConfiguration } from 'generated';
import { Selectable } from 'options';
import { useReportDataSources, useReportLibraries, useReportSections } from 'options/report';
import { useUserOptions } from 'options/users';
import { ReactComponentLike } from 'prop-types';
import { ReactNode, useId, useRef, useState } from 'react';
import { Controller, useFormState, useWatch } from 'react-hook-form';
import { validateRequiredRule } from 'validation/entry';
import { FilterConfig, FilterRepeatingBlock } from './FilterRepeatingBlock';
import { addLabelToName, EnumSelectable } from '../../utils';
import { GROUP_OPTIONS, SIZING } from '../../constants.ts';
import {
    DirtySectionErrorMessage,
    ValidationErrorBanner,
    ValidationErrorSection,
} from 'design-system/errors/ValidationError';

export type ConfigForm = {
    dataSourceId: Selectable;
    reportTitle: string;
    description: string;
    ownerId: Selectable;
    group: EnumSelectable<ReportConfiguration.group>;
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
        filterRequests: data.filterRequests.map((fc) => ({
            id: fc.id,
            filterCodeUid: parseInt(fc.filter.value),
            selectType: fc.selectType?.value,
            columnUid: fc.associatedColumn?.value ? parseInt(fc.associatedColumn.value) : undefined,
            isRequired: fc.isRequired,
        })),
    };
};

const ReportConfigurationContent = ({ config, isEditable }: { config?: ReportConfiguration; isEditable: boolean }) => {
    const [dataSource, setDataSource] = useState<string | Selectable | undefined>(config?.dataSource.id.toString());
    const dataSourceSelected = !!dataSource;

    return (
        <>
            {isEditable ? (
                <>
                    <ValidationErrors />
                    <DataSourceEditCard config={config} setDataSource={setDataSource} />
                </>
            ) : (
                <DataSourceCard isEditable={false} defaultValue={config!.dataSource.id.toString()} disabled={false} />
            )}
            <Card id="metadata" title="2. Report configuration" collapsible={false} disabled={!dataSourceSelected}>
                <Row
                    isEditable={isEditable}
                    disabled={!dataSourceSelected}
                    fieldName="reportTitle"
                    EditComponent={TextInputField}
                    label="Name"
                    defaultValue={config?.title}
                    maxLength={100}
                />
                <Row
                    isEditable={isEditable}
                    disabled={!dataSourceSelected}
                    fieldName="description"
                    EditComponent={TextAreaField}
                    label="Description"
                    defaultValue={config?.description}
                    maxLength={300}
                />
                <Row
                    isEditable={isEditable}
                    disabled={!dataSourceSelected}
                    fieldName="ownerId"
                    EditComponent={SingleSelect}
                    label="Owner"
                    defaultValue={config?.ownerUid.toString()}
                    getOptions={() => {
                        const options = useUserOptions();
                        if (options.length === 0) return options;
                        // add system option once loaded (to avoid options appearing loaded when not)
                        return [{ value: '0', name: 'System' }, ...options];
                    }}
                    helperText="The user who can edit and delete this report."
                />
                <Row
                    isEditable={isEditable}
                    disabled={!dataSourceSelected}
                    fieldName="group"
                    EditComponent={RadioGroup}
                    label="Group"
                    defaultValue={config?.group}
                    getOptions={() => GROUP_OPTIONS}
                    helperText="The level of visibility for the report. Templates are public."
                />
                <Row
                    isEditable={isEditable}
                    disabled={!dataSourceSelected}
                    fieldName="sectionCode"
                    EditComponent={SingleSelect}
                    label="Section name"
                    defaultValue={config?.sectionCd}
                    getOptions={useReportSections}
                    helperText="The heading under which this report appears."
                />
                <Row
                    isEditable={isEditable}
                    disabled={!dataSourceSelected}
                    fieldName="libraryId"
                    EditComponent={SingleSelect}
                    label="Report execution library"
                    defaultValue={config?.library.id.toString()}
                    getOptions={() => {
                        // move nbs custom to the top of the list
                        const libs = useReportLibraries();
                        const nbsCustom = libs.find(({ name }) => name === 'nbs_custom');
                        const options = libs.filter(({ name }) => name !== 'nbs_custom');
                        if (!nbsCustom) return options;
                        return [nbsCustom, ...options];
                    }}
                    helperText="The query logic for the report"
                />
            </Card>
            <FilterRepeatingBlock config={config} isEditable={isEditable} dataSource={dataSource} />
        </>
    );
};

const ValidationErrors = () => {
    const { errors } = useFormState<ConfigForm>();
    const hasFormErrs = !!Object.keys(errors).length;
    const metadataErrs = Object.entries(errors).filter(([k, _e]) => k !== 'dataSourceId' && k !== 'filterRequests');

    if (hasFormErrs) {
        return (
            <ValidationErrorBanner level={2}>
                <>
                    {errors.dataSourceId?.message && (
                        <ValidationErrorSection id="report-source" title="Report source">
                            <li>{errors.dataSourceId.message}</li>
                        </ValidationErrorSection>
                    )}
                    {!!metadataErrs.length && (
                        <ValidationErrorSection id="metadata" title="Report configuration">
                            {metadataErrs.map(([k, { message }]) => !!message && <li key={`error-${k}`}>{message}</li>)}
                        </ValidationErrorSection>
                    )}
                    {errors.filterRequests?.type && (
                        <ValidationErrorSection id="filter-config" title="Available filters">
                            <li>
                                <DirtySectionErrorMessage title="Available filters" />
                            </li>
                        </ValidationErrorSection>
                    )}
                </>
            </ValidationErrorBanner>
        );
    }
};

const DataSourceEditCard = ({
    config,
    setDataSource,
}: {
    config?: ReportConfiguration;
    setDataSource: (ds: Selectable) => void;
}) => {
    const [dataSourceSelected, setDataSourceSelected] = useState(!!config?.dataSource);
    const confirmDataSourceRef = useRef<ModalRef>(null);

    const dataSource = useWatch<ConfigForm, 'dataSourceId'>({ disabled: dataSourceSelected, name: 'dataSourceId' });

    return (
        <DataSourceCard
            isEditable={true}
            defaultValue={config?.dataSource.id.toString()}
            disabled={dataSourceSelected}
            footer={
                <Shown when={!dataSourceSelected}>
                    {/* didn't seem worth adding a styles module for this one thing */}
                    <div style={{ paddingLeft: '16rem' }}>
                        <Button
                            secondary={true}
                            disabled={!dataSource}
                            onClick={confirmDataSourceRef.current?.toggleModal}
                        >
                            Confirm data source
                        </Button>
                    </div>
                </Shown>
            }
        >
            <ConfirmationModal
                modal={confirmDataSourceRef}
                title={`Confirm data source: ${dataSource?.name}`}
                message={
                    <>
                        After you confirm, you cannot edit the data source again. If you want to use a different data
                        source for this report later, create a new report.
                    </>
                }
                confirmText="Confirm"
                cancelText="Cancel"
                onConfirm={() => {
                    setDataSourceSelected(true);
                    setDataSource(dataSource);
                    confirmDataSourceRef.current?.toggleModal();
                }}
                onCancel={() => {
                    confirmDataSourceRef.current?.toggleModal();
                }}
            />
        </DataSourceCard>
    );
};

const DataSourceCard = ({
    defaultValue,
    disabled,
    footer,
    children,
    isEditable,
}: {
    defaultValue?: string;
    disabled: boolean;
    isEditable: boolean;
    footer?: ReactNode;
    children?: ReactNode;
}) => {
    return (
        <Card id="report-source" title="1. Report source" collapsible={false} footer={footer}>
            <Row
                isEditable={isEditable}
                disabled={disabled}
                fieldName="dataSourceId"
                EditComponent={SingleSelect}
                label="Data source"
                defaultValue={defaultValue}
                getOptions={useReportDataSources}
            />

            {children}
        </Card>
    );
};

const Row = ({
    isEditable,
    disabled,
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
    disabled: boolean;
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

    const optionsReady = defaultValue && !!getOptions ? options!.length > 0 : true;

    // if there is a default value and a get options, wait for options to load before registering the field
    return isEditable && optionsReady ? (
        <Controller
            name={fieldName}
            rules={required && !disabled ? validateRequiredRule(label) : undefined}
            defaultValue={option}
            // ignoring the ref as it does not pass down well and isn't critical
            // eslint-disable-next-line @typescript-eslint/no-unused-vars
            render={({ field: { ref, ...fieldValues }, fieldState: { error } }) => (
                <EditComponent
                    id={id}
                    orientation="horizontal"
                    sizing={SIZING}
                    required={required}
                    label={label}
                    helperText={helperText}
                    error={error?.message}
                    options={(options ?? []).map(addLabelToName)}
                    maxLength={maxLength}
                    disabled={disabled}
                    {...fieldValues}
                />
            )}
        />
    ) : (
        <ValueField label={label} helperText={helperText} sizing={SIZING}>
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
