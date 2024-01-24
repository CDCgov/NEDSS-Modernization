import { PagesSubSection } from 'apps/page-builder/generated';
import styles from './managesubsectiontile.module.scss';
import { Icon as NbsIcon } from 'components/Icon/Icon';
import { Icon, Button } from '@trussworks/react-uswds';
import { useState } from 'react';

type ManageSubsectionTileProps = {
    subsection: PagesSubSection;
    setOnAction?: (action: boolean) => void;
    action: boolean;
    onDelete?: (subsection: PagesSubSection) => void;
};

export const ManageSubsectionTile = ({ subsection, setOnAction, action, onDelete }: ManageSubsectionTileProps) => {
    const [deleteWarning, setDeleteWarning] = useState<PagesSubSection | undefined>(undefined);

    const deleteHeader = (curSubsection: PagesSubSection) => {
        if (curSubsection.questions.length !== 0) {
            return `Section cannot be deleted. This section contains elements (subsections and questions) inside it. Remove the contents first, and then the section can be deleted.`;
        } else {
            return `Are you sure you want to delete this section?`;
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
        <div className={styles.tile}>
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
                            <div className={styles.warningDrag}>
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
                    <div className={styles.handle}>
                        <NbsIcon name="drag" size="3" />
                    </div>
                    <div className={styles.label}>
                        <NbsIcon name="group" size="3" />
                        <span data-testid="manageSectionTileId">{`${subsection.name}(${subsection.questions.length})`}</span>
                    </div>

                    <div className={styles.buttons}>
                        <Button
                            type="button"
                            onClick={() => {
                                setOnAction?.(true);
                                console.log('edit');
                            }}
                            outline
                            disabled={action}
                            className={styles.iconBtn}>
                            <Icon.Edit style={{ cursor: 'pointer' }} size={3} />
                        </Button>
                        <Button
                            type="button"
                            className={styles.iconBtn}
                            outline
                            disabled={action}
                            onClick={() => {
                                setOnAction?.(true);
                                setDeleteWarning(subsection);
                            }}>
                            <Icon.Delete style={{ cursor: 'pointer' }} size={3} />
                        </Button>
                        <Button
                            type="button"
                            outline
                            disabled={action}
                            className={styles.iconBtn}
                            onClick={() => {
                                setOnAction?.(true);
                                console.log('visbility');
                            }}>
                            <Icon.Visibility style={{ cursor: 'pointer' }} size={3} />
                        </Button>
                    </div>
                </div>
            )}
        </div>
    );
};
