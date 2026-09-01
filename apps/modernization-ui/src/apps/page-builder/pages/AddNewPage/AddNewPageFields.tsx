import { ChangeEvent, RefObject } from 'react';

import { ErrorMessage, Icon, Label, ModalRef, ModalToggleButton, Textarea } from '@trussworks/react-uswds';
import { Controller, useFormContext } from 'react-hook-form';

import { Condition, PageControllerService, PageCreateRequest, Template } from 'apps/page-builder/generated';
import { Input } from 'components/FormInputs/Input';
import { MultiSelect, SingleSelect } from 'design-system/select';
import { Option } from 'generated';
import { maxLengthRule, validPageNameRule } from 'validation/entry';
import { dataMartNameRule } from 'validation/entry/dataMartNameRule';

type AddNewPageFieldProps = {
    conditions: Condition[];
    conditionLookupModal: RefObject<ModalRef>;
    createConditionModal: RefObject<ModalRef>;
    importTemplateModal: RefObject<ModalRef>;
    templates: Template[];
    mmgs: Option[];
};
export const AddNewPageFields = (props: AddNewPageFieldProps) => {
    const form = useFormContext<PageCreateRequest>();

    const validatePageName = async (val: string) => {
        const response = await PageControllerService.validatePageRequest({
            requestBody: { name: val },
        });
        if (!response) {
            form.setError('name', { message: 'Name is already in use' });
        }
    };

    const conditionOptions = props.conditions.map((m) => ({
        name: m.name ?? '',
        value: m.id,
    }));

    const templateOptions = props.templates.map((template) => {
        return {
            name: template.templateNm ?? '',
            value: template.id?.toString() ?? '',
        };
    });

    return (
        <>
            <Controller
                control={form.control}
                name="conditionIds"
                render={({ field: { onChange, value, name } }) => (
                    <MultiSelect
                        onChange={(values) => onChange(values.map((v) => v.value))}
                        value={conditionOptions.filter((c) => value?.includes(c.value))}
                        name={name}
                        id={name}
                        label="Condition(s)"
                        orientation="vertical"
                        aria-label="select the conditions for the page"
                        options={conditionOptions}
                    />
                )}
            />
            <p>Can't find the condition you're looking for?</p>
            <ModalToggleButton
                modalRef={props.conditionLookupModal}
                data-testid="advancedConditionSearchBtn"
                outline={true}
            >
                <p>
                    <Icon.Search size={3} aria-label="search" />
                    Advanced condition search
                </p>
            </ModalToggleButton>
            <ModalToggleButton
                modalRef={props.createConditionModal}
                unstyled={true}
                data-testid="createNewConditionHereBtn"
            >
                <p>Create a new condition here</p>
            </ModalToggleButton>
            <Controller
                control={form.control}
                name="name"
                rules={{
                    required: { value: true, message: 'Name is required.' },
                    ...validPageNameRule,
                }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        onChange={onChange}
                        onBlur={() => {
                            onBlur();
                            validatePageName(value);
                        }}
                        label="Page name"
                        name={name}
                        htmlFor={name}
                        id={name}
                        ariaLabel="enter a name for the page"
                        defaultValue={value}
                        className="pageName"
                        type="text"
                        error={error?.message}
                        required={true}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="templateId"
                rules={{
                    required: { value: true, message: 'Template is required.' },
                }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label="Template"
                        name={name}
                        value={templateOptions.find((o) => o.value === value?.toString())}
                        id={name}
                        aria-label="select a template"
                        onChange={(v) => onChange(v?.value ? parseInt(v.value) : null)}
                        onBlur={onBlur}
                        options={templateOptions}
                        error={error?.message}
                        required={true}
                    />
                )}
            />
            <p>
                Can't find the template you're looking for?
                <br />
                <ModalToggleButton modalRef={props.importTemplateModal} unstyled={true}>
                    <p>Import a new template here</p>
                </ModalToggleButton>
            </p>
            <Controller
                control={form.control}
                name="messageMappingGuide"
                rules={{ required: { value: true, message: 'Reporting mechanism is required.' } }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label="Reporting mechanism"
                        name={name}
                        id={name}
                        aria-label="select a reporting mechanism for the page"
                        onChange={(v) => onChange(v?.value ?? null)}
                        onBlur={onBlur}
                        value={props.mmgs.find((o) => o.value === value)}
                        options={props.mmgs}
                        error={error?.message}
                        required={true}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="pageDescription"
                rules={maxLengthRule(2000)}
                render={({ field: { onChange, name, value, onBlur }, fieldState: { error } }) => (
                    <>
                        <Label htmlFor={name}>Page description</Label>
                        <Textarea onChange={onChange} onBlur={onBlur} defaultValue={value} name={name} id={name} />
                        {error?.message && <ErrorMessage id={error?.message}>{error?.message}</ErrorMessage>}
                    </>
                )}
            />
            <Controller
                control={form.control}
                name="dataMartName"
                rules={dataMartNameRule}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Data mart name"
                        name={name}
                        htmlFor={name}
                        id={name}
                        aria-label="enter a Data mart name for the page"
                        type="text"
                        onChange={(e: ChangeEvent<HTMLInputElement>) => {
                            onChange({ ...e, target: { ...e.target, value: e.target.value?.toUpperCase() } });
                        }}
                        defaultValue={value}
                        error={error?.message}
                        onBlur={onBlur}
                    />
                )}
            />
        </>
    );
};
