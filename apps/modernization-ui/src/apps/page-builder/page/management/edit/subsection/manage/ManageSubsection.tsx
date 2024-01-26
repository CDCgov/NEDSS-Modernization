import { useState } from 'react';
import styles from './managesubsection.module.scss';
import { Button, Icon } from '@trussworks/react-uswds';
import { Heading } from 'components/heading';
import { AlertInLineProps } from '../../section/manage/ManageSectionModal';
import { Icon as NbsIcon } from 'components/Icon/Icon';
import { PagesSection, PagesSubSection, SubSectionControllerService } from 'apps/page-builder/generated';
import { ManageSubsectionTile } from './ManageSubsectionTile/ManageSubsectionTile';
import { AddSubSection } from './AddSubSection';
import { usePageManagement } from '../../../usePageManagement';
import { authorization } from 'authorization';
import { DragDropContext, Droppable } from 'react-beautiful-dnd';
import { useDragDrop } from 'apps/page-builder/context/DragDropProvider';

type ManageSubsectionProps = {
    alert?: AlertInLineProps;
    onResetAlert?: () => void;
    section: PagesSection;
    onCancel?: () => void;
    onSetAlert?: (message: string, type: 'success' | 'error' | 'warning' | 'info') => void;
};

export const ManageSubsection = ({ alert, onResetAlert, section, onSetAlert, onCancel }: ManageSubsectionProps) => {
    const { handleDragEnd, handleDragStart, handleDragUpdate } = useDragDrop();
    const [subsectionState, setSubsectionState] = useState<'manage' | 'add' | 'edit'>('manage');
    const { page, refresh } = usePageManagement();
    const [onAction, setOnAction] = useState<boolean>(false);

    const handleUpdateState = (state: 'manage' | 'add' | 'edit') => {
        setSubsectionState(state);
    };

    const onDelete = (subsection: PagesSubSection) => {
        SubSectionControllerService.deleteSubSectionUsingDelete({
            authorization: authorization(),
            page: page.id,
            subSectionId: subsection.id
        }).then(() => {
            onSetAlert?.(`You have successfully deleted "${subsection.name}"`, `success`);
            refresh?.();
        });
    };

    return (
        <DragDropContext onDragEnd={handleDragEnd} onDragStart={handleDragStart} onDragUpdate={handleDragUpdate}>
            {subsectionState === 'add' && (
                <AddSubSection
                    sectionId={section.id}
                    pageId={page.id}
                    onCancel={() => {
                        handleUpdateState('manage');
                    }}
                    onSubSectionTouched={(section: string) => {
                        onSetAlert?.(`You have successfully added subsection "${section}"`, `success`);
                        handleUpdateState('manage');
                        refresh();
                    }}
                />
            )}
            {subsectionState === 'manage' && (
                <div className={styles.manageSubsection}>
                    <div className={styles.header}>
                        <div className={styles.manageSubsectionHeader} data-testid="header">
                            <Heading level={4}>Manage subsections</Heading>
                        </div>
                        <div className={styles.addSubsectionHeader}>
                            <Button
                                type="button"
                                onClick={() => {
                                    handleUpdateState('add');
                                }}
                                className={styles.addSubsectionBtn}
                                disabled={onAction}>
                                <Icon.Add size={3} className={styles.addIcon} />
                                Add new subsection
                            </Button>
                        </div>
                    </div>
                    <div className={styles.content}>
                        {alert !== undefined && (
                            <div className={styles.alert}>
                                <div className={styles.checkCircle}>
                                    <Icon.CheckCircle size={3} />
                                </div>
                                <div className={styles.alertContent}>
                                    <div className={styles.alertMessage}>{alert.message}</div>
                                    <div className={styles.closeBtn}>
                                        <Icon.Close size={3} onClick={() => onResetAlert?.()} />
                                    </div>
                                </div>
                            </div>
                        )}
                        <div className={styles.section}>
                            <div className={styles.folderIcon}>
                                <NbsIcon name={'folder'} />
                            </div>
                            <p className={styles.sectionName}>{section?.name}</p>
                        </div>
                        <Droppable droppableId={section.id!.toString()} type="subsection">
                            {(provided) => (
                                <div
                                    className="manage-subsections"
                                    {...provided.droppableProps}
                                    ref={provided.innerRef}>
                                    {section.subSections.map((s, k) => {
                                        return (
                                            <ManageSubsectionTile
                                                action={onAction}
                                                subsection={s}
                                                key={k}
                                                setOnAction={setOnAction}
                                                onDelete={onDelete}
                                                index={k}
                                            />
                                        );
                                    })}
                                    {provided.placeholder}
                                </div>
                            )}
                        </Droppable>
                    </div>
                    <div className={styles.footer}>
                        <Button
                            onClick={() => {
                                onCancel?.();
                            }}
                            type={'button'}
                            outline>
                            Close
                        </Button>
                    </div>
                </div>
            )}
        </DragDropContext>
    );
};
