import { Icon } from 'components/Icon/Icon';
import styles from './ColumnTile.module.scss';
import { Checkbox } from '@trussworks/react-uswds';
import { DisplayColumn, useColumnContext } from 'apps/search/context/ColumnContextProvider';
import { Draggable, DraggableProvided } from 'react-beautiful-dnd';

type Props = {
    column: DisplayColumn;
    index: number;
    lite?: boolean;
};

export const ColumnTile = ({ column, index, lite }: Props) => {
    const { toggleHide } = useColumnContext();
    return lite ? (
        <div className={`${styles.tile} ${column.sortable ? '' : styles.locked}`} data-testid="tile">
            <div className={styles.handle}>
                <Icon name="drag" />
            </div>
            <div className={styles.check}>
                <Checkbox id={column.id} name={column.name} disabled={true} label={column.name} checked={true} />
            </div>
        </div>
    ) : (
        <Draggable draggableId={column.id} index={index}>
            {(provided: DraggableProvided) => (
                <div
                    className={`tile ${styles.tile} ${column.sortable ? '' : styles.locked}`}
                    ref={provided.innerRef}
                    {...provided.draggableProps}
                    data-testid="tile">
                    <div className={styles.handle} {...provided.dragHandleProps}>
                        <Icon name="drag" />
                    </div>
                    <div className={styles.check}>
                        <Checkbox
                            id={column.id + 'C'}
                            name={column.name}
                            label={column.name}
                            checked={column.visible}
                            onChange={() => toggleHide(column.id)}
                        />
                    </div>
                </div>
            )}
        </Draggable>
    );
};
