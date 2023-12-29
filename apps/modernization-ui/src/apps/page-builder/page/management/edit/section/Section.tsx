import { PageQuestionControllerService, PagesSection } from 'apps/page-builder/generated';
import { SectionHeader } from './SectionHeader';

import styles from './section.module.scss';
import { Subsection } from '../subsection/Subsection';
import { useState } from 'react';
import { useAlert } from '../../../../../../alert';
import { usePageManagement } from '../../usePageManagement';
import { authorization } from 'authorization';

type Props = {
    section: PagesSection;
    onAddSubsection: (section: number) => void;
};
export const Section = ({ section, onAddSubsection }: Props) => {
    const [isExpanded, setIsExpanded] = useState<boolean>(true);

    const handleExpandedChange = (expanded: boolean) => {
        setIsExpanded(expanded);
    };
    const { showAlert } = useAlert();
    const { page, fetch } = usePageManagement();
    const handleAddQuestion = (subsection: number, questionId: number) => {
        const request = {
            subsectionId: subsection,
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
        <div className={styles.section}>
            <SectionHeader
                name={section.name ?? ''}
                subsectionCount={section.subSections?.length ?? 0}
                onAddSubsection={() => onAddSubsection(section.id!)}
                onExpandedChange={handleExpandedChange}
                isExpanded={isExpanded}
            />
            {isExpanded && (
                <div className={styles.subsectionWrapper}>
                    {section.subSections?.map((subsection, k) => (
                        <Subsection
                            subsection={subsection}
                            key={k}
                            onAddQuestion={(questionId) => handleAddQuestion(subsection.id!, questionId)}
                        />
                    ))}
                </div>
            )}
        </div>
    );
};
