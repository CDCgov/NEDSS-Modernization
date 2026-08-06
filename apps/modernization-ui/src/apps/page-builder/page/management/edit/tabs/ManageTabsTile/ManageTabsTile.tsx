import { Draggable, DraggableProvided } from '@hello-pangea/dnd';
import { Icon } from '@trussworks/react-uswds';

import { PagesTab } from 'apps/page-builder/generated';
import { Icon as IconComponent } from 'components/Icon/Icon';

import styles from './manageTabsTile.module.scss';

type Props = {
    tab: PagesTab;
    index: number;
    setSelectedForEdit: (tab: PagesTab, index: number) => void;
    setSelectedForDelete: (tab: PagesTab | undefined) => void;
    onChangeVisibility: (tab: PagesTab) => void;
    selectedForDelete: PagesTab | undefined;
    deleteTab: () => void;
    reset: () => void;
};

export const ManageTabsTile = ({
    tab,
    index,
    setSelectedForEdit,
    setSelectedForDelete,
    onChangeVisibility,
    selectedForDelete,
    deleteTab,
    reset,
}: Props) => {
    return (
        <Draggable draggableId={tab.id!.toString()} index={index}>
            {(provided: DraggableProvided, snapshot) => (
                <div
                    className={`${styles.tile} ${tab === selectedForDelete ? styles.delete : ''} ${
                        snapshot.isDragging ? styles.dragging : ''
                    }`}
                    ref={provided.innerRef}
                    {...provided.draggableProps}
                >
                    <div className={styles.tabInfo}>
                        <div
                            className={`${styles.handle} ${selectedForDelete ? styles.inactive : ''}`}
                            {...provided.dragHandleProps}
                        >
                            <IconComponent
                                name="drag"
                                color={selectedForDelete ? 'inactive' : undefined}
                                alt="drag handle"
                            />
                        </div>
                        <div className={styles.label} data-testid="label">
                            <IconComponent name="folder" alt="folder" />
                            {tab.name}&nbsp;
                            {tab.sections && tab.sections.length ? '(' + tab.sections!.length + ')' : '(0)'}
                        </div>
                    </div>
                    <div className={styles.buttons}>
                        <Icon.Edit
                            aria-label="edit"
                            onClick={() => {
                                reset();
                                setSelectedForEdit(tab, index);
                            }}
                            size={3}
                        />
                        <Icon.Delete
                            aria-label="delete"
                            onClick={() => {
                                reset();
                                setSelectedForDelete(tab);
                            }}
                            size={3}
                        />
                        {tab.visible ? (
                            <Icon.Visibility aria-label="hide" size={3} onClick={() => onChangeVisibility(tab)} />
                        ) : (
                            <Icon.VisibilityOff aria-label="show" size={3} onClick={() => onChangeVisibility(tab)} />
                        )}
                        <div className={styles.delete_buttons}>
                            <p onClick={deleteTab}>Yes, delete</p> | <p onClick={() => reset()}>Cancel</p>
                        </div>
                    </div>
                </div>
            )}
        </Draggable>
    );
};
