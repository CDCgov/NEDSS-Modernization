import { PagesSection } from 'apps/page-builder/generated';
import { Draggable, DraggableProvided } from 'react-beautiful-dnd';
import { Icon as IconComponent } from 'components/Icon/Icon';
import { Icon } from '@trussworks/react-uswds';
import styles from './manageSectionTile.module.scss';

type Props = {
    section: PagesSection;
    index: number;
    setSelectedForEdit: (section: PagesSection) => void;
    setSelectedForDelete: (section: PagesSection | undefined) => void;
    selectedForDelete: PagesSection | undefined;
    handleDelete: (section: PagesSection) => void;
    reset: () => void;
};

export const ManageSectionTile = ({
    section,
    index,
    setSelectedForEdit,
    setSelectedForDelete,
    selectedForDelete,
    handleDelete,
    reset
}: Props) => {
    return (
        <Draggable draggableId={section.id?.toString()} index={index} key={section.id?.toString()}>
            {(provided: DraggableProvided) => (
                <div
                    className={`${styles.manageSectionTile} ${
                        section.id === selectedForDelete?.id ? styles.manageSectionTilesDelete : null
                    }`}
                    ref={provided.innerRef}
                    {...provided.draggableProps}>
                    <div className={styles.handle} {...provided.dragHandleProps}>
                        <IconComponent name="drag" />
                        <p>Delete this tab?</p>
                    </div>
                    <div className={styles.label}>
                        <IconComponent name="group" />
                        <span data-testid="manageSectionTileId">
                            {section.name}&nbsp;
                            {section.subSections && section.subSections.length
                                ? '(' + section.subSections!.length + ')'
                                : '(0)'}
                        </span>
                    </div>
                    <div className={`${styles.buttons} ${section.subSections?.length !== 0 ? styles.locked : null}`}>
                        <Icon.Edit
                            onClick={() => {
                                reset();
                                setSelectedForEdit(section);
                            }}
                            size={3}
                        />
                        <Icon.Delete
                            onClick={() => {
                                section.subSections?.length === 0 ? (reset(), setSelectedForDelete(section)) : null;
                            }}
                            size={3}
                        />
                        <div className={styles.delete}>
                            <p onClick={() => setSelectedForDelete(undefined)}>Cancel</p>
                            <p onClick={() => handleDelete(selectedForDelete!)}>Delete</p>
                        </div>
                    </div>
                </div>
            )}
        </Draggable>
    );
};
