import { ErrorMessage, Label, Radio, Textarea } from '@trussworks/react-uswds';

import { QuestionValidationRequest } from 'apps/page-builder/generated/models/QuestionValidationRequest';
import { useOptions } from 'apps/page-builder/hooks/api/useOptions';
import { useQuestionValidation } from 'apps/page-builder/hooks/api/useQuestionValidation';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { useEffect } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { CreateQuestionForm } from '../QuestionForm';
import styles from '../question-form.module.scss';
import { SegmentedButtons } from 'components/SegmentedButtons/SegmentedButtons';

const questionTypes: { name: string; value: 'CODED' | 'NUMERIC' | 'TEXT' | 'DATE' }[] = [
    { name: 'Value set', value: 'CODED' },
    { name: 'Numeric entry', value: 'NUMERIC' },
    { name: 'Text only', value: 'TEXT' },
    { name: 'Date picker', value: 'DATE' }
];

type Props = {
    editing?: boolean;
};
export const BasicInformationFields = ({ editing = false }: Props) => {
    const form = useFormContext<CreateQuestionForm>();
    const [uniqueId, uniqueName] = useWatch({
        control: form.control,
        name: ['uniqueId', 'uniqueName', 'questionType'],
        exact: true
    });
    const { options: subgroups } = useOptions('NBS_QUES_SUBGROUP');
    const { isValid: uniqueIdIsValid, validate: validateUniqueId } = useQuestionValidation(
        QuestionValidationRequest.field.UNIQUE_ID
    );
    const { isValid: uniqueNameIsValid, validate: validateUniqueName } = useQuestionValidation(
        QuestionValidationRequest.field.UNIQUE_NAME
    );

    const handleUniqueIdValidation = async (uniqueId?: string) => {
        if (uniqueId) {
            validateUniqueId(uniqueId);
        }
    };

    const handleUniqueNameValidation = async (name?: string) => {
        if (name) {
            validateUniqueName(name);
        }
    };

    useEffect(() => {
        // check === false to keep undefined from triggering an error
        if (uniqueIdIsValid === false) {
            form.setError('uniqueId', {
                message: `A question with Unique ID: ${uniqueId} already exists in the system`
            });
        }
    }, [uniqueIdIsValid]);

    useEffect(() => {
        // check === false to keep undefined from triggering an error
        if (uniqueNameIsValid === false) {
            form.setError('uniqueName', {
                message: `A question with Unique name: ${uniqueName} already exists in the system`
            });
        }
    }, [uniqueNameIsValid]);

    return (
        <>
            <h4>Basic information</h4>
            <Controller
                control={form.control}
                name="codeSet"
                render={({ field: { onChange, value } }) => (
                    <div className={styles.radioButtons}>
                        <Radio
                            id="codeSet_LOCAL"
                            name="codeSet"
                            value={'LOCAL'}
                            label="LOCAL"
                            onChange={onChange}
                            checked={value === 'LOCAL'}
                            disabled={editing}
                        />
                        <Radio
                            id="codeSet_PHIN"
                            name="codeSet"
                            value={'PHIN'}
                            label="PHIN"
                            onChange={onChange}
                            checked={value === 'PHIN'}
                            disabled={editing}
                        />
                    </div>
                )}
            />
            <div>
                <Controller
                    control={form.control}
                    name="uniqueId"
                    rules={{
                        pattern: { value: /^\w*$/, message: 'Valid characters are A-Z, a-z, 0-9, or _' },
                        ...maxLengthRule(50)
                    }}
                    render={({ field: { onChange, value, onBlur, name }, fieldState: { error } }) => (
                        <Input
                            onChange={onChange}
                            onBlur={() => {
                                onBlur();
                                handleUniqueIdValidation(uniqueId);
                            }}
                            defaultValue={value}
                            label="Unique ID"
                            type="text"
                            error={error?.message}
                            name={name}
                            htmlFor={name}
                            id={name}
                            disabled={editing}
                        />
                    )}
                />
                <div className={styles.helpText}>
                    If you decide not to provide a UNIQUE ID, the system will generate one for you.
                </div>
            </div>
            <Controller
                control={form.control}
                name="uniqueName"
                rules={{ required: { value: true, message: 'Unique name is required' }, ...maxLengthRule(50) }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        onChange={onChange}
                        onBlur={() => {
                            onBlur();
                            handleUniqueNameValidation(uniqueName);
                        }}
                        defaultValue={value}
                        label="Unique name"
                        type="text"
                        name={name}
                        htmlFor={name}
                        id={name}
                        error={error?.message}
                        required
                        disabled={editing}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="subgroup"
                rules={{ required: { value: true, message: 'Subgroup required' } }}
                render={({ field: { onChange, value, onBlur, name }, fieldState: { error } }) => (
                    <SelectInput
                        label="Subgroup"
                        className="subgroupSelect"
                        defaultValue={value}
                        onChange={(e) => {
                            onChange(e);
                            onBlur();
                        }}
                        onBlur={onBlur}
                        error={error?.message}
                        options={subgroups}
                        name={name}
                        htmlFor={name}
                        id={name}
                        disabled={editing}
                        required
                    />
                )}
            />
            <Controller
                control={form.control}
                name="description"
                rules={{ required: { value: true, message: 'Description is required' }, ...maxLengthRule(2000) }}
                render={({ field: { onChange, name, value, onBlur }, fieldState: { error } }) => (
                    <>
                        <Label className="required" htmlFor={name}>
                            Description
                        </Label>
                        {error?.message && <ErrorMessage id={error?.message}>{error?.message}</ErrorMessage>}
                        <Textarea
                            onChange={onChange}
                            onBlur={onBlur}
                            defaultValue={value}
                            name={name}
                            disabled={editing}
                            id={name}
                            rows={1}
                            className={styles.textaArea}
                            required
                        />
                    </>
                )}
            />
            <Controller
                control={form.control}
                name="questionType"
                rules={{ required: { value: true, message: 'Field type required' } }}
                render={({ field: { onChange, value }, fieldState: { error } }) => (
                    <>
                        {error?.message && <ErrorMessage id={error?.message}>{error?.message}</ErrorMessage>}
                        <SegmentedButtons
                            title="Field type"
                            buttons={questionTypes.map((field) => ({
                                value: field.value,
                                name: field.name,
                                label: field.name
                            }))}
                            onClick={(field): void => {
                                onChange(field.value);
                            }}
                            value={value}
                            required
                        />
                    </>
                )}
            />
        </>
    );
};
