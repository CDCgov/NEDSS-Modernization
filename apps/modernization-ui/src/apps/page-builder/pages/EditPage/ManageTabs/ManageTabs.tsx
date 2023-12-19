import { PagesTab } from 'apps/page-builder/generated';
import './ManageTabs.scss';
import { useDragDrop } from 'apps/page-builder/context/DragDropProvider';
import { DragDropContext, Droppable } from 'react-beautiful-dnd';
import { ManageTabsTile } from './ManageTabsTile/ManageTabsTile';

type Props = {
    setSelectedEditTab: (tab: PagesTab, index: number) => void;
    setDeleteTab: (tab: PagesTab) => void;
    selectedForDelete: PagesTab | undefined;
    setSelectedForDelete: (tab: PagesTab | undefined) => void;
    reset: () => void;
};

const ManageTabs = ({ setSelectedEditTab, setDeleteTab, selectedForDelete, setSelectedForDelete, reset }: Props) => {
    const { tabs, handleDragEnd, handleDragStart, handleDragUpdate } = useDragDrop();
    return (
        <DragDropContext onDragEnd={handleDragEnd} onDragStart={handleDragStart} onDragUpdate={handleDragUpdate}>
            <Droppable droppableId="all-tabs" type="tabs">
                {(provided) => (
                    <div className="manage-tabs" {...provided.droppableProps} ref={provided.innerRef}>
                        {tabs.map((tab, i) => {
                            return (
                                <ManageTabsTile
                                    key={tab.id!.toString()}
                                    tab={tab}
                                    index={i}
                                    setSelectedEditTab={setSelectedEditTab}
                                    setDeleteTab={setDeleteTab}
                                    selectedForDelete={selectedForDelete}
                                    setSelectedForDelete={setSelectedForDelete}
                                    reset={reset}
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

export default ManageTabs;
