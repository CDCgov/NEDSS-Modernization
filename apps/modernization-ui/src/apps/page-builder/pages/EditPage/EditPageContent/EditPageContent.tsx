import { ModalRef } from '@trussworks/react-uswds';
import { useAlert } from 'alert';
import { PagesTab } from 'apps/page-builder/generated';
import { useAddQuestionsToPage } from 'apps/page-builder/hooks/api/useAddQuestionsToPage';
import { useEffect, useRef, useState } from 'react';
import { useParams } from 'react-router-dom';
import { SectionComponent } from '../../../components/Section/Section';
import { AddQuestionModal } from '../AddQuestionModal/AddQuestionModal';
import './EditPageContent.scss';

export const EditPageContentComponent = ({
    content,
    onContentChange
}: {
    content: PagesTab;
    onContentChange: () => void;
}) => {
    const modal = useRef<ModalRef>(null);
    const { pageId } = useParams();
    const [addQuestionSubsection, setAddQuestionSubsection] = useState<number | undefined>();
    const { error, response, add } = useAddQuestionsToPage();
    const { showAlert } = useAlert();

    const handleShowAddQuestion = (subsection: number) => {
        setAddQuestionSubsection(subsection);
        modal.current?.toggleModal();
    };

    const handleAddQuestionClose = (questions: number[]) => {
        if (questions.length > 0 && addQuestionSubsection && pageId) {
            add(questions, addQuestionSubsection, +pageId);
        }
    };

    useEffect(() => {
        if (response) {
            showAlert({
                type: 'success',
                message: `Successfully added questions to page.`
            });
            onContentChange();
        }
        if (error) {
            showAlert({
                type: 'error',
                message: `Failed to add question(s) to page.`
            });
        }
    }, [response, error]);

    return (
        <>
            <div className="edit-page-content">
                <div className="edit-page-content__sections">
                    {content.sections?.map(
                        (section, i) =>
                            section.visible && (
                                <SectionComponent
                                    key={i}
                                    section={section}
                                    onShowAddQuestion={handleShowAddQuestion}
                                    onAddSection={onContentChange}
                                />
                            )
                    )}
                </div>
            </div>
            {pageId && <AddQuestionModal pageId={+pageId} onClose={handleAddQuestionClose} modal={modal} />}
        </>
    );
};
