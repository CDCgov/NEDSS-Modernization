import React from 'react';
import { Concept, Condition, PageControllerService, PageInformationChangeRequest } from 'apps/page-builder/generated';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelectInput } from 'components/selection/multi';
import { Control, Controller } from 'react-hook-form';
import { ModalToggleButton } from '@trussworks/react-uswds';
import { validPageNameRule } from '../../../../../../validation/entry';
import { dataMartNameRule } from '../../../../../../validation/entry/dataMartNameRule';
import { authorization } from '../../../../../../authorization';
import { useAlert } from '../../../../../../alert';

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
    const { alertError } = useAlert();
    const validatePageName = async (val: string) => {
        const response = await PageControllerService.validatePageRequestUsingPost({
            authorization: authorization(),
            request: { name: val }
        });
        if (!response) {
            alertError({ message: 'Failed to save page' });
        }
    };

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
                        onBlur={() => {
                            onBlur();
                            validatePageName(value!);
                        }}
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
                render={({ field: { onChange, value, name } }) => (
                    <Input
                        onChange={onChange}
                        label="Page description"
                        name={name}
                        htmlFor={name}
                        id={name}
                        aria-label={'enter a description for the page'}
                        type="text"
                        multiline
                        disabled={isEnabled}
                        defaultValue={value}
                    />
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
