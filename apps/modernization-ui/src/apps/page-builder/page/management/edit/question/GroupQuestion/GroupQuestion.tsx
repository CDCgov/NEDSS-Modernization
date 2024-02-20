import { GroupSubSectionRequest, PagesQuestion, PagesSubSection } from 'apps/page-builder/generated';
import styles from './group-question.module.scss';
import { useForm, FormProvider } from 'react-hook-form';
import { Form, ModalRef } from '@trussworks/react-uswds';
import { RefObject, useState } from 'react';
import { Button, ModalToggleButton } from '@trussworks/react-uswds';
import { RepeatingBlock } from './RepeatingBlock';
import { SubsectionDetails } from './SubsectionDetails';
import { SubSectionControllerService } from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import { usePageManagement } from '../../../usePageManagement';
import { useAlert } from 'alert';

type Props = {
    subsection: PagesSubSection;
    questions: PagesQuestion[];
    modalRef: RefObject<ModalRef>;
};

type Additional = {
    repeatNumber: number;
    visibleText: string;
};

type GroupQuestionFormType = GroupSubSectionRequest & PagesSubSection & Additional;

export const GroupQuestion = ({ subsection, questions, modalRef }: Props) => {
    const { page, refresh } = usePageManagement();
    const [valid, setValid] = useState(false);
    const init = {
        name: subsection.name,
        batches: questions.map((question) => ({
            batchTableAppearIndCd: undefined,
            batchTableColumnWidth: undefined,
            batchTableHeader: undefined,
            id: question.id
        })),
        blockName: undefined,
        id: subsection.id,
        visibleText: subsection.visible ? 'Y' : 'N',
        repeatNumber: 1
    };
    const methods = useForm<GroupQuestionFormType>({
        mode: 'onChange',
        defaultValues: { ...init }
    });
    const { handleSubmit, reset } = methods;
    const { showAlert } = useAlert();

    const handleGroup = (data: GroupQuestionFormType) => {
        try {
            SubSectionControllerService.groupSubSectionUsingPost({
                authorization: authorization(),
                page: page.id,
                request: {
                    batches: data.batches,
                    blockName: data.blockName,
                    id: data.id
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
            visible: data.visibleText === 'Y' ? true : false,
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
