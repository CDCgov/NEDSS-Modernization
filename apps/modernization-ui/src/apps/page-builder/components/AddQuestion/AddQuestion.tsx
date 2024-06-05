import { Button, Icon } from '@trussworks/react-uswds';
import { useAlert } from 'alert';
import { CreateCodedQuestionRequest } from 'apps/page-builder/generated';
import { CreateQuestionRequest, useCreateQuestion } from 'apps/page-builder/hooks/api/useCreateQuestion';
import classNames from 'classnames';
import { PageProvider } from 'page';
import { useEffect, useState } from 'react';
import { FormProvider, useForm, useFormContext } from 'react-hook-form';
import { ButtonBar } from '../ButtonBar/ButtonBar';
import { CloseableHeader } from '../CloseableHeader/CloseableHeader';
import { CreateQuestionForm, QuestionForm } from './QuestionForm';
import { CreateEditValueset } from './valueset/CreateEditValueset';
import { ValuesetSearch } from './valueset/ValuesetSearch';
import styles from './add-question.module.scss';
import './AddQuestion.scss';

type Props = {
    onBack: () => void;
    onClose: () => void;
    onQuestionCreated: (id: number) => void;
};
export const AddQuestion = ({ onBack, onClose, onQuestionCreated }: Props) => {
    const [state, setState] = useState<'create' | 'findValueset' | 'createValueset'>('create');
    const { createQuestion, questionId, error } = useCreateQuestion();
    const { showError } = useAlert();
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
            showError({ message: error });
        }
    }, [questionId, error]);

    return (
        <FormProvider {...form}>
            {state === 'create' && (
                <AddQuestionContent
                    onFindValueSet={() => setState('findValueset')}
                    onBack={onBack}
                    onClose={onClose}
                    onSubmit={handleSubmit}
                />
            )}
            {state === 'findValueset' && (
                <PageProvider>
                    <FindValueSet
                        onCancel={() => setState('create')}
                        onClose={onClose}
                        onCreateNew={() => setState('createValueset')}
                    />
                </PageProvider>
            )}
            {state === 'createValueset' && (
                <CreateEditValueset
                    onCancel={() => {
                        setState('findValueset');
                    }}
                    onClose={onClose}
                    onAccept={() => {
                        setState('create');
                    }}
                />
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
                    <h3>Let's create a new question</h3>
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
                <Button
                    disabled={!form.formState.isValid}
                    onClick={onSubmit}
                    className="createAndDeployToPageBtn"
                    type="button">
                    Create and apply to page
                </Button>
            </ButtonBar>
        </div>
    );
};

type FindValueSetProps = {
    onCancel: () => void;
    onClose: () => void;
    onCreateNew: () => void;
};
const FindValueSet = ({ onCancel, onClose, onCreateNew }: FindValueSetProps) => {
    const form = useFormContext<CreateCodedQuestionRequest>();

    const handleSetValueset = (valueset: number) => {
        form.setValue('valueSet', valueset);
        onCancel();
    };

    return (
        <ValuesetSearch onCancel={onCancel} onClose={onClose} onAccept={handleSetValueset} onCreateNew={onCreateNew} />
    );
};
