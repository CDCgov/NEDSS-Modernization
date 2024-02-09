import { Button, Icon } from '@trussworks/react-uswds';
import { useAlert } from 'alert';
import { CreateCodedQuestionRequest } from 'apps/page-builder/generated';
import { CreateQuestionRequest, useCreateQuestion } from 'apps/page-builder/hooks/api/useCreateQuestion';
import classNames from 'classnames';
import { Heading } from 'components/heading';
import { PageProvider } from 'page';
import { useEffect, useState } from 'react';
import { FormProvider, useForm, useFormContext } from 'react-hook-form';
import { ButtonBar } from '../ButtonBar/ButtonBar';
import { CloseableHeader } from '../CloseableHeader/CloseableHeader';
import './AddQuestion.scss';
import { CreateQuestionForm, QuestionForm } from './QuestionForm';
import styles from './add-question.module.scss';
import { ValuesetSearch } from './valueset/ValuesetSearch';

type Props = {
    onBack: () => void;
    onClose: () => void;
    onQuestionCreated: (id: number) => void;
};
export const AddQuestion = ({ onBack, onClose, onQuestionCreated }: Props) => {
    const [state, setState] = useState<'create' | 'findValueSet'>('create');
    const { createQuestion, questionId, error } = useCreateQuestion();
    const { alertError } = useAlert();
    const form = useForm<CreateQuestionForm>({
        mode: 'onBlur',
        defaultValues: {
            codeSet: 'LOCAL',
            messagingInfo: { includedInMessage: false, requiredInMessage: false }
        }
    });

    const handleSubmit = () => {
        createQuestion(form.getValues() as CreateQuestionRequest);
    };

    useEffect(() => {
        if (questionId) {
            onQuestionCreated(questionId);
        }
        if (error) {
            alertError({ message: error });
        }
    }, [questionId, error]);

    return (
        <FormProvider {...form}>
            {state === 'create' && (
                <AddQuestionContent
                    onFindValueSet={() => setState('findValueSet')}
                    onBack={onBack}
                    onClose={onClose}
                    onSubmit={handleSubmit}
                />
            )}
            {state === 'findValueSet' && (
                <PageProvider>
                    <FindValueSet onCancel={() => setState('create')} onClose={onClose} />
                </PageProvider>
            )}
        </FormProvider>
    );
};

type AddQuestionContentProps = {
    onBack: () => void;
    onClose: () => void;
    onSubmit: () => void;
    onFindValueSet: () => void;
};
const AddQuestionContent = ({ onBack, onClose, onSubmit, onFindValueSet }: AddQuestionContentProps) => {
    const form = useFormContext<CreateQuestionForm>();
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
                    <QuestionForm onFindValueSet={onFindValueSet} />
                </div>
            </div>
            <ButtonBar>
                <Button onClick={onClose} type="button" outline>
                    Cancel
                </Button>
                <Button disabled={!form.formState.isValid} onClick={onSubmit} type="button">
                    Create and apply to page
                </Button>
            </ButtonBar>
        </div>
    );
};

type FindValueSetProps = {
    onCancel: () => void;
    onClose: () => void;
};
const FindValueSet = ({ onCancel, onClose }: FindValueSetProps) => {
    const form = useFormContext<CreateCodedQuestionRequest>();

    const handleSetValueset = (valueset: number) => {
        console.log('setting valueset', valueset);
        console.log('current vs value:', form.getValues('valueSet'));
        form.setValue('valueSet', valueset);
        onCancel();
    };

    return <ValuesetSearch onCancel={onCancel} onClose={onClose} onAccept={handleSetValueset} />;
};
