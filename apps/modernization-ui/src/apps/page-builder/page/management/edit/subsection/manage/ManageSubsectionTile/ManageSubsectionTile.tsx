import { PagesSubSection } from 'apps/page-builder/generated';
import styles from './managesubsectiontile.module.scss';
import { Icon as NbsIcon } from 'components/Icon/Icon';
import { Icon, Button } from '@trussworks/react-uswds';
import { useState } from 'react';
import { Draggable, DraggableProvided } from 'react-beautiful-dnd';

type ManageSubsectionTileProps = {
    subsection: PagesSubSection;
    setOnAction?: (action: boolean) => void;
    setEdit: (subsection: PagesSubSection) => void;
    action: boolean;
    onDelete?: (subsection: PagesSubSection) => void;
    index: number;
    onChangeVisibility: (subsection: PagesSubSection, visibility: boolean) => void;
};

export const ManageSubsectionTile = ({
    subsection,
    setOnAction,
    setEdit,
    action,
    onDelete,
    index,
    onChangeVisibility
}: ManageSubsectionTileProps) => {
    const [deleteWarning, setDeleteWarning] = useState<PagesSubSection | undefined>(undefined);

    const deleteHeader = (curSubsection: PagesSubSection) => {
        if (curSubsection.questions.length !== 0) {
            return `Subsection cannot be deleted. This subsection contains elements (questions) inside it. Remove the contents first, and then the subsection can be deleted.`;
        } else {
            return `Are you sure you want to delete this subsection?`;
        }
    };

    const isValidDelete = (curSubsection: PagesSubSection) => {
        if (curSubsection.questions.length !== 0) {
            return false;
        } else {
            return true;
        }
    };

    return (
        <Draggable draggableId={subsection.id?.toString()} index={index} key={subsection.id?.toString()}>
            {(provided: DraggableProvided) => (
                <div className={styles.tile} ref={provided.innerRef} {...provided.draggableProps}>
                    {deleteWarning !== undefined && deleteWarning.id === subsection.id ? (
                        <div className={styles.warningModal}>
                            <div className={styles.warningModalHeader}>
                                <div className={styles.warningIcon}>
                                    <Icon.Warning size={3} />
                                </div>
                                {deleteHeader?.(subsection)}
                            </div>
                            <div className={styles.warningModalContent}>
                                <div className={styles.content}>
                                    <div className={styles.warningDrag} {...provided.dragHandleProps}>
                                        <NbsIcon name="drag" size="3" />
                                    </div>
                                    <div className={styles.warningGroup}>
                                        <NbsIcon name="group" size="3" />
                                    </div>
                                    <div>{`${deleteWarning.name}(${subsection.questions.length})`}</div>
                                </div>
                                <div className={styles.warningModalBtns}>
                                    {isValidDelete(subsection) ? (
                                        <>
                                            <div
                                                onClick={() => {
                                                    onDelete?.(subsection);
                                                    setDeleteWarning(undefined);
                                                    setOnAction?.(false);
                                                }}>
                                                Yes, delete
                                            </div>
                                            <div className={styles.separator}>|</div>
                                            <div
                                                onClick={() => {
                                                    setDeleteWarning(undefined);
                                                    setOnAction?.(false);
                                                }}>
                                                Cancel
                                            </div>
                                        </>
                                    ) : (
                                        <div
                                            data-testid="subsectionOkLink"
                                            onClick={() => {
                                                setDeleteWarning(undefined);
                                                setOnAction?.(false);
                                            }}>
                                            OK
                                        </div>
                                    )}
                                </div>
                            </div>
                        </div>
                    ) : (
                        <div className={styles.manageSubsectionTile}>
                            <div className={styles.handle} {...provided.dragHandleProps} data-testid="dragAndDropIcon">
                                <NbsIcon name="drag" size="3" />
                            </div>
                            <div className={styles.label} data-testid={'label'}>
                                <NbsIcon name="group" size="3" />
                                <span data-testid="manageSectionTileId">{`${subsection.name} (${subsection.questions.length})`}</span>
                            </div>

                            <div className={styles.buttons}>
                                <Button
                                    type="button"
                                    onClick={() => {
                                        setEdit(subsection);
                                    }}
                                    outline
                                    disabled={action}
                                    data-testid="subsectionTileEditIcon"
                                    className={styles.iconBtn}>
                                    <Icon.Edit style={{ cursor: 'pointer' }} size={3} />
                                </Button>
                                <Button
                                    type="button"
                                    data-testid="subsectionTileDeleteIcon"
                                    className={styles.iconBtn}
                                    outline
                                    disabled={action}
                                    onClick={() => {
                                        setDeleteWarning(subsection);
                                        setOnAction?.(true);
                                    }}>
                                    <Icon.Delete style={{ cursor: 'pointer' }} size={3} />
                                </Button>
                                {subsection.visible ? (
                                    <Button
                                        type="button"
                                        outline
                                        data-testid="subsectionTileVisibilityIcon-on"
                                        className={styles.iconBtn}
                                        disabled={action}
                                        onClick={() => {
                                            onChangeVisibility(subsection, false);
                                        }}>
                                        <Icon.Visibility style={{ cursor: 'pointer' }} size={3} />
                                    </Button>
                                ) : (
                                    <Button
                                        type="button"
                                        outline
                                        data-testid="subsectionTileVisibilityIcon-off"
                                        className={`${styles.iconBtn} ${styles.offVisibility}`}
                                        disabled={action}
                                        onClick={() => {
                                            onChangeVisibility(subsection, true);
                                        }}>
                                        <Icon.VisibilityOff style={{ cursor: 'pointer' }} size={3} />
                                    </Button>
                                )}
                            </div>
                        </div>
                    )}
                </div>
            )}
        </Draggable>
    );
};
