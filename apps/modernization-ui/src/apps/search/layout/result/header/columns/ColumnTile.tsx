import { Icon } from 'components/Icon/Icon';
import styles from './ColumnTile.module.scss';
import { Checkbox } from 'design-system/checkbox/Checkbox';
import { DisplayColumn } from 'apps/search/context/ColumnContextProvider';
import { Draggable, DraggableProvided } from 'react-beautiful-dnd';

type Props = {
    column: DisplayColumn;
    index: number;
    lite?: boolean;
};

export const ColumnTile = ({ column, index, lite }: Props) => {
    return lite ? (
        <div className={`${styles.tile} ${column.sortable ? '' : styles.locked}`} data-testid="tile">
            <div className={styles.handle}>
                <Icon name="drag" />
            </div>
            <div className={styles.check}>
                <Checkbox
                    id="display"
                    name="display"
                    disabled={true}
                    selected={true}
                    selectable={{ label: column.name, value: column.name, name: column.name }}
                />
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
                            id="display"
                            name="display"
                            disabled={!column.sortable}
                            selected={column.visible}
                            selectable={{ label: column.name, value: column.name, name: column.name }}
                        />
                    </div>
                </div>
            )}
        </Draggable>
    );
};
