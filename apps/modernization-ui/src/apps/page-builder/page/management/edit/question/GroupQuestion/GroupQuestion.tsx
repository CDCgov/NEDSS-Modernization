import { Button, Form, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { useAlert } from 'alert';
import {
    GroupSubSectionRequest,
    PagesQuestion,
    PagesSubSection,
    SubSectionControllerService,
    UpdateSubSectionRequest
} from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import { RefObject, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { usePageManagement } from '../../../usePageManagement';
import { RepeatingBlock } from './RepeatingBlock';
import { SubsectionDetails } from './SubsectionDetails';
import styles from './group-question.module.scss';

type Props = {
    subsection: PagesSubSection;
    questions: PagesQuestion[];
    modalRef: RefObject<ModalRef>;
};

export type GroupQuestionFormType = GroupSubSectionRequest & UpdateSubSectionRequest;

export const GroupQuestion = ({ subsection, questions, modalRef }: Props) => {
    const { page, refresh } = usePageManagement();
    const [valid, setValid] = useState(false);
    const init: GroupQuestionFormType = {
        name: subsection.name,
        batches: questions.map((question) => ({
            appearsInTable: true,
            width: 0,
            label: '',
            id: question.id
        })),
        blockName: '',
        visible: subsection.visible,
        repeatingNbr: 1
    };
    console.log('init', init);
    const methods = useForm<GroupQuestionFormType>({
        mode: 'onChange',
        defaultValues: { ...init }
    });
    const { handleSubmit, reset } = methods;
    const { showAlert } = useAlert();

    const handleGroup = (data: GroupQuestionFormType) => {
        console.log('submitting with data: ', data);
        console.log('init is', init);
        try {
            SubSectionControllerService.groupSubSectionUsingPost({
                authorization: authorization(),
                page: page.id,
                subsection: subsection.id,
                request: {
                    batches: data.batches,
                    blockName: data.blockName,
                    repeatingNbr: data.repeatingNbr
                }
            }).then(() => {
                modalRef.current?.toggleModal();
                showAlert({
                    type: 'success',
                    header: 'Grouped',
                    message: `You've successfully grouped ${data.blockName} subsection`
                });
                refresh();
            });
        } catch (error) {
            modalRef.current?.toggleModal();
            if (error instanceof Error) {
                console.error(error);
                showAlert({
                    type: 'error',
                    header: 'error',
                    message: error.message
                });
            } else {
                console.error(error);
                showAlert({
                    type: 'error',
                    header: 'error',
                    message: 'An unknown error occurred'
                });
            }
        }
    };

    const handleSubsectionUpdate = (data: GroupQuestionFormType) => {
        const request = {
            name: data.name,
            visible: data.visible,
            isGrouped: true
        };
        try {
            SubSectionControllerService.updateSubSectionUsingPut({
                authorization: authorization(),
                page: page.id,
                request: request,
                subSectionId: subsection.id
            });
        } catch (error: unknown) {
            if (error instanceof Error) {
                console.error(error);
                showAlert({
                    type: 'error',
                    header: 'error',
                    message: error.message
                });
            } else {
                console.error(error);
                showAlert({
                    type: 'error',
                    header: 'error',
                    message: 'An unknown error occurred'
                });
            }
        }
    };

    const onSubmit = handleSubmit((data) => {
        handleGroup(data);
        handleSubsectionUpdate(data);
    });

    return (
        <div className={styles.groupQuestion}>
            <FormProvider {...methods}>
                <Form onSubmit={onSubmit} className={styles.form}>
                    <SubsectionDetails />
                    <RepeatingBlock questions={questions} valid={valid} setValid={setValid} />
                    <div className={styles.footer}>
                        <ModalToggleButton modalRef={modalRef} onClick={() => reset()} type="button" outline closer>
                            Cancel
                        </ModalToggleButton>
                        <Button onClick={onSubmit} type="button" disabled={!valid}>
                            Submit
                        </Button>
                    </div>
                </Form>
            </FormProvider>
        </div>
    );
};
