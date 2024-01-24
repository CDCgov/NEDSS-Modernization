import { PagesTab } from 'apps/page-builder/generated';
import { Draggable, DraggableProvided } from 'react-beautiful-dnd';
import { Icon } from '@trussworks/react-uswds';
import { Icon as IconComponent } from 'components/Icon/Icon';
import styles from './manageTabsTile.module.scss';

type Props = {
    tab: PagesTab;
    index: number;
    setSelectedForEdit: (tab: PagesTab, index: number) => void;
    setSelectedForDelete: (tab: PagesTab | undefined) => void;
    selectedForDelete: PagesTab | undefined;
    deleteTab: () => void;
    reset: () => void;
};

export const ManageTabsTile = ({
    tab,
    index,
    setSelectedForEdit,
    setSelectedForDelete,
    selectedForDelete,
    deleteTab,
    reset
}: Props) => {
    return (
        <Draggable draggableId={tab.id!.toString()} index={index}>
            {(provided: DraggableProvided) => (
                <div
                    className={`${styles.tile} ${tab === selectedForDelete ? styles.delete : ''}`}
                    ref={provided.innerRef}
                    {...provided.draggableProps}>
                    <div
                        className={`${styles.handle} ${selectedForDelete ? styles.inactive : ''}`}
                        {...provided.dragHandleProps}>
                        {selectedForDelete ? (
                            <IconComponent name="drag" color="inactive" />
                        ) : (
                            <IconComponent name="drag" />
                        )}
                    </div>
                    <div className={styles.label} data-testid={'label'}>
                        <IconComponent name="folder" />
                        {tab.name}&nbsp;
                        {tab.sections && tab.sections.length ? '(' + tab.sections!.length + ')' : '(0)'}
                    </div>
                    <div className={styles.buttons}>
                        <Icon.Edit
                            onClick={() => {
                                reset();
                                setSelectedForEdit(tab, index);
                            }}
                            size={3}
                        />
                        <Icon.Delete
                            onClick={() => {
                                reset(), setSelectedForDelete(tab);
                            }}
                            size={3}
                        />
                        {tab.visible ? <Icon.Visibility size={3} /> : <Icon.VisibilityOff size={3} />}
                        <div className={styles.delete_buttons}>
                            <p onClick={deleteTab}>Yes, delete</p> | <p onClick={() => reset()}>Cancel</p>
                        </div>
                    </div>
                </div>
            )}
        </Draggable>
    );
};
