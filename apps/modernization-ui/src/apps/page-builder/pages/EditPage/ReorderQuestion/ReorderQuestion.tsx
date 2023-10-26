import { PagesQuestion } from 'apps/page-builder/generated';
import './ReorderQuestion.scss';
import { Icon } from 'components/Icon/Icon';

type Props = {
    question: PagesQuestion;
};

export const ReorderQuestion = ({ question }: Props) => {
    return (
        <div className="reorder-question">
            <div className="reorder-question__tile">
                <Icon name={'drag'} size={'m'} />
                <Icon name={'question'} size={'m'} />
                <p>{question.name}</p>
            </div>
        </div>
    );
};
