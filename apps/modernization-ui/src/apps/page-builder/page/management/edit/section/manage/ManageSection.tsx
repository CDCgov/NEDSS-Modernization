import { Button, Icon } from '@trussworks/react-uswds';
import { PagesSection, PagesTab, SectionControllerService } from 'apps/page-builder/generated';
import { Icon as NbsIcon } from 'components/Icon/Icon';
import styles from './managesection.module.scss';
import { useState } from 'react';
import { AddSection } from './AddSection';
import { AlertInLineProps } from './ManageSectionModal';
import { ManageSectionTile } from './ManageSectionTile/ManageSectionTile';
import { useDragDrop } from 'apps/page-builder/context/DragDropProvider';
import { DragDropContext, Droppable } from 'react-beautiful-dnd';

type ManageSectionProps = {
    pageId: number;
    tab?: PagesTab;
    onCancel?: () => void;
    onContentChange?: () => void;
    alert?: AlertInLineProps;
    onDeleteSection?: (section: string) => void;
    onResetAlert?: () => void;
    onUpdateSection?: () => void;
    onAddSection?: (section: string) => void;
    onHiddenSection?: () => void;
    onUnhiddenSection?: () => void;
};

export const ManageSection = ({
    onCancel,
    tab,
    onContentChange,
    pageId,
    alert,
    onDeleteSection,
    onResetAlert,
    onUpdateSection,
    onAddSection,
    onHiddenSection,
    onUnhiddenSection
}: ManageSectionProps) => {
    const [sectionState, setSectionState] = useState<'manage' | 'add' | 'edit'>('manage');

    const [confirmDelete, setConfirmDelete] = useState<PagesSection | undefined>(undefined);

    const [sectionEdit, setSectionEdit] = useState<PagesSection | undefined>();

    const [onAction, setOnAction] = useState<boolean>(false);

    const handleUpdateState = (state: 'manage' | 'add' | 'edit') => {
        setSectionState(state);
    };

    const onDelete = (section: PagesSection) => {
        SectionControllerService.deleteSection({
            page: pageId,
            sectionId: section.id
        }).then(() => {
            onContentChange?.();
            onDeleteSection?.(section.name);
        });
    };

    const onChangeVisibility = (section: PagesSection, visibility: boolean) => {
        SectionControllerService.updateSection({
            page: pageId,
            requestBody: { name: section.name, visible: visibility },
            section: section.id
        }).then(() => {
            onContentChange?.();
            if (visibility) {
                onUnhiddenSection?.();
            } else {
                onHiddenSection?.();
            }
        });
    };

    const { handleDragEnd, handleDragStart, handleDragUpdate } = useDragDrop();
    return (
        <>
            {sectionState === 'add' && (
                <AddSection
                    pageId={pageId}
                    onSectionTouched={() => {
                        onContentChange?.();
                        setSectionState('manage');
                    }}
                    onAddSection={onAddSection}
                    onCancel={() => setSectionState('manage')}
                    tabId={tab?.id}
                    isEdit={false}
                />
            )}
            {sectionState === 'edit' && (
                <AddSection
                    pageId={pageId}
                    onSectionTouched={() => {
                        onContentChange?.();
                        setSectionState('manage');
                        setSectionEdit(undefined);
                        onUpdateSection?.();
                    }}
                    onCancel={() => {
                        setSectionState('manage');
                        setSectionEdit(undefined);
                    }}
                    tabId={tab?.id}
                    isEdit={true}
                    section={sectionEdit}
                />
            )}
            {sectionState === 'manage' && (
                <div className={styles.managesection}>
                    <div className={styles.header}>
                        <div className={styles.manageSectionHeader} data-testid="header">
                            <h2>Manage sections</h2>
                        </div>
                        <div className={styles.addSectionHeader}>
                            <Button
                                type="button"
                                onClick={() => {
                                    handleUpdateState('add');
                                }}
                                className={`${styles.addSectionBtn} addNewSectionBtn`}
                                disabled={onAction}>
                                <Icon.Add size={3} className={styles.addIcon} />
                                Add new section
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
                        <div className={styles.tab}>
                            <div className={styles.folderIcon}>
                                <NbsIcon name={'folder'} />
                            </div>
                            <p className={styles.tabName}>{tab?.name}</p>
                        </div>
                        <DragDropContext
                            onDragEnd={handleDragEnd}
                            onDragStart={handleDragStart}
                            onDragUpdate={handleDragUpdate}>
                            <Droppable droppableId="all-sections" type="sections">
                                {(provided) => (
                                    <div
                                        className="manage-sections"
                                        {...provided.droppableProps}
                                        ref={provided.innerRef}>
                                        {tab?.sections?.map((section, k) => {
                                            return (
                                                <ManageSectionTile
                                                    section={section}
                                                    index={k}
                                                    key={k}
                                                    setSelectedForDelete={setConfirmDelete}
                                                    selectedForDelete={confirmDelete}
                                                    handleDelete={onDelete}
                                                    setOnAction={setOnAction}
                                                    onAction={onAction}
                                                    setSectionState={setSectionState}
                                                    setSelectedForEdit={setSectionEdit}
                                                    onChangeVisibility={onChangeVisibility}
                                                />
                                            );
                                        })}
                                        {provided.placeholder}
                                    </div>
                                )}
                            </Droppable>
                        </DragDropContext>
                    </div>
                    <div className={styles.footer}>
                        <Button
                            onClick={() => {
                                onCancel?.();
                                setConfirmDelete(undefined);
                            }}
                            type={'button'}
                            outline
                            className="manageSectionsCloseBtn">
                            Close
                        </Button>
                    </div>
                </div>
            )}
        </>
    );
};
