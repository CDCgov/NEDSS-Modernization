import { useDragDrop } from 'apps/page-builder/context/DragDropProvider';
import { PagesTab } from 'apps/page-builder/generated';
import { deleteTab, updateTab } from 'apps/page-builder/services/tabsAPI';
import { useState } from 'react';
import { DragDropContext, Droppable } from '@hello-pangea/dnd';
import { ManageTabsTile } from '../ManageTabsTile/ManageTabsTile';
import styles from './reorderable-tabs.module.scss';

type Props = {
    page: number;
    tabs: PagesTab[];
    onEdit: (tab: PagesTab | undefined) => void;
    onTabChanged: (message: string) => void;
    onDeleteError: () => void;
};
export const ReorderableTabs = ({ page, tabs, onEdit, onTabChanged, onDeleteError }: Props) => {
    const { handleDragEnd, handleDragStart, handleDragUpdate } = useDragDrop();
    const [selectedForDelete, setSelectedForDelete] = useState<PagesTab | undefined>(undefined);

    const handleVisibilityChange = (tab: PagesTab) => {
        updateTab(page, { name: tab.name, visible: !tab.visible }, tab.id)
            .catch(() => console.error('Failed to update tab visibility'))
            .then(() => {
                onTabChanged(`Successfully updated tab visibility`);
            });
    };

    const handleDelete = () => {
        if (selectedForDelete) {
            if (selectedForDelete.sections.length > 0) {
                onDeleteError();
                setSelectedForDelete(undefined);
            } else {
                deleteTab(page, selectedForDelete.id)
                    .catch((e) => {
                        console.error(e);
                    })
                    .then(() => {
                        onTabChanged(`You've successfully deleted ${selectedForDelete.name}!`);
                        setSelectedForDelete(undefined);
                    });
            }
        }
    };

    const handleCancelDelete = () => {
        setSelectedForDelete(undefined);
    };

    return (
        <DragDropContext onDragEnd={handleDragEnd} onDragStart={handleDragStart} onDragUpdate={handleDragUpdate}>
            <Droppable droppableId="all-tabs" type="tab">
                {(provided, snapshot) => (
                    <div
                        className={styles.tabList}
                        {...provided.droppableProps}
                        ref={provided.innerRef}
                        style={{
                            backgroundColor: snapshot.isDraggingOver ? '#d9e8f6' : 'white'
                        }}>
                        {tabs.map((tab, i) => {
                            return (
                                <ManageTabsTile
                                    key={tab.id!.toString()}
                                    tab={tab}
                                    index={i}
                                    setSelectedForEdit={onEdit}
                                    setSelectedForDelete={setSelectedForDelete}
                                    selectedForDelete={selectedForDelete}
                                    deleteTab={handleDelete}
                                    reset={handleCancelDelete}
                                    onChangeVisibility={handleVisibilityChange}
                                />
                            );
                        })}
                        {provided.placeholder}
                    </div>
                )}
            </Droppable>
        </DragDropContext>
    );
};
