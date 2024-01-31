import { Input } from 'components/FormInputs/Input';
import { Heading } from 'components/heading';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { CreateQuestionForm } from '../QuestionForm';
import styles from '../question-form.module.scss';
import { useEffect, useState } from 'react';
import { Option } from 'generated';
import { useOptions } from 'apps/page-builder/hooks/api/useOptions';

export const DataMartFields = () => {
    const [rdbTableNames, setRdbTableNames] = useState<Option[]>([]);

    const form = useFormContext<CreateQuestionForm>();
    const watch = useWatch(form);

    useEffect(() => {
        useOptions('NBS_PH_DOMAINS').then((response) => setRdbTableNames(response.options));
    }, []);

    useEffect(() => {
        if (watch.displayControl?.toString() !== '1026') {
            const tableName = rdbTableNames.find((o) => o.value === watch.subgroup);
            form.setValue('dataMartInfo.defaultRdbTableName', tableName?.name);
        }
    }, [watch.subgroup]);

    return (
        <>
            <Heading className={styles.heading} level={4}>
                Data mart
            </Heading>
            <div className={styles.fieldInfo}>These fields will not be displayed to your users</div>
            <Controller
                control={form.control}
                name="dataMartInfo.reportLabel"
                rules={{
                    required: { value: true, message: 'Default label in report is required' },
                    ...maxLengthRule(50)
                }}
                render={({ field: { onChange, value, onBlur }, fieldState: { error } }) => (
                    <Input
                        label="Default label in report"
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value}
                        type="text"
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={form.control}
                name="dataMartInfo.defaultRdbTableName"
                rules={{
                    required: { value: true, message: 'Default RDB table name is required' },
                    ...maxLengthRule(50)
                }}
                render={({ field: { onChange, value }, fieldState: { error } }) => (
                    <Input
                        label="Default RDB table name"
                        onChange={onChange}
                        className="field-space"
                        defaultValue={value}
                        disabled={true}
                        type="text"
                        error={error?.message}
                        required
                    />
                )}
            />
        </>
    );
};
