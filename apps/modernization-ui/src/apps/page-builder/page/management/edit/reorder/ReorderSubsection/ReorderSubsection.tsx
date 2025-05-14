import { useEffect, useState } from 'react';
import styles from './reorder-subsection.module.scss';
import { Icon } from 'components/Icon/Icon';
import { ReorderQuestion } from '../ReorderQuestion/ReorderQuestion';
import { PagesSubSection } from 'apps/page-builder/generated/models/PagesSubSection';
import { PagesQuestion } from 'apps/page-builder/generated';
import { Draggable, DraggableProvided, Droppable } from '@hello-pangea/dnd';
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
        }
    }, [closeId]);

    return (
        <Draggable draggableId={subsection.id!.toString()} index={index}>
            {(provided: DraggableProvided, snapshot) => (
                <div
                    className={`${styles.subsection} ${visible ? '' : styles.hidden} ${
                        snapshot.isDragging ? styles.dragging : ''
                    }`}
                    ref={provided.innerRef}
                    {...provided.draggableProps}>
                    <div className={styles.tile}>
                        <div className={styles.toggle} onClick={() => setQuestionsOpen(!questionsOpen)}>
                            {!questionsOpen ? (
                                <Icon name={'navigate-next'} size="s" />
                            ) : (
                                <Icon name={'expand-more'} size="s" />
                            )}
                        </div>
                        <div className={styles.handle} {...provided.dragHandleProps}>
                            <Icon name={'drag'} size={'m'} />
                        </div>
                        <Icon name={'subsection'} size={'m'} />
                        {subsection.name}
                    </div>
                    <div className={`${styles.questions} ${!questionsOpen ? styles.closed : ''}`}>
                        <Droppable droppableId={subsection.id!.toString()} type="question">
                            {(prov, snapshot) => (
                                <div
                                    className={styles.droppable}
                                    {...prov.droppableProps}
                                    ref={prov.innerRef}
                                    style={{ backgroundColor: snapshot.isDraggingOver ? '#d9e8f6' : 'white' }}>
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
