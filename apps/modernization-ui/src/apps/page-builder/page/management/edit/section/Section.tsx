import { PagesQuestion, PagesSection } from 'apps/page-builder/generated';
import { SectionHeader } from './SectionHeader';
import styles from './section.module.scss';
import { Subsection } from '../subsection/Subsection';
import { RefObject, useState } from 'react';
import { ModalRef } from '@trussworks/react-uswds';

type Props = {
    section: PagesSection;
    onAddQuestion: (subsection: number) => void;
    onAddSubsection: (section: number) => void;
    onEditQuestion: (question: PagesQuestion) => void;
    handleEditSection: () => void;
    handleDeleteSection: () => void;
    addQuestionModalRef: RefObject<ModalRef>;
};

export const Section = ({
    section,
    onAddSubsection,
    onAddQuestion,
    addQuestionModalRef,
    onEditQuestion,
    handleEditSection,
    handleDeleteSection
}: Props) => {
    const [isExpanded, setIsExpanded] = useState<boolean>(true);

    const handleExpandedChange = (expanded: boolean) => {
        setIsExpanded(expanded);
    };

    return (
        <div className={styles.section}>
            <SectionHeader
                name={section.name ?? ''}
                subsectionCount={section.subSections?.length ?? 0}
                onAddSubsection={() => onAddSubsection(section.id!)}
                onExpandedChange={handleExpandedChange}
                handleEditSection={handleEditSection}
                handleDeleteSection={handleDeleteSection}
                isExpanded={isExpanded}
            />
            {isExpanded && (
                <div className={styles.subsectionWrapper}>
                    {section.subSections?.map((subsection, k) => (
                        <Subsection
                            subsection={subsection}
                            key={k}
                            onEditQuestion={onEditQuestion}
                            addQuestionModalRef={addQuestionModalRef}
                            onAddQuestion={() => onAddQuestion(subsection.id!)}
                        />
                    ))}
                </div>
            )}
        </div>
    );
};
