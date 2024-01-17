import { PagesQuestion, PagesSection, SectionControllerService } from 'apps/page-builder/generated';
import React, { RefObject } from 'react';

import styles from './section.module.scss';
import { Section } from './Section';
import { ModalRef } from '@trussworks/react-uswds';
import { authorization } from 'authorization';
import { usePageManagement } from '../../usePageManagement';
import { useAlert } from 'alert';

type Props = {
    sections: PagesSection[];
    onAddQuestion: (subsection: number) => void;
    onAddSubsection: (section: number) => void;
    addQuestionModalRef: RefObject<ModalRef>;
    onEditQuestion: (question: PagesQuestion) => void;
    handleEditSection: () => void;
};

export const Sections = ({
    sections,
    onAddSubsection,
    onAddQuestion,
    addQuestionModalRef,
    onEditQuestion,
    handleEditSection
}: Props) => {
    const { page, fetch } = usePageManagement();

    const { showAlert } = useAlert();

    const handleDeleteSection = (section: PagesSection) => {
        SectionControllerService.deleteSectionUsingDelete({
            authorization: authorization(),
            page: page.id,
            sectionId: section.id
        }).then(() => {
            showAlert({ message: `You've successfully deleted section!`, type: `success` });
            fetch(page.id);
        });
    };

    return (
        <div className={styles.sections}>
            {sections.map((s, k) => (
                <React.Fragment key={k}>
                    <Section
                        section={s}
                        onAddSubsection={onAddSubsection}
                        onAddQuestion={onAddQuestion}
                        onEditQuestion={onEditQuestion}
                        addQuestionModalRef={addQuestionModalRef}
                        handleEditSection={handleEditSection}
                        handleDeleteSection={() => handleDeleteSection?.(s)}
                    />
                </React.Fragment>
            ))}
        </div>
    );
};
