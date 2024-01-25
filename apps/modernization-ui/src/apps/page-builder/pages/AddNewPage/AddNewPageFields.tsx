import { Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { Concept, Condition, PageControllerService, PageCreateRequest, Template } from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelectInput } from 'components/selection/multi';
import React from 'react';
import { Controller, useFormContext } from 'react-hook-form';
import { validPageNameRule } from 'validation/entry';
import { dataMartNameRule } from 'validation/entry/dataMartNameRule';

type AddNewPageFieldProps = {
    conditions: Condition[];
    conditionLookupModal: React.RefObject<ModalRef>;
    createConditionModal: React.RefObject<ModalRef>;
    importTemplateModal: React.RefObject<ModalRef>;
    templates: Template[];
    mmgs: Concept[];
};
export const AddNewPageFields = (props: AddNewPageFieldProps) => {
    const form = useFormContext<PageCreateRequest>();

    const validatePageName = async (val: string) => {
        const response = await PageControllerService.validatePageRequestUsingPost({
            authorization: authorization(),
            request: { name: val }
        });
        if (!response) {
            form.setError('name', { message: 'Name is already in use' });
        }
    };

    return (
        <>
            <Controller
                control={form.control}
                name="conditionIds"
                render={({ field: { onChange, value, name } }) => (
                    <MultiSelectInput
                        onChange={onChange}
                        value={value}
                        name={name}
                        id={name}
                        label="Condition(s)"
                        aria-label={'select the conditions for the page'}
                        options={props.conditions.map((m) => {
                            return {
                                name: m.name ?? '',
                                value: m.id
                            };
                        })}
                    />
                )}
            />
            <p>Can't find the condition you're looking for?</p>
            <ModalToggleButton modalRef={props.conditionLookupModal} outline>
                <p>
                    <Icon.Search size={3} />
                    Advanced condition search
                </p>
            </ModalToggleButton>
            <ModalToggleButton modalRef={props.createConditionModal} unstyled>
                <p>Create a new condition here</p>
            </ModalToggleButton>
            <Controller
                control={form.control}
                name="name"
                rules={{
                    required: { value: true, message: 'Name is required.' },
                    ...validPageNameRule
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
                        ariaLabel={'enter a name for the page'}
                        defaultValue={value}
                        className="pageName"
                        type="text"
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={form.control}
                name="templateId"
                rules={{
                    required: { value: true, message: 'Template is required.' }
                }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SelectInput
                        label="Template"
                        name={name}
                        defaultValue={value}
                        htmlFor={name}
                        id={name}
                        aria-label={'select a template'}
                        onChange={onChange}
                        onBlur={onBlur}
                        options={props.templates.map((template) => {
                            return {
                                name: template.templateNm ?? '',
                                value: template.id?.toString() ?? ''
                            };
                        })}
                        error={error?.message}
                        required></SelectInput>
                )}
            />
            <p>
                Can't find the template you're looking for?
                <br />
                <ModalToggleButton modalRef={props.importTemplateModal} unstyled>
                    <p>Import a new template here</p>
                </ModalToggleButton>
            </p>
            <Controller
                control={form.control}
                name="messageMappingGuide"
                rules={{ required: { value: true, message: 'Reporting mechanism is required.' } }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <SelectInput
                        label="Reporting mechanism"
                        name={name}
                        htmlFor={name}
                        id={name}
                        aria-label={'select a reporting mechanism for the page'}
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value}
                        options={props.mmgs.map((m) => {
                            return {
                                name: m.display ?? '',
                                value: m.conceptCode ?? ''
                            };
                        })}
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={form.control}
                name="pageDescription"
                render={({ field: { onChange, value, name } }) => (
                    <Input
                        onChange={(d: any) => {
                            onChange(d);
                        }}
                        label="Page description"
                        name={name}
                        htmlFor={name}
                        id={name}
                        aria-label={'enter a description for the page'}
                        type="text"
                        multiline
                        defaultValue={value}
                    />
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
                        aria-label={'enter a Data mart name for the page'}
                        type="text"
                        onChange={onChange}
                        defaultValue={value}
                        error={error?.message}
                        onBlur={onBlur}
                    />
                )}
            />
        </>
    );
};
