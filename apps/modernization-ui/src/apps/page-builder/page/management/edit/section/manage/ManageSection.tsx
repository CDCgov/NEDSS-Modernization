import { Button, Icon } from '@trussworks/react-uswds';
import { PagesSection, PagesTab, SectionControllerService } from 'apps/page-builder/generated';
import { Icon as NbsIcon } from 'components/Icon/Icon';
import styles from './managesection.module.scss';
import { useState } from 'react';
import { AddSection } from './AddSection';
import { Heading } from 'components/heading';
import { authorization } from 'authorization';
import { AlertInLineProps } from './ManageSectionModal';
import React from 'react';

type ManageSectionProps = {
    tab?: PagesTab;
    pageId: number;
    onCancel?: () => void;
    onContentChange?: () => void;
    alert?: AlertInLineProps;
    onDeleteSection?: () => void;
    onResetAlert?: () => void;
};

export const ManageSection = ({
    onCancel,
    tab,
    onContentChange,
    pageId,
    alert,
    onDeleteSection,
    onResetAlert
}: ManageSectionProps) => {
    const [sectionState, setSectionState] = useState<'manage' | 'add'>('manage');

    const [confirmDelete, setConfirmDelete] = useState<number | undefined>(undefined);

    const [onAction, setOnAction] = useState<boolean>(false);

    const handleUpdateState = (state: 'manage' | 'add') => {
        setSectionState(state);
    };

    const onDelete = (section: PagesSection) => {
        SectionControllerService.deleteSectionUsingDelete({
            authorization: authorization(),
            page: pageId,
            sectionId: section.id
        }).then(() => {
            onContentChange?.();
            onDeleteSection?.();
        });
    };

    const deleteHeader = (section: PagesSection) => {
        if (section.subSections.length !== 0) {
            return `Section cannot be deleted. This section contains elements (subsections and questions) inside it. Remove the contents first, and then the section can be deleted.`;
        } else {
            return `Are you sure you want to delete this section?`;
        }
    };

    const isValidDelete = (section: PagesSection) => {
        if (section.subSections.length !== 0) {
            return false;
        } else {
            return true;
        }
    };

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
                    tabId={tab?.id}
                />
            )}
            {sectionState === 'manage' && (
                <div className={styles.managesection}>
                    <div className={styles.header}>
                        <div className={styles.manageSectionHeader}>
                            <Heading level={4}>Manage sections</Heading>
                        </div>
                        <div className={styles.addSectionHeader}>
                            <Button
                                type="button"
                                onClick={() => {
                                    handleUpdateState('add');
                                }}
                                className={styles.addSectionBtn}
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
                        {tab?.sections?.map((section, k) => {
                            return (
                                <React.Fragment key={k}>
                                    {confirmDelete !== undefined && confirmDelete === section.id ? (
                                        <div className={styles.warningModal}>
                                            <div className={styles.warningModalHeader}>
                                                <div className={styles.warningIcon}>
                                                    <Icon.Warning size={3} />
                                                </div>
                                                {deleteHeader?.(section)}
                                            </div>
                                            <div className={styles.warningModalContent}>
                                                <div className={styles.content}>
                                                    <div className={styles.warningDrag}>
                                                        <NbsIcon name={'drag'} />
                                                    </div>
                                                    <div className={styles.warningGroup}>
                                                        <NbsIcon name={'group'} />
                                                    </div>
                                                    <div
                                                        className={
                                                            styles.warningSectionName
                                                        }>{`${section.name}(${section.subSections.length})`}</div>
                                                </div>
                                                <div className={styles.warningModalBtns}>
                                                    {isValidDelete(section) ? (
                                                        <>
                                                            <div
                                                                onClick={() => {
                                                                    onDelete?.(section);
                                                                    setConfirmDelete(undefined);
                                                                    setOnAction(false);
                                                                }}>
                                                                Yes, delete
                                                            </div>
                                                            <div className={styles.separator}>|</div>
                                                            <div
                                                                onClick={() => {
                                                                    setConfirmDelete(undefined);
                                                                    setOnAction(false);
                                                                }}>
                                                                Cancel
                                                            </div>
                                                        </>
                                                    ) : (
                                                        <div
                                                            onClick={() => {
                                                                setConfirmDelete(undefined);
                                                                setOnAction(false);
                                                            }}>
                                                            OK
                                                        </div>
                                                    )}
                                                </div>
                                            </div>
                                        </div>
                                    ) : (
                                        <div className={styles.section}>
                                            <div className={styles.icons}>
                                                <div className={styles.drag}>
                                                    <NbsIcon name={'drag'} />
                                                </div>
                                                <div className={styles.group}>
                                                    <NbsIcon name={'group'} />
                                                </div>
                                            </div>
                                            <p
                                                className={
                                                    styles.sectionName
                                                }>{`${section.name}(${section.subSections.length})`}</p>
                                            <div className={styles.sectionIcons}>
                                                <div className={styles.edit}>
                                                    <Button
                                                        type="button"
                                                        onClick={() => {
                                                            console.log('edit here');
                                                        }}
                                                        outline
                                                        className={styles.iconBtn}
                                                        disabled={onAction}>
                                                        <Icon.Edit style={{ cursor: 'pointer' }} size={3} />
                                                    </Button>
                                                </div>
                                                <div className={styles.delete}>
                                                    <Button
                                                        type="button"
                                                        className={styles.iconBtn}
                                                        outline
                                                        disabled={onAction}
                                                        onClick={() => {
                                                            setConfirmDelete(section.id);
                                                            setOnAction(true);
                                                        }}>
                                                        <Icon.Delete style={{ cursor: 'pointer' }} size={3} />
                                                    </Button>
                                                </div>
                                            </div>
                                        </div>
                                    )}
                                </React.Fragment>
                            );
                        })}
                    </div>
                    <div className={styles.footer}>
                        <Button
                            onClick={() => {
                                onCancel?.();
                                setConfirmDelete(undefined);
                            }}
                            type={'button'}
                            outline>
                            Close
                        </Button>
                    </div>
                </div>
            )}
        </>
    );
};
