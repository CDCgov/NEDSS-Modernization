import { useState } from 'react';
import './ReorderSubsection.scss';
import { Icon } from 'components/Icon/Icon';
import { ReorderQuestion } from '../ReorderQuestion/ReorderQuestion';
import { PagesSubSection } from 'apps/page-builder/generated';

type Props = {
    subsection: PagesSubSection;
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
                {subsection && subsection.questions
                    ? subsection.questions.map((question, i) => {
                          if (question.display) {
                              return <ReorderQuestion question={question} key={i} />;
                          }
                      })
                    : null}
            </div>
        </div>
    );
};
