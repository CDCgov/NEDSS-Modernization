import { PagesSection } from 'apps/page-builder/generated';
import { SectionHeader } from './SectionHeader';

import styles from './section.module.scss';
import { Subsection } from '../subsection/Subsection';
import { useState } from 'react';

type Props = {
    section: PagesSection;
    onAddQuestion: (subsection: number) => void;
    onAddSubsection: (section: number) => void;
};
export const Section = ({ section, onAddQuestion, onAddSubsection }: Props) => {
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
                isExpanded={isExpanded}
            />
            <div className={styles.subsectionWrapper}>
                {isExpanded &&
                    section.subSections?.map((subsection, k) => (
                        <Subsection
                            subsection={subsection}
                            key={k}
                            onAddQuestion={() => onAddQuestion(subsection.id!)}
                        />
                    ))}
            </div>
        </div>
    );
};
