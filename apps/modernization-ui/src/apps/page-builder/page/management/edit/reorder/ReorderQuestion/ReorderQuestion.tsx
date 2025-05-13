import { PagesQuestion } from 'apps/page-builder/generated';
import styles from './reorder-question.module.scss';
import { Icon } from 'components/Icon/Icon';
import { Draggable, DraggableProvided } from '@hello-pangea/dnd';

type Props = {
    question: PagesQuestion;
    index: number;
    visible: boolean;
};

export const ReorderQuestion = ({ question, index, visible }: Props) => {
    return (
        <Draggable draggableId={question.id!.toString()} index={index}>
            {(prov: DraggableProvided, snapshot) => (
                <div
                    className={`${styles.question} ${visible ? '' : styles.hidden} ${
                        snapshot.isDragging ? styles.dragging : ''
                    }`}
                    ref={prov.innerRef}
                    {...prov.draggableProps}>
                    <div className={styles.tile}>
                        <div className={styles.handle} {...prov.dragHandleProps}>
                            <Icon name={'drag'} size={'m'} />
                        </div>
                        <Icon name={'question'} size={'m'} />
                        {question.displayComponent === 1003 ? (
                            <p> &#60; Hyperlink &#62;</p>
                        ) : question.displayComponent === 1012 ? (
                            <p> &#60; Line separator &#62;</p>
                        ) : question.displayComponent === 1014 ? (
                            <p> &#60; Comment &#62;</p>
                        ) : (
                            <p>{question.name}</p>
                        )}
                    </div>
                </div>
            )}
        </Draggable>
    );
};
