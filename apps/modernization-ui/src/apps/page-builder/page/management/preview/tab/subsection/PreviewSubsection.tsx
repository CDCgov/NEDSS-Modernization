import styles from './preview-subsection.module.scss';
import { PagesSubSection } from '../../../../../generated';
import { PreviewQuestion } from '../question/PreviewQuestion';
import { PreviewSubsectionHeader } from './PreviewSubsectionHeader';
import { useState } from 'react';
import { Button } from '@trussworks/react-uswds';
import React from 'react';

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
                    {isExpanded && (
                        <div className={styles.questionWrapper}>
                            {subsection.isGrouped ? (
                                <div className={styles.grouped}>
                                    <div className={styles.groupedForm}>
                                        {subsection.questions.map((question, k) => (
                                            <React.Fragment key={k}>
                                                {question.appearsInBatch && (
                                                    <div className={styles.groupedQuestionName}>
                                                        <span>{question.batchLabel ?? question.name}</span>
                                                    </div>
                                                )}
                                            </React.Fragment>
                                        ))}
                                    </div>
                                    <p className={styles.groupedInfo}>No data has been entered.</p>
                                    <div className={styles.groupedQuestionsSection}>
                                        {subsection.questions.map((question, k) => (
                                            <React.Fragment key={k}>
                                                {question.visible && (
                                                    <PreviewQuestion
                                                        question={question}
                                                        isGrouped={subsection.isGrouped}
                                                    />
                                                )}
                                            </React.Fragment>
                                        ))}
                                    </div>
                                    <div className={styles.footer}>
                                        <Button type="button" disabled={true}>
                                            Add
                                        </Button>
                                    </div>
                                </div>
                            ) : (
                                <>
                                    {subsection.questions.map((question, k) => (
                                        <React.Fragment key={k}>
                                            {question?.visible && <PreviewQuestion question={question} />}
                                        </React.Fragment>
                                    ))}
                                </>
                            )}
                        </div>
                    )}
                </div>
            )}
        </>
    );
};
