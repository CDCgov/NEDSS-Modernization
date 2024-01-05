import { PagesQuestion, PagesSection } from 'apps/page-builder/generated';
import React from 'react';

import styles from './section.module.scss';
import { Section } from './Section';

type Props = {
    sections: PagesSection[];
    onAddSubsection: (section: number) => void;
    onEditQuestion: (question: PagesQuestion) => void;
};
export const Sections = ({ sections, onAddSubsection, onEditQuestion }: Props) => {
    return (
        <div className={styles.sections}>
            {sections.map((s, k) => (
                <Section section={s} key={k} onAddSubsection={onAddSubsection} onEditQuestion={onEditQuestion} />
            ))}
        </div>
    );
};
