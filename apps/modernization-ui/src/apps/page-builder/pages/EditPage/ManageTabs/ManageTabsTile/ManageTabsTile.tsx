import './ManageTabsTile.scss';
import { PagesTab } from 'apps/page-builder/generated';
import { Draggable, DraggableProvided } from 'react-beautiful-dnd';
import { Icon } from '@trussworks/react-uswds';
import { Icon as IconComponent } from 'components/Icon/Icon';

type Props = {
    tab: PagesTab;
    index: number;
    setSelectedEditTab: (tab: PagesTab, index: number) => void;
    setDeleteTab: (tab: PagesTab) => void;
    selectedForDelete: PagesTab | undefined;
    setSelectedForDelete: (tab: PagesTab | undefined) => void;
    reset: () => void;
};

export const ManageTabsTile = ({
    tab,
    index,
    setSelectedEditTab,
    setDeleteTab,
    selectedForDelete,
    setSelectedForDelete,
    reset
}: Props) => {
    return (
        <Draggable draggableId={tab.id!.toString()} index={index}>
            {(provided: DraggableProvided) => (
                <div
                    className={`manage-tabs-tile ${tab.id === selectedForDelete?.id ? 'delete' : ''}`}
                    ref={provided.innerRef}
                    {...provided.draggableProps}>
                    <div className="manage-tabs-tile__handle" {...provided.dragHandleProps}>
                        <IconComponent name="drag" />
                        <p>Delete this tab?</p>
                    </div>
                    <div className="manage-tabs-tile__label">
                        <IconComponent name="folder" />
                        {tab.name}&nbsp;
                        {tab.sections && tab.sections.length ? '(' + tab.sections!.length + ')' : '(0)'}
                    </div>
                    <div className={`manage-tabs-tile__buttons ${tab.sections?.length !== 0 ? 'locked' : ''}`}>
                        <Icon.Edit
                            onClick={() => {
                                reset();
                                setSelectedEditTab(tab, index);
                            }}
                            size={3}
                        />
                        <Icon.Delete
                            onClick={() => {
                                tab.sections?.length === 0 ? (reset(), setSelectedForDelete(tab)) : null;
                            }}
                            size={3}
                        />
                        <div className="delete">
                            <p onClick={() => setSelectedForDelete(undefined)}>Cancel</p>
                            <p onClick={() => setDeleteTab(selectedForDelete!)}>Delete</p>
                        </div>
                    </div>
                </div>
            )}
        </Draggable>
    );
};
