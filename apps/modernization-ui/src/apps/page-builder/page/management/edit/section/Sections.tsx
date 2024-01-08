import { PagesQuestion, PagesSection } from 'apps/page-builder/generated';
import React, { RefObject } from 'react';

import styles from './section.module.scss';
import { Section } from './Section';
import { ModalRef } from '@trussworks/react-uswds';

type Props = {
    sections: PagesSection[];
    onAddQuestion: (subsection: number) => void;
    onAddSubsection: (section: number) => void;
    addQuestionModalRef: RefObject<ModalRef>;
    editQuestionModalRef: RefObject<ModalRef>;
    onEditQuestion: (question: PagesQuestion) => void;
};

export const Sections = ({
    sections,
    onAddSubsection,
    onAddQuestion,
    addQuestionModalRef,
    editQuestionModalRef,
    onEditQuestion
}: Props) => {
    return (
        <div className={styles.sections}>
            {sections.map((s, k) => (
                <Section
                    section={s}
                    key={k}
                    onAddSubsection={onAddSubsection}
                    onAddQuestion={onAddQuestion}
                    onEditQuestion={onEditQuestion}
                    addQuestionModalRef={addQuestionModalRef}
                    editQuestionModalRef={editQuestionModalRef}
                />
            ))}
        </div>
    );
};
