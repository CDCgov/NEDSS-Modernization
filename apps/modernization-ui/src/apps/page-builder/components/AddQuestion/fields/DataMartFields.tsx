import { QuestionValidationRequest } from 'apps/page-builder/generated/models/QuestionValidationRequest';
import { useOptions } from 'apps/page-builder/hooks/api/useOptions';
import { usePageQuestionDataMartValidation } from 'apps/page-builder/hooks/api/usePageQuestionValidation';
import { useQuestionValidation } from 'apps/page-builder/hooks/api/useQuestionValidation';
import { Input } from 'components/FormInputs/Input';
import { ChangeEvent, useEffect } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { CreateQuestionForm } from '../QuestionForm';

type Props = {
    editing?: boolean;
    page?: number;
    questionId?: number;
};
export const DataMartFields = ({ editing = false, page, questionId }: Props) => {
    const { options: rdbTableNames } = useOptions('NBS_PH_DOMAINS');
    const form = useFormContext<CreateQuestionForm>();
    const [displayControl, subgroup, dataMartColumnName, rdbColumnName] = useWatch({
        control: form.control,
        name: ['displayControl', 'subgroup', 'dataMartInfo.dataMartColumnName', 'dataMartInfo.rdbColumnName'],
        exact: true
    });
    const alphanumericUnderscoreNotStartingWithNumber = /^[a-zA-Z_]\w*$/;
    const { isValid: isValidRdbColumn, validate: validateRdbColumnName } = useQuestionValidation(
        QuestionValidationRequest.field.RDB_COLUMN_NAME
    );
    const { isValid: isValidDataMartColumnName, validate: validateDataMartColumnName } = useQuestionValidation(
        QuestionValidationRequest.field.DATA_MART_COLUMN_NAME
    );
    const { isValid: isValidPageDataMartColumnName, validate: validatePageDataMartColumnName } =
        usePageQuestionDataMartValidation();

    useEffect(() => {
        if (displayControl?.toString() !== '1026') {
            const tableName = rdbTableNames.find((o) => o.value === subgroup);
            form.setValue('dataMartInfo.defaultRdbTableName', tableName?.name);
        }
    }, [subgroup, rdbTableNames]);

    const handleRdbColumnNameValidation = (subgroup: string | undefined, columnName: string | undefined) => {
        if (columnName && subgroup) {
            validateRdbColumnName(`${subgroup}_${columnName}`);
        }
    };

    const handleDataMartColumnNameValidation = (columnName: string | undefined) => {
        if (columnName) {
            if (!editing) {
                validateDataMartColumnName(columnName);
            } else if (page && questionId) {
                validatePageDataMartColumnName(page, questionId, columnName);
            }
        }
    };

    // If subgroup changes, we have to re-validate the rdbColumnName
    useEffect(() => {
        if (subgroup && rdbColumnName) {
            handleRdbColumnNameValidation(subgroup, rdbColumnName);
        }
    }, [subgroup]);

    useEffect(() => {
        // check === false to keep undefined from triggering an error
        if (isValidRdbColumn === false) {
            form.setError('dataMartInfo.rdbColumnName', {
                message: `An Rdb column named: ${rdbColumnName} already exists in the system for the specified subgroup`
            });
        } else {
            form.clearErrors('dataMartInfo.rdbColumnName');
        }
    }, [isValidRdbColumn]);

    useEffect(() => {
        if (isValidDataMartColumnName === false || isValidPageDataMartColumnName === false) {
            form.setError('dataMartInfo.dataMartColumnName', {
                message: `A Data mart column named: ${dataMartColumnName} already exists in the system`
            });
        }
    }, [isValidDataMartColumnName, isValidPageDataMartColumnName]);

    return (
        <>
            <h4>Data mart</h4>
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
                        className="defaultLabelInReport"
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
                    required: { value: !editing, message: 'Default RDB table name is required' },
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
                        required={!editing}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="dataMartInfo.rdbColumnName"
                rules={{
                    required: { value: !editing, message: 'RDB column name is required' },
                    pattern: {
                        value: alphanumericUnderscoreNotStartingWithNumber,
                        message: 'Must not start with a number and valid characters are A-Z, a-z, 0-9, or _'
                    },
                    ...maxLengthRule(20)
                }}
                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                    <Input
                        label="RDB column name"
                        className="rdbColumnName"
                        onChange={(e: ChangeEvent<HTMLInputElement>) => {
                            onChange({ ...e, target: { ...e.target, value: e.target.value?.toUpperCase() } });
                            form.setValue('dataMartInfo.dataMartColumnName', e.target.value?.toUpperCase());
                        }}
                        onBlur={() => {
                            handleRdbColumnNameValidation(subgroup, rdbColumnName);
                            setTimeout(() => {
                                onBlur();
                            }, 1300);
                        }}
                        defaultValue={value}
                        type="text"
                        error={error?.message}
                        name={name}
                        id={name}
                        htmlFor={name}
                        disabled={editing}
                        required={!editing}
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
                            handleDataMartColumnNameValidation(dataMartColumnName);
                        }}
                        className="field-space"
                        defaultValue={value}
                        type="text"
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
