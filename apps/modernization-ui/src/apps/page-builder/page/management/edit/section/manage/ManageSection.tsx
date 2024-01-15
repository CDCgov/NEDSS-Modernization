import { Button, Icon } from '@trussworks/react-uswds';
import { PagesTab } from 'apps/page-builder/generated';
import { Icon as NbsIcon } from 'components/Icon/Icon';
import styles from './managesection.module.scss';
import { useState } from 'react';
import { AddSection } from './AddSection';
import { Heading } from 'components/heading';

type ManageSectionProps = {
    tab: PagesTab;
    pageId: number;
    onCancel?: () => void;
    onContentChange?: () => void;
};

export const ManageSection = ({ onCancel, tab, onContentChange, pageId }: ManageSectionProps) => {
    const [sectionState, setSectionState] = useState<'manage' | 'add'>('manage');

    return (
        <>
            {sectionState === 'add' && (
                <AddSection
                    pageId={pageId}
                    onAddSectionCreated={() => {
                        onContentChange && onContentChange();
                        setSectionState('manage');
                    }}
                    onCancel={() => setSectionState('manage')}
                    tabId={tab.id}
                />
            )}

            {sectionState === 'manage' && tab !== undefined && (
                <>
                    <div className={styles.managesection}>
                        <div className={styles.header}>
                            <div className={styles.manageSectionHeader}>
                                <Heading level={4}>Manage sections</Heading>
                            </div>
                            <div className={styles.addSectionHeader}>
                                <Button
                                    type="button"
                                    onClick={() => setSectionState('add')}
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
                            <>
                                {tab.sections?.map((section, k) => {
                                    return (
                                        <>
                                            <div className={styles.section} key={k}>
                                                <div className={styles.drag}>
                                                    <NbsIcon name={'drag'} />
                                                </div>
                                                <div className={styles.group}>
                                                    <NbsIcon name={'group'} />
                                                </div>
                                                <p>{`${section.name}(${section.subSections.length})`}</p>
                                                <div className={styles.sectionIcons}>
                                                    <div className={styles.edit}>
                                                        <Icon.Edit
                                                            style={{ cursor: 'pointer' }}
                                                            size={3}
                                                            onClick={() => console.log('edit here')}
                                                        />
                                                    </div>
                                                    <div className={styles.delete}>
                                                        <Icon.Delete
                                                            style={{ cursor: 'pointer' }}
                                                            size={3}
                                                            onClick={() => console.log('delete here')}
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                        </>
                                    );
                                })}
                            </>
                        </div>
                        <div className={styles.footer}>
                            <Button onClick={onCancel} type={'button'} outline>
                                Close
                            </Button>
                        </div>
                    </div>
                </>
            )}
        </>
    );
};
