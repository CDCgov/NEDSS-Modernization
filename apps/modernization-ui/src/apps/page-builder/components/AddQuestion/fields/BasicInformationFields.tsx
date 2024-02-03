import { Button, ButtonGroup, ErrorMessage, Label, Radio, Textarea } from '@trussworks/react-uswds';
import { Heading } from 'components/heading';

import { useOptions } from 'apps/page-builder/hooks/api/useOptions';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Option } from 'generated';
import { useEffect, useState } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { CreateQuestionForm, QuestionType } from '../QuestionForm';
import styles from '../question-form.module.scss';

const questionTypes: { name: string; value: QuestionType }[] = [
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
    const watch = useWatch(form);
    const [subgroups, setSubgroups] = useState<Option[]>([]);

    useEffect(() => {
        useOptions('NBS_QUES_SUBGROUP').then((response) => setSubgroups(response.options));
    }, []);

    return (
        <>
            <Heading level={4}>Basic information</Heading>
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
                            readOnly={editing}
                        />
                        <Radio
                            id="codeSet_PHIN"
                            name="codeSet"
                            value={'PHIN'}
                            label="PHIN"
                            onChange={onChange}
                            checked={value === 'PHIN'}
                            readOnly={editing}
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
                            onBlur={onBlur}
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
                        onBlur={onBlur}
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
                render={({ field: { onChange, name }, fieldState: { error } }) => (
                    <>
                        <Label className="required" htmlFor={name}>
                            Field type
                        </Label>
                        {error?.message && <ErrorMessage id={error?.message}>{error?.message}</ErrorMessage>}
                        <ButtonGroup className={styles.buttonGroup} type="segmented">
                            {questionTypes.map((field, k) => (
                                <Button
                                    key={k}
                                    type="button"
                                    outline={field.value !== watch.questionType}
                                    disabled={editing}
                                    onClick={() => {
                                        onChange(field.value);
                                    }}>
                                    {field.name}
                                </Button>
                            ))}
                        </ButtonGroup>
                    </>
                )}
            />
        </>
    );
};
