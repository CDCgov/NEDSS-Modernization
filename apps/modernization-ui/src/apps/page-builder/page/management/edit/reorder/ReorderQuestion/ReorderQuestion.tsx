import { PagesQuestion } from 'apps/page-builder/generated';
import styles from './reorder-question.module.scss';
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
                    className={`${styles.question} ${visible ? '' : styles.hidden}`}
                    ref={prov.innerRef}
                    {...prov.draggableProps}>
                    <div className={styles.tile}>
                        <div className={styles.handle} {...prov.dragHandleProps}>
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
