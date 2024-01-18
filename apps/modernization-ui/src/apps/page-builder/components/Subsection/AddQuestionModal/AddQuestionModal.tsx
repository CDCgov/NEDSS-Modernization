import { Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { QuestionLibrary } from 'apps/page-builder/pages/QuestionLibrary/QuestionLibrary';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { RefObject, useRef } from 'react';
import { CreateQuestion } from '../../CreateQuestion/CreateQuestion';
import './AddQuestionModal.scss';

type Props = {
    modalRef: RefObject<ModalRef>;
    addValueModalRef?: RefObject<ModalRef>;
    subsectionId: number;
};

export const AddQuestionModal = ({ modalRef, addValueModalRef }: Props) => {
    const createModalRef = useRef<ModalRef>(null);

    const handleAddQuestion = (questionId: number) => {
        console.log('add question', questionId);
    };
    const handleClose = () => {
        createModalRef.current?.toggleModal(undefined, false);
    };

    return (
        <>
            <ModalComponent
                modalRef={modalRef}
                size="wide"
                modalHeading={
                    <div className="add-question-header">
                        <ModalToggleButton modalRef={modalRef} closer unstyled>
                            <Icon.ArrowBack size={3} />
                        </ModalToggleButton>
                        <span>Add a question</span>
                    </div>
                }
                modalBody={
                    <div className="add-question-modal">
                        <QuestionLibrary
                            hideTabs
                            modalRef={modalRef}
                            createModalRef={createModalRef}
                            onAddQuestion={handleAddQuestion}
                        />
                    </div>
                }
                closer
            />
            <ModalComponent
                size="wide"
                modalRef={createModalRef}
                modalHeading={
                    <div className="add-question-header">
                        <ModalToggleButton modalRef={createModalRef} closer unstyled>
                            <Icon.ArrowBack size={3} />
                        </ModalToggleButton>
                        <span>Add question</span>
                    </div>
                }
                closer
                modalBody={
                    <CreateQuestion
                        onAddQuestion={handleAddQuestion}
                        onCloseModal={handleClose}
                        addValueModalRef={addValueModalRef}
                    />
                }
            />
        </>
    );
};
