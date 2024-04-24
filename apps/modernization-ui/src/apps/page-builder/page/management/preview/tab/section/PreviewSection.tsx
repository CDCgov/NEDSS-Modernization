import { PagesSection } from 'apps/page-builder/generated';

import styles from './preview-section.module.scss';
import { PreviewSubsection } from '../subsection/PreviewSubsection';
import { PreviewSectionHeader } from './PreviewSectionHeader';
import { useState } from 'react';

type PreviewSectionType = {
    section: PagesSection;
};

const PreviewSection = ({ section }: PreviewSectionType) => {
    const [isExpanded, setIsExpanded] = useState(true);
    const handleExpandedChange = (expanded: boolean) => {
        setIsExpanded(expanded);
    };
    return (
        <>
            {section.visible && (
                <section className={styles.section}>
                    <PreviewSectionHeader
                        section={section}
                        isExpanded={isExpanded}
                        onExpandedChange={handleExpandedChange}
                    />
                    {isExpanded &&
                        section?.subSections.map((subsection, k) => (
                            <PreviewSubsection subsection={subsection} key={k} />
                        ))}
                </section>
            )}
        </>
    );
};

export { PreviewSection };
