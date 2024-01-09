import React, { RefObject } from 'react';
import { Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { QuestionLibrary } from 'apps/page-builder/pages/QuestionLibrary/QuestionLibrary';
import './AddQuestionModal.scss';
import { PageQuestionControllerService } from '../../../generated';
import { authorization } from '../../../../../authorization';
import { useAlert } from '../../../../../alert';
import { usePageManagement } from '../../../page/management';

type Props = {
    modalRef: RefObject<ModalRef>;
    subsectionId: number;
};

export const AddQuestionModal = ({ modalRef, subsectionId }: Props) => {
    const { showAlert } = useAlert();
    const { page, fetch } = usePageManagement();
    const handleAddQuestion = (questionId: number) => {
        const request = {
            subsectionId,
            questionId: questionId
        };
        PageQuestionControllerService.addQuestionToPageUsingPost({
            authorization: authorization(),
            page: page.id,
            request
        }).then((response) => {
            fetch(page.id);
            showAlert({
                type: 'success',
                header: 'Add',
                message: response.message || 'Add Question successfully on page'
            });
            return response;
        });
    };

    return (
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
                    <QuestionLibrary hideTabs modalRef={modalRef} onAddQuestion={handleAddQuestion} />
                </div>
            }
            closer
        />
    );
};
