import { ErrorMessage, Label, Textarea } from '@trussworks/react-uswds';
import { Concept, Condition, PageControllerService, PageInformationChangeRequest } from 'apps/page-builder/generated';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelectInput } from 'components/selection/multi';
import { ChangeEvent } from 'react';
import { Controller, useFormContext } from 'react-hook-form';
import { authorization } from '../../../../../../authorization';
import { maxLengthRule, validPageNameRule } from '../../../../../../validation/entry';
import { dataMartNameRule } from '../../../../../../validation/entry/dataMartNameRule';

type AddNewPageFieldProps = {
    conditions: Condition[];
    mmgs: Concept[];
    eventType: string;
    isEnabled: boolean;
    pageStatus: string | undefined;
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

export const PageDetailsField = ({ conditions, mmgs, eventType, isEnabled, pageStatus }: AddNewPageFieldProps) => {
    const form = useFormContext<PageInformationChangeRequest>();
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
                        disabled={isEnabled || pageStatus === 'Published with Draft'}
                        error={error?.message}
                        required
                    />
                )}
            />
            <SelectInput label="Event type" value={eventType} options={eventTypeOptions} disabled />
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
                control={form.control}
                name="description"
                rules={maxLengthRule(2000)}
                render={({ field: { onChange, name, value, onBlur }, fieldState: { error } }) => (
                    <>
                        <Label htmlFor={name}>Page description</Label>
                        <Textarea
                            onChange={onChange}
                            onBlur={onBlur}
                            defaultValue={value}
                            name={name}
                            id={name}
                            disabled={isEnabled}
                        />
                        {error?.message && <ErrorMessage id={error?.message}>{error?.message}</ErrorMessage>}
                    </>
                )}
            />
            <Controller
                control={form.control}
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
                        onChange={(e: ChangeEvent<HTMLInputElement>) => {
                            onChange({ ...e, target: { ...e.target, value: e.target.value?.toUpperCase() } });
                        }}
                        defaultValue={value}
                        error={error?.message}
                        onBlur={onBlur}
                        disabled={isEnabled || pageStatus === 'Published with Draft'}
                    />
                )}
            />
        </>
    );
};
