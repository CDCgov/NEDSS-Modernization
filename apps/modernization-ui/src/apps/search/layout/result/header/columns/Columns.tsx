import { ButtonActionMenu } from 'components/ButtonActionMenu/ButtonActionMenu';
import { Icon } from '@trussworks/react-uswds';
import { useColumnContext } from 'apps/search/context/ColumnContextProvider';
import { ColumnTile } from './ColumnTile';
import { DragDropContext, Droppable } from 'react-beautiful-dnd';
import styles from './Columns.module.scss';

export const Columns = () => {
    const { tempColumns, handleDragEnd, saveColumns, resetColumns } = useColumnContext();

    return (
        <DragDropContext onDragEnd={handleDragEnd}>
            <div className="columns">
                <ButtonActionMenu
                    outline
                    icon={<Icon.Settings size={4} />}
                    onClose={resetColumns}
                    menuTitle="Columns"
                    menuAction={saveColumns}
                    menuActionTitle="Save columns">
                    <div className={styles.columns}>
                        {tempColumns.slice(0, 4).map((column, i) => (
                            <ColumnTile column={column} key={column.id} index={i} lite />
                        ))}
                        <Droppable droppableId="all-columns">
                            {(provided) => (
                                <div {...provided.droppableProps} ref={provided.innerRef}>
                                    {tempColumns.slice(4, 9).map((column, i) => (
                                        <ColumnTile column={column} key={column.id} index={i + 4} />
                                    ))}
                                    {provided.placeholder}
                                </div>
                            )}
                        </Droppable>
                    </div>
                </ButtonActionMenu>
            </div>
        </DragDropContext>
    );
};
