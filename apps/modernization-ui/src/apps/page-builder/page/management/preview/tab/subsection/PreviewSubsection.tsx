import styles from './preview-subsection.module.scss';
import { PagesSubSection } from '../../../../../generated';
import { PreviewQuestion } from '../question/PreviewQuestion';
import { PreviewSubsectionHeader } from './PreviewSubsectionHeader';
import { useState } from 'react';

type Props = {
    subsection: PagesSubSection;
};

export const PreviewSubsection = ({ subsection }: Props) => {
    const { visible } = subsection;
    const [isExpanded, setIsExpanded] = useState(true);

    const handleExpandedChange = (expanded: boolean) => {
        setIsExpanded(expanded);
    };

    return (
        <>
            {visible && (
                <div className={styles.subsection}>
                    <PreviewSubsectionHeader
                        subsection={subsection}
                        isExpanded={isExpanded}
                        onExpandedChange={handleExpandedChange}
                    />
                    <div className={styles.questionWrapper}>
                        {isExpanded &&
                            subsection.questions.map((question, k) => <PreviewQuestion question={question} key={k} />)}
                    </div>
                </div>
            )}
        </>
    );
};
