import { useState } from 'react';
import './ReorderSubsection.scss';
import { Icon } from 'components/Icon/Icon';
import { ReorderQuestion } from '../ReorderQuestion/ReorderQuestion';
import { PageSubSection } from 'apps/page-builder/generated/models/PageSubSection';

type Props = {
    subsection: PageSubSection;
};

export const ReorderSubsection = ({ subsection }: Props) => {
    const [questionsOpen, setQuestionsOpen] = useState(true);

    return (
        <div className="reorder-subsection">
            <div className="reorder-subsection__tile">
                <div className="reorder-section__toggle" onClick={() => setQuestionsOpen(!questionsOpen)}>
                    {questionsOpen ? (
                        <Icon name={'expand-more'} size="xs" />
                    ) : (
                        <Icon name={'navigate-next'} size="xs" />
                    )}
                </div>
                <Icon name={'drag'} size={'m'} />
                <Icon name={'subsection'} size={'m'} />
                {subsection.name}
            </div>
            <div className={`reorder-subsection__questions ${questionsOpen ? '' : 'closed'}`}>
                {subsection && subsection.pageQuestions
                    ? subsection.pageQuestions.map((question: any, i: number) => {
                          if (question.dispay === 'T') {
                              return <ReorderQuestion question={question} key={i} />;
                          }
                      })
                    : null}
            </div>
        </div>
    );
};
