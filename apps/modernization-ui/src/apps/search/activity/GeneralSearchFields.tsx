/* eslint-disable */

import { Controller, useFormContext } from 'react-hook-form';
import { SingleSelect } from 'design-system/select';
import { SearchCriteria } from 'apps/search/criteria';
import { ActivityFilterEntry, reportType, statusOptions } from './ActivityLogFormTypes';
import { EntryFieldsProps } from 'design-system/entry';
import { CheckboxGroup } from 'design-system/checkbox/CheckboxGroup';
import { useEffect } from 'react';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';

const GeneralSearchFields = ({ sizing = 'medium' }: EntryFieldsProps) => {
    const form = useFormContext<ActivityFilterEntry, Partial<ActivityFilterEntry>>();

    const today = new Date();
    const yesterday = new Date();
    yesterday.setDate(today.getDate() - 1);

    const formatDate = (date: Date) => {
        const month = date.getMonth() + 1; // Months are zero-indexed
        const day = date.getDate();
        const year = date.getFullYear();
        return `${month < 10 ? `0${month}` : month}/${day < 10 ? `0${day}` : day}/${year}`;
    };
    // useEffect(() => {
    //     form.setValue('reportType', reportType[0]);
    //     form.setValue('status', statusOptions);
    // }, [form]);

    return (
        <SearchCriteria>
            <Controller
                control={form.control}
                name="reportType"
                render={({ field: { name, onChange, value } }) => (
                    <SingleSelect
                        name={name}
                        value={value}
                        id="reportType"
                        label="Type"
                        sizing={sizing}
                        required
                        onChange={onChange}
                        options={reportType}
                    />
                )}
            />
            <Controller
                shouldUnregister
                control={form.control}
                name="eventDate.from"
                rules={{
                    required: { value: true, message: 'From date is required' }
                }}
                render={({ field: { onBlur, onChange, name, value }, fieldState: { error } }) => (
                    <DatePickerInput
                        defaultValue={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        label="From Date"
                        helperText={'mm/dd/yyyy'}
                        sizing={sizing}
                        required
                        name={name}
                        errorMessage={error?.message}
                    />
                )}
            />
            <Controller
                shouldUnregister
                control={form.control}
                name="eventDate.to"
                rules={{
                    required: { value: true, message: 'To date is required' }
                }}
                render={({ field: { onBlur, onChange, name, value }, fieldState: { error } }) => (
                    <DatePickerInput
                        defaultValue={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        label="To Date"
                        helperText={'mm/dd/yyyy'}
                        sizing={sizing}
                        required
                        name={name}
                        errorMessage={error?.message}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="status"
                rules={{
                    required: { value: true, message: 'At least one status is required' }
                }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <CheckboxGroup
                        name={name}
                        label={'Display records with status'}
                        sizing={sizing}
                        required
                        options={statusOptions}
                        value={value || []}
                        onChange={onChange}
                        onBlur={onBlur}
                        error={error?.message}
                    />
                )}
            />
        </SearchCriteria>
    );
};

export default GeneralSearchFields;
