import { PagesQuestion } from 'apps/page-builder/generated';
import './ReorderQuestion.scss';
import { Icon } from 'components/Icon/Icon';
import { Draggable, DraggableProvided } from 'react-beautiful-dnd';

type Props = {
    question: PagesQuestion;
    index: number;
    visible: boolean;
};

export const ReorderQuestion = ({ question, index, visible }: Props) => {
    return (
        <Draggable draggableId={question.id!.toString()} index={index}>
            {(prov: DraggableProvided) => (
                <div
                    className={`reorder-question ${visible ? '' : 'hidden'}`}
                    ref={prov.innerRef}
                    {...prov.draggableProps}>
                    <div className="reorder-question__tile">
                        <div className="reorder-question__handle" {...prov.dragHandleProps}>
                            <Icon name={'drag'} size={'m'} />
                        </div>
                        <Icon name={'question'} size={'m'} />
                        <p>{question.name}</p>
                    </div>
                </div>
            )}
        </Draggable>
    );
};
