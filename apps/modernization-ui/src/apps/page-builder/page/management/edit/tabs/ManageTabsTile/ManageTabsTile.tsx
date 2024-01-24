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
    reset: () => void;
};

export const ManageTabsTile = ({
    tab,
    index,
    setSelectedForEdit,
    setSelectedForDelete,
    selectedForDelete,
    reset
}: Props) => {
    return (
        <Draggable draggableId={tab.id!.toString()} index={index}>
            {(provided: DraggableProvided) => (
                <div
                    className={`${styles.tile} ${tab === selectedForDelete ? styles.delete : ''}`}
                    ref={provided.innerRef}
                    {...provided.draggableProps}>
                    <div className={styles.handle} {...provided.dragHandleProps}>
                        <IconComponent name="drag" />
                        <p>Delete this tab?</p>
                    </div>
                    <div className={styles.label} data-testid={'label'}>
                        <IconComponent name="folder" />
                        {tab.name}&nbsp;
                        {tab.sections && tab.sections.length ? '(' + tab.sections!.length + ')' : '(0)'}
                    </div>
                    <div className={`${styles.buttons} ${tab.sections?.length === 0 ? styles.locked : ''}`}>
                        <Icon.Edit
                            onClick={() => {
                                reset();
                                setSelectedForEdit(tab, index);
                            }}
                            size={3}
                        />
                        <Icon.Delete
                            onClick={() => {
                                tab.sections?.length !== 0 ? (reset(), setSelectedForDelete(tab)) : null;
                            }}
                            size={3}
                        />
                        {tab.visible ? <Icon.Visibility size={3} /> : <Icon.VisibilityOff size={3} />}
                        <div className={styles.deleteb}>
                            <p onClick={() => setSelectedForDelete(undefined)}>Cancel</p>
                            <p onClick={selectedForDelete ? () => setSelectedForDelete(selectedForDelete!) : undefined}>
                                Delete
                            </p>
                        </div>
                    </div>
                </div>
            )}
        </Draggable>
    );
};
