import { PagesSection } from 'apps/page-builder/generated';
import React from 'react';

import styles from './section.module.scss';
import { Section } from './Section';

type Props = {
    sections: PagesSection[];
    onAddQuestion: (subsection: number) => void;
    onAddSubsection: (section: number) => void;
};
export const Sections = ({ sections, onAddSubsection, onAddQuestion }: Props) => {
    return (
        <div className={styles.sections}>
            {sections.map((s, k) => (
                <Section section={s} key={k} onAddSubsection={onAddSubsection} onAddQuestion={onAddQuestion} />
            ))}
        </div>
    );
};
