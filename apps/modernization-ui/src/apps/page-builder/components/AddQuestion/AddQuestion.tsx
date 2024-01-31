import { Button, Icon } from '@trussworks/react-uswds';
import { CreateTextQuestionRequest } from 'apps/page-builder/generated';
import classNames from 'classnames';
import { Heading } from 'components/heading';
import { FormProvider, useForm } from 'react-hook-form';
import { ButtonBar } from '../ButtonBar/ButtonBar';
import { CloseableHeader } from '../CloseableHeader/CloseableHeader';
import './AddQuestion.scss';
import { CreateQuestionForm, QuestionForm } from './QuestionForm';
import styles from './add-question.module.scss';

type Props = {
    onBack: () => void;
    onClose: () => void;
};
export const AddQuestion = ({ onBack, onClose }: Props) => {
    const form = useForm<CreateQuestionForm>({
        mode: 'onBlur',
        defaultValues: {
            codeSet: CreateTextQuestionRequest.codeSet.LOCAL,
            messagingInfo: { includedInMessage: false, requiredInMessage: false }
        }
    });

    const handleSubmit = () => {
        console.log('handle submit NYI');
    };

    return (
        <div className={classNames(styles.addQuestion, 'add-question')}>
            <CloseableHeader
                title={
                    <div className={styles.addQuestionHeader}>
                        <Icon.ArrowBack onClick={onBack} /> Add question
                    </div>
                }
                onClose={onClose}
            />
            <div className={styles.scrollableContent}>
                <div className={styles.heading}>
                    <Heading level={3}>Let's create a new question</Heading>
                    <div className={styles.fieldInfo}>
                        All fields with <span className={styles.mandatory}>*</span> are required
                    </div>
                </div>
                <div className={styles.formContainer}>
                    <FormProvider {...form}>
                        <QuestionForm />
                    </FormProvider>
                </div>
            </div>
            <ButtonBar>
                <Button onClick={onClose} type="button" outline>
                    Cancel
                </Button>
                <Button
                    disabled={!form.formState.isDirty || !form.formState.isValid}
                    onClick={handleSubmit}
                    type="button">
                    Create and apply to page
                </Button>
            </ButtonBar>
        </div>
    );
};
