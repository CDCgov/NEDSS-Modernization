import { useOptions } from 'apps/page-builder/hooks/api/useOptions';
import { Input } from 'components/FormInputs/Input';
import { Heading } from 'components/heading';
import { ChangeEvent, useEffect } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { CreateQuestionForm } from '../QuestionForm';
import styles from '../question-form.module.scss';
import { QuestionValidationRequest } from 'apps/page-builder/generated/models/QuestionValidationRequest';
import { useQuestionValidation } from 'apps/page-builder/hooks/api/useQuestionValidation';

type Props = {
    editing?: boolean;
};
export const DataMartFields = ({ editing = false }: Props) => {
    const { options: rdbTableNames } = useOptions('NBS_PH_DOMAINS');
    const form = useFormContext<CreateQuestionForm>();
    const watch = useWatch(form);
    const alphanumericUnderscoreNotStartingWithNumber = /^[a-zA-Z_]\w*$/;
    const { isValid: isValidRdbColumn, validate: validateRdbColumnName } = useQuestionValidation(
        QuestionValidationRequest.field.RDB_COLUMN_NAME
    );
    const { isValid: isValidDataMartColumnName, validate: validateDataMartColumnName } = useQuestionValidation(
        QuestionValidationRequest.field.DATA_MART_COLUMN_NAME
    );

    useEffect(() => {
        if (watch.displayControl?.toString() !== '1026') {
            const tableName = rdbTableNames.find((o) => o.value === watch.subgroup);
            form.setValue('dataMartInfo.defaultRdbTableName', tableName?.name);
        }
    }, [watch.subgroup]);

    const handleRdbColumnNameValidation = (columnName: string | undefined) => {
        if (columnName) {
            validateRdbColumnName(columnName);
        }
    };

    const handleDataMartColumnNameValidation = (columnName: string | undefined) => {
        if (columnName) {
            validateDataMartColumnName(columnName);
        }
    };

    useEffect(() => {
        // check === false to keep undefined from triggering an error
        if (isValidRdbColumn === false) {
            form.setError('dataMartInfo.rdbColumnName', {
                message: `A column name: ${watch.dataMartInfo?.rdbColumnName} already exists in the system`
            });
        }
    }, [isValidRdbColumn]);

    useEffect(() => {
        if (isValidDataMartColumnName === false) {
            form.setError('dataMartInfo.dataMartColumnName', {
                message: `A column name: ${watch.dataMartInfo?.dataMartColumnName} already exists in the system`
            });
        }
    }, [isValidDataMartColumnName]);

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
                render={({ field: { onChange, name, value, onBlur }, fieldState: { error } }) => (
                    <Input
                        label="Default label in report"
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value}
                        type="text"
                        error={error?.message}
                        name={name}
                        id={name}
                        htmlFor={name}
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
                render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                    <Input
                        label="Default RDB table name"
                        onChange={onChange}
                        className="field-space"
                        defaultValue={value}
                        disabled={true}
                        type="text"
                        error={error?.message}
                        name={name}
                        id={name}
                        htmlFor={name}
                        required
                    />
                )}
            />
            <Controller
                control={form.control}
                name="dataMartInfo.rdbColumnName"
                rules={{
                    required: { value: true, message: 'RDB column name is required' },
                    pattern: {
                        value: alphanumericUnderscoreNotStartingWithNumber,
                        message: 'Must not start with a number and valid characters are A-Z, a-z, 0-9, or _'
                    },
                    ...maxLengthRule(20)
                }}
                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                    <Input
                        label="RDB column name"
                        onChange={(e: ChangeEvent<HTMLInputElement>) => {
                            onChange({ ...e, target: { ...e.target, value: e.target.value?.toUpperCase() } });
                            form.setValue('dataMartInfo.dataMartColumnName', e.target.value?.toUpperCase());
                        }}
                        onBlur={() => {
                            onBlur();
                            handleRdbColumnNameValidation(watch.dataMartInfo?.rdbColumnName);
                        }}
                        defaultValue={value}
                        type="text"
                        error={error?.message}
                        name={name}
                        id={name}
                        htmlFor={name}
                        disabled={editing}
                        required
                    />
                )}
            />
            <Controller
                control={form.control}
                name="dataMartInfo.dataMartColumnName"
                rules={{
                    pattern: {
                        value: alphanumericUnderscoreNotStartingWithNumber,
                        message: 'Must not start with a number and valid characters are A-Z, a-z, 0-9, or _'
                    },
                    ...maxLengthRule(20)
                }}
                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                    <Input
                        label="Data mart column name"
                        onChange={(e: ChangeEvent<HTMLInputElement>) => {
                            onChange({ ...e, target: { ...e.target, value: e.target.value?.toUpperCase() } });
                        }}
                        onBlur={() => {
                            onBlur();
                            handleDataMartColumnNameValidation(watch.dataMartInfo?.dataMartColumnName);
                        }}
                        className="field-space"
                        defaultValue={value}
                        type="text"
                        disabled={editing}
                        error={error?.message}
                        name={name}
                        id={name}
                        htmlFor={name}
                    />
                )}
            />
        </>
    );
};
