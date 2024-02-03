import {
    CreateCodedQuestionRequest,
    CreateDateQuestionRequest,
    CreateNumericQuestionRequest,
    CreateTextQuestionRequest
} from 'apps/page-builder/generated';

import { useEffect } from 'react';
import { useFormContext, useWatch } from 'react-hook-form';
import { AdministrativeFields } from './fields/AdministrativeFields';
import { BasicInformationFields } from './fields/BasicInformationFields';
import { DataMartFields } from './fields/DataMartFields';
import { MessagingFields } from './fields/MessagingFields';
import { QuestionSpecificFields } from './fields/QuestionSpecificFields';
import { UserInterfaceFields } from './fields/UserInterfaceFields';
import styles from './question-form.module.scss';

export type CreateQuestionForm = (
    | CreateNumericQuestionRequest
    | CreateTextQuestionRequest
    | CreateCodedQuestionRequest
    | CreateDateQuestionRequest
) &
    AdditionalQuestionFields;

type AdditionalQuestionFields = {
    questionType?: QuestionType;
    relatedUnits?: boolean;
    unitType?: 'literal' | 'coded' | '';
};

type Props = {
    question?: CreateQuestionForm;
};
export type QuestionType = 'CODED' | 'NUMERIC' | 'TEXT' | 'DATE';

export const QuestionForm = ({ question }: Props) => {
    const form = useFormContext<CreateQuestionForm>();
    const watch = useWatch();

    useEffect(() => {
        if (watch.displayControl?.toString() === '1026') {
            // Clear data mart values when `Readonly User text, number, or date` display control is selected
            form.reset({
                ...form.getValues(),
                dataMartInfo: {
                    reportLabel: undefined,
                    defaultRdbTableName: undefined,
                    dataMartColumnName: undefined,
                    rdbColumnName: undefined
                }
            });
        }
    }, [watch.displayControl]);

    return (
        <div className={styles.form}>
            <BasicInformationFields editing={question !== undefined} />
            <QuestionSpecificFields />
            <div className={styles.divider} />
            <UserInterfaceFields />
            {watch.displayControl?.toString() !== '1026' && ( // hide data mart and messaging if display control = 'Readonly User text, number, or date'
                <>
                    <div className={styles.divider} />
                    <DataMartFields editing={question !== undefined} />
                    <div className={styles.divider} />
                    <MessagingFields editing={question !== undefined} />
                </>
            )}
            <div className={styles.divider} />
            <AdministrativeFields />
        </div>
    );
};
