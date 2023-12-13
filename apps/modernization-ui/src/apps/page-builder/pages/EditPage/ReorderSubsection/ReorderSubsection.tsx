import { useEffect, useState } from 'react';
import './ReorderSubsection.scss';
import { Icon } from 'components/Icon/Icon';
import { ReorderQuestion } from '../ReorderQuestion/ReorderQuestion';
import { PagesSubSection } from 'apps/page-builder/generated/models/PagesSubSection';
import { PagesQuestion } from 'apps/page-builder/generated';
import { Draggable, DraggableProvided, Droppable } from 'react-beautiful-dnd';
import { useDragDrop } from 'apps/page-builder/context/DragDropProvider';

type Props = {
    index: number;
    subsection: PagesSubSection;
    visible: boolean;
};

export const ReorderSubsection = ({ subsection, index, visible }: Props) => {
    const [questions, setQuestions] = useState<PagesQuestion[]>([]);
    const [questionsOpen, setQuestionsOpen] = useState(true);
    const { closeId } = useDragDrop();
    useEffect(() => {
        if (!subsection.questions) return;
        setQuestions(subsection.questions);
    }, [subsection]);

    useEffect(() => {
        if (closeId.id === subsection.id!.toString()) {
            setQuestionsOpen(false);
        } else setQuestionsOpen(true);
    }, [closeId]);

    return (
        <Draggable draggableId={subsection.id!.toString()} index={index}>
            {(provided: DraggableProvided) => (
                <div
                    className={`reorder-subsection ${visible ? '' : 'hidden'}`}
                    ref={provided.innerRef}
                    {...provided.draggableProps}>
                    <div className={`reorder-subsection__tile`}>
                        <div className="reorder-section__toggle" onClick={() => setQuestionsOpen(!questionsOpen)}>
                            {!questionsOpen ? (
                                <Icon name={'navigate-next'} size="xs" />
                            ) : (
                                <Icon name={'expand-more'} size="xs" />
                            )}
                        </div>
                        <div className="reorder-subsection__handle" {...provided.dragHandleProps}>
                            <Icon name={'drag'} size={'m'} />
                        </div>
                        <Icon name={'subsection'} size={'m'} />
                        {subsection.name}
                    </div>
                    <div className={`reorder-subsection__questions ${!questionsOpen ? 'closed' : ''}`}>
                        <Droppable droppableId={subsection.id!.toString()} type="question">
                            {(prov) => (
                                <div {...prov.droppableProps} ref={prov.innerRef} className="reorder-questions__tiles">
                                    {questions
                                        ? questions.map((question: any, i: number) => {
                                              return (
                                                  <ReorderQuestion
                                                      question={question}
                                                      key={question.id.toString()}
                                                      index={i}
                                                      visible={question.display}
                                                  />
                                              );
                                          })
                                        : null}
                                    {prov.placeholder}
                                </div>
                            )}
                        </Droppable>
                    </div>
                </div>
            )}
        </Draggable>
    );
};
