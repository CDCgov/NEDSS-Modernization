import { CreateQuestionRequest } from 'apps/page-builder/hooks/api/useCreateQuestion';
import { useEffect } from 'react';
import { useFormContext, useWatch } from 'react-hook-form';
import { AdministrativeFields } from './fields/AdministrativeFields';
import { BasicInformationFields } from './fields/BasicInformationFields';
import { DataMartFields } from './fields/DataMartFields';
import { MessagingFields } from './fields/MessagingFields';
import { QuestionSpecificFields } from './fields/QuestionSpecificFields';
import { UserInterfaceFields } from './fields/UserInterfaceFields';
import styles from './question-form.module.scss';

export type CreateQuestionForm = Omit<CreateQuestionRequest & AdditionalQuestionFields, 'codeSet'> & {
    codeSet: 'LOCAL' | 'PHIN';
};

export type AdditionalQuestionFields = {
    relatedUnits?: boolean;
    unitType?: 'literal' | 'coded' | '';
};

type Props = {
    question?: CreateQuestionForm;
};

export const QuestionForm = ({ question }: Props) => {
    const form = useFormContext<CreateQuestionForm>();
    const displayControl = useWatch<CreateQuestionForm>({
        control: form.control,
        name: 'displayControl',
        exact: true
    });

    useEffect(() => {
        if (displayControl?.toString() === '1026') {
            // Clear data mart / messaging values when `Readonly User text, number, or date` display control is selected
            form.resetField('dataMartInfo');
            form.resetField('messagingInfo');
        }
    }, [displayControl]);

    return (
        <div className={styles.form}>
            <BasicInformationFields editing={question !== undefined} />
            <QuestionSpecificFields />
            <div className={styles.divider} />
            <UserInterfaceFields />
            {displayControl?.toString() !== '1026' && ( // hide data mart and messaging if display control = 'Readonly User text, number, or date'
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
