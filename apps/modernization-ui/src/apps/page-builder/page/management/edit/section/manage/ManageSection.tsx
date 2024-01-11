import { Button, Icon } from '@trussworks/react-uswds';
import { PagesTab } from 'apps/page-builder/generated';
import { Icon as NbsIcon } from 'components/Icon/Icon';
import styles from './managesection.module.scss';

type ManageSectionProps = {
    tab: PagesTab;
    onCloseModal?: () => void;
    refresh?: () => void;
};

export const ManageSection = ({ onCloseModal, tab }: ManageSectionProps) => {
    return (
        <div className={styles.managesection}>
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
                            <div className={styles.subsection} key={k}>
                                <div className={styles.drag}>
                                    <NbsIcon name={'drag'} />
                                </div>
                                <div className={styles.group}>
                                    <NbsIcon name={'group'} />
                                </div>
                                <p>{`${section.name}(${section.subSections.length})`}</p>
                                <div className={styles.subsectionIcons}>
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
            <div className={styles.footer}>
                <Button onClick={onCloseModal} type={'button'} outline>
                    Close
                </Button>
            </div>
        </div>
    );
};
