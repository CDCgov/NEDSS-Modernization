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
import { HorizontalRule } from '../FormDivider/HorizontalRule';

export type CreateQuestionForm = Omit<CreateQuestionRequest & AdditionalQuestionFields, 'codeSet'> & {
    codeSet: 'LOCAL' | 'PHIN';
};

export type AdditionalQuestionFields = {
    relatedUnits?: boolean;
    unitType?: 'literal' | 'coded' | '';
};

type Props = {
    onFindValueSet: () => void;
};

export const QuestionForm = ({ onFindValueSet }: Props) => {
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
            <BasicInformationFields />
            <QuestionSpecificFields onFindValueSet={onFindValueSet} />
            <HorizontalRule />
            <UserInterfaceFields />
            {displayControl?.toString() !== '1026' && ( // hide data mart and messaging if display control = 'Readonly User text, number, or date'
                <>
                    <HorizontalRule />
                    <DataMartFields />
                    <HorizontalRule />
                    <MessagingFields />
                </>
            )}
            <HorizontalRule />
            <AdministrativeFields />
        </div>
    );
};
