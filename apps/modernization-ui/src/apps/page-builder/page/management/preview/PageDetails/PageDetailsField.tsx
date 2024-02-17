import React from 'react';
import { Concept, Condition, PageInformationChangeRequest } from 'apps/page-builder/generated';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelectInput } from 'components/selection/multi';
import { Control, Controller } from 'react-hook-form';
import { ErrorMessage, Label, ModalToggleButton, Textarea } from '@trussworks/react-uswds';
import { maxLengthRule, validPageNameRule } from '../../../../../../validation/entry';
import { dataMartNameRule } from '../../../../../../validation/entry/dataMartNameRule';

type AddNewPageFieldProps = {
    conditions: Condition[];
    mmgs: Concept[];
    control: Control<PageInformationChangeRequest, any>;
    eventType: string;
    isEnabled: boolean;
};

const eventTypeOptions = [
    { value: 'CON', name: 'Contact Record' },
    { value: 'IXS', name: 'Interview' },
    { value: 'INV', name: 'Investigation' },
    { value: 'ISO', name: 'Lab Isolate Tracking' },
    { value: 'LAB', name: 'Lab Report' },
    { value: 'SUS', name: 'Lab Susceptibility' },
    { value: 'VAC', name: 'Vaccination' }
];

export const PageDetailsField = ({ conditions, mmgs, control, eventType, isEnabled }: AddNewPageFieldProps) => {
    return (
        <>
            <Controller
                control={control}
                name="conditions"
                render={({ field: { onChange, value, name } }) => (
                    <MultiSelectInput
                        onChange={onChange}
                        value={value}
                        name={name}
                        id={name}
                        disabled={isEnabled}
                        label="Condition(s)"
                        aria-label={'select the conditions for the page'}
                        options={conditions.map((m) => {
                            return {
                                name: m.name ?? '',
                                value: m.id
                            };
                        })}
                    />
                )}
            />
            <p>Can't find the condition you're looking for?</p>
            <ModalToggleButton modalRef={null!} unstyled>
                <p>Search and add condition(s)</p>
            </ModalToggleButton>{' '}
            <span className="operator">or</span>
            <ModalToggleButton modalRef={null!} unstyled>
                <p>Create a new condition here</p>
            </ModalToggleButton>
            <Controller
                control={control}
                name="name"
                rules={{
                    required: { value: true, message: 'Name is required.' },
                    ...validPageNameRule
                }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        onChange={onChange}
                        onBlur={onBlur}
                        label="Page name"
                        name={name}
                        htmlFor={name}
                        id={name}
                        ariaLabel={'enter a name for the page'}
                        defaultValue={value}
                        className="pageName"
                        type="text"
                        disabled={isEnabled}
                        error={error?.message}
                        required
                    />
                )}
            />
            <SelectInput label="Event type" value={eventType} options={eventTypeOptions} disabled />
            <Controller
                control={control}
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
                        className="margin-bottom-10"
                        onBlur={onBlur}
                        disabled={isEnabled}
                        defaultValue={value}
                        options={mmgs.map((m) => {
                            return {
                                name: m.display ?? '',
                                value: m.localCode ?? ''
                            };
                        })}
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="description"
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
                control={control}
                name="datamart"
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
                        disabled={isEnabled}
                    />
                )}
            />
        </>
    );
};
