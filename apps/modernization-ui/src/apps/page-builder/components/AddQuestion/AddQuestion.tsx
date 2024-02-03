import { Button, Icon } from '@trussworks/react-uswds';
import {
    CancelablePromise,
    CodedQuestion,
    CreateCodedQuestionRequest,
    CreateDateQuestionRequest,
    CreateNumericQuestionRequest,
    CreateTextQuestionRequest,
    QuestionControllerService
} from 'apps/page-builder/generated';
import classNames from 'classnames';
import { Heading } from 'components/heading';
import { FormProvider, useForm } from 'react-hook-form';
import { ButtonBar } from '../ButtonBar/ButtonBar';
import { CloseableHeader } from '../CloseableHeader/CloseableHeader';
import { CreateQuestionForm, QuestionForm } from './QuestionForm';
import { authorization } from 'authorization';
import './AddQuestion.scss';
import styles from './add-question.module.scss';
import { useAlert } from 'alert';

type Props = {
    onBack: () => void;
    onClose: () => void;
    onQuestionCreated: (id: number) => void;
};
export const AddQuestion = ({ onBack, onClose, onQuestionCreated }: Props) => {
    const { alertError } = useAlert();
    const form = useForm<CreateQuestionForm>({
        mode: 'onBlur',
        defaultValues: {
            codeSet: CreateTextQuestionRequest.codeSet.LOCAL,
            messagingInfo: { includedInMessage: false, requiredInMessage: false }
        }
    });

    const handleSubmit = () => {
        let request: CancelablePromise<CodedQuestion>;
        switch (form.getValues('questionType')) {
            case 'CODED':
                request = QuestionControllerService.createCodedQuestionUsingPost({
                    authorization: authorization(),
                    request: { ...(form.getValues() as CreateCodedQuestionRequest) }
                });
                break;
            case 'TEXT':
                request = QuestionControllerService.createTextQuestionUsingPost({
                    authorization: authorization(),
                    request: { ...(form.getValues() as CreateTextQuestionRequest) }
                });
                break;
            case 'DATE':
                request = QuestionControllerService.createDateQuestionUsingPost({
                    authorization: authorization(),
                    request: { ...(form.getValues() as CreateDateQuestionRequest) }
                });
                break;
            case 'NUMERIC':
                request = QuestionControllerService.createNumericQuestionUsingPost({
                    authorization: authorization(),
                    request: { ...(form.getValues() as CreateNumericQuestionRequest) }
                });
                break;
            default:
                throw new Error('Failed to create question');
        }
        request
            .then((response) => {
                response.id && onQuestionCreated(response.id);
            })
            .catch((error) => {
                console.error('error', error);
                alertError({ message: 'Failed to create question' });
            });
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
                <Button disabled={!form.formState.isValid} onClick={handleSubmit} type="button">
                    Create and apply to page
                </Button>
            </ButtonBar>
        </div>
    );
};
