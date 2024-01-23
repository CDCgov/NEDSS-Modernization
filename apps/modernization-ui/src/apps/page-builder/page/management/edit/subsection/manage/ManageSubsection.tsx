import { useState } from 'react';
import styles from './managesubsection.module.scss';
import { Button, Icon } from '@trussworks/react-uswds';
import { Heading } from 'components/heading';
import { AlertInLineProps } from '../../section/manage/ManageSectionModal';
import { Icon as NbsIcon } from 'components/Icon/Icon';
import { PagesSection } from 'apps/page-builder/generated';

type ManageSubsectionProps = {
    alert?: AlertInLineProps;
    onResetAlert?: () => void;
    section: PagesSection;
    onCancel?: () => void;
    onSetAlert?: (message: string, type: 'success' | 'error' | 'warning' | 'info') => void;
};

export const ManageSubsection = ({ alert, onResetAlert, section, onSetAlert }: ManageSubsectionProps) => {
    const [subsectionState, setSubsectionState] = useState<'manage' | 'add' | 'edit'>('manage');

    const [onAction, setOnAction] = useState<boolean>(false);

    const handleUpdateState = (state: 'manage' | 'add' | 'edit') => {
        setSubsectionState(state);
    };

    return (
        <>
            {subsectionState === 'manage' && (
                <div className={styles.manageSubsection}>
                    <div className={styles.header}>
                        <div className={styles.manageSubsectionHeader} data-testid="header">
                            <Heading level={4}>Manage sections</Heading>
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
                            <p className={styles.tabName}>{section?.name}</p>
                        </div>
                    </div>
                </div>
            )}
        </>
    );
};
