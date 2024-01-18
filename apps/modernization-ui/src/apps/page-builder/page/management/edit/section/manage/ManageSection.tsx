import { Button, Icon } from '@trussworks/react-uswds';
import { PagesSection, PagesTab } from 'apps/page-builder/generated';
import { Icon as NbsIcon } from 'components/Icon/Icon';
import styles from './managesection.module.scss';
import { useState } from 'react';
import { AddSection } from './AddSection';
import { Heading } from 'components/heading';
import { ManageSectionTile } from './ManageSectionTile/ManageSectionTile';
import { useDragDrop } from 'apps/page-builder/context/DragDropProvider';
import { DragDropContext, Droppable } from 'react-beautiful-dnd';

type ManageSectionProps = {
    pageId: number;
    tab: PagesTab;
    onCancel?: () => void;
    onContentChange?: () => void;
    setSelectedForEdit: (section: PagesSection | undefined) => void;
    selectedForEdit: PagesSection | undefined;
    setSelectedForDelete: (section: PagesSection | undefined) => void;
    selectedForDelete: PagesSection | undefined;
    handleDelete: (section: PagesSection) => void;
    reset: () => void;
};

export const ManageSection = ({
    pageId,
    tab,
    onContentChange,
    onCancel,
    setSelectedForEdit,
    selectedForEdit,
    setSelectedForDelete,
    selectedForDelete,
    handleDelete,
    reset
}: ManageSectionProps) => {
    const [sectionState, setSectionState] = useState<'manage' | 'add'>('manage');

    const handleUpdateState = (state: 'manage' | 'add') => {
        setSectionState(state);
    };

    const { handleDragEnd, handleDragStart, handleDragUpdate } = useDragDrop();
    return (
        <>
            {sectionState === 'add' && (
                <AddSection
                    pageId={pageId}
                    onAddSectionCreated={() => {
                        onContentChange?.();
                        setSectionState('manage');
                    }}
                    onCancel={() => setSectionState('manage')}
                    tabId={tab.id}
                    selectedForEdit={selectedForEdit}
                />
            )}
            {sectionState === 'manage' && (
                <div className={styles.managesection}>
                    <div className={styles.header}>
                        <div className={styles.manageSectionHeader} data-testid="header">
                            <Heading level={4}>Manage sections</Heading>
                        </div>
                        <div className={styles.addSectionHeader}>
                            <Button
                                type="button"
                                onClick={() => {
                                    handleUpdateState('add');
                                }}
                                className={styles.addSectionBtn}>
                                <Icon.Add size={3} className={styles.addIcon} />
                                Add new section
                            </Button>
                        </div>
                    </div>
                    <div className={styles.content}>
                        <div className={styles.tab}>
                            <div className={styles.folderIcon}>
                                <NbsIcon name={'folder'} />
                            </div>
                            <p className={styles.tabName}>{tab.name}</p>
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
                                        {tab.sections?.map((section, k) => {
                                            return (
                                                <ManageSectionTile
                                                    section={section}
                                                    index={k}
                                                    key={k}
                                                    setSelectedForEdit={setSelectedForEdit}
                                                    setSelectedForDelete={setSelectedForDelete}
                                                    selectedForDelete={selectedForDelete}
                                                    handleDelete={handleDelete}
                                                    reset={reset}
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
                        <Button onClick={onCancel} type={'button'} outline>
                            Close
                        </Button>
                    </div>
                </div>
            )}
        </>
    );
};
