import { ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { Concept, Condition, Template } from 'apps/page-builder/generated';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelectInput } from 'components/selection/multi';
import React from 'react';
import { Controller, useFormContext } from 'react-hook-form';
import { NavLink } from 'react-router-dom';

type AddNewPageFieldProps = {
    conditions: Condition[];
    conditionLookupModal: React.RefObject<ModalRef>;
    createConditionModal: React.RefObject<ModalRef>;
    templates: Template[];
    mmgs: Concept[];
};
export const AddNewPageFields = (props: AddNewPageFieldProps) => {
    const form = useFormContext();

    return (
        <>
            <Controller
                control={form.control}
                name="conditionIds"
                render={({ field: { onChange, value } }) => (
                    <MultiSelectInput
                        onChange={onChange}
                        value={value}
                        options={props.conditions.map((m) => {
                            return {
                                name: m.conditionShortNm ?? '',
                                value: m.id
                            };
                        })}
                        label="Condition(s)"></MultiSelectInput>
                )}
            />
            <p>
                Can't find the condition you're looking for?
                <br />
                <ModalToggleButton modalRef={props.conditionLookupModal} unstyled>
                    <p>Search and add condition(s)</p>
                </ModalToggleButton>
                &nbsp; or &nbsp;
                <ModalToggleButton modalRef={props.createConditionModal} unstyled>
                    <p>create a new condition here</p>
                </ModalToggleButton>
            </p>
            <Controller
                control={form.control}
                name="name"
                rules={{ required: { value: true, message: 'Name is required.' } }}
                render={({ field: { onChange, value }, fieldState: { error } }) => (
                    <Input
                        onChange={onChange}
                        defaultValue={value}
                        label="Page name"
                        type="text"
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={form.control}
                name="templateId"
                rules={{ required: { value: true, message: 'Template is required.' } }}
                render={({ field: { onChange, value }, fieldState: { error } }) => (
                    <SelectInput
                        label="Templates"
                        defaultValue={value}
                        onChange={onChange}
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
                <NavLink to={'add/template'}>Import a new template here</NavLink>
            </p>
            <Controller
                control={form.control}
                name="messageMappingGuide"
                rules={{ required: { value: true, message: 'MMG is required.' } }}
                render={({ field: { onChange, value }, fieldState: { error } }) => (
                    <SelectInput
                        label="MMG"
                        name="messageMappingGuide"
                        onChange={onChange}
                        defaultValue={value}
                        options={props.mmgs.map((m) => {
                            return {
                                name: m.display ?? '',
                                value: m.conceptCode ?? ''
                            };
                        })}
                        error={error?.message}
                        required></SelectInput>
                )}
            />
            <p>
                Would you like to add any additional information?
                <br />
                These fields are optional, you can make changes to this later.
            </p>
            <Controller
                control={form.control}
                name="pageDescription"
                render={({ field: { onChange, value } }) => (
                    <Input
                        onChange={(d: any) => {
                            onChange(d);
                        }}
                        label="Page description"
                        type="text"
                        multiline
                        defaultValue={value}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="dataMartName"
                render={({ field: { onChange, value } }) => (
                    <Input label="Data mart name" type="text" onChange={onChange} defaultValue={value} />
                )}
            />
        </>
    );
};
