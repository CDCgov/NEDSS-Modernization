import { useRef, useState, useEffect, ReactNode } from 'react';
import { Button, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { AddEditTab } from 'apps/page-builder/page/management/edit/tabs/AddEditTab/AddEditTab';
import { addTab } from 'apps/page-builder/services/tabsAPI';
import { PagesTab } from 'apps/page-builder/generated';
import { Heading } from 'components/heading';
import styles from './manageTabs.module.scss';

type Props = {
    pageId: number;
    tabs: PagesTab[];
    onAddSuccess?: () => void;
};

export const ManageTabs = ({ pageId, tabs, onAddSuccess }: Props) => {
    const modalRef = useRef<ModalRef>(null);
    const [message, setMessage] = useState<AlertMessage | undefined>(undefined);
    const [addEdit, setAddEdit] = useState(false);
    const [selectedForEdit, setSelectedForEdit] = useState<PagesTab | undefined>(undefined);
    const [selectedForDelete, setSelectedForDelete] = useState(undefined);
    const [newTab, setNewTab] = useState<TabEntry | undefined>(undefined);

    type AlertMessage = { type: 'warning' | 'success'; message: string | ReactNode; expiration: number };

    type TabEntry = { name: string | undefined; visible: boolean; order: number };

    useEffect(() => {
        if (selectedForEdit) {
            setMessage({
                type: 'success',
                expiration: 3000,
                message: (
                    <p>
                        You've successfully added <span>{selectedForEdit.name}!</span>
                    </p>
                )
            });
        }
    }, [selectedForEdit, selectedForDelete]);

    useEffect(() => {
        setTimeout(() => setMessage(undefined), 3000);
    }, [message]);

    const handleAdd = async () => {
        if (newTab && onAddSuccess) {
            await addTab(pageId, { name: newTab.name!, visible: newTab.visible })
                .catch((e) => {
                    console.error(e);
                })
                .then(() => {
                    onAddSuccess();
                })
                .then(() => {
                    setMessage({
                        type: 'success',
                        expiration: 3000,
                        message: (
                            <p>
                                You've successfully added <span>{newTab?.name}!</span>
                            </p>
                        )
                    });
                })
                .then(() => resetEditPageTabs());
        }
    };

    const resetEditPageTabs = () => {
        setAddEdit(false);
        setSelectedForEdit(undefined);
        setSelectedForDelete(undefined);
        setNewTab(undefined);
    };

    return (
        <div className={styles.manage}>
            <ModalToggleButton unstyled type="button" modalRef={modalRef} data-testid="openManageTabs">
                <Icon.Edit />
                <h4>Manage tabs</h4>
            </ModalToggleButton>
            <ModalComponent
                size={'tall'}
                modalRef={modalRef}
                modalHeading={
                    <div className={styles.modalHeader}>
                        <Heading level={3}>Manage tabs</Heading>
                        <Button type="button" onClick={() => setAddEdit(true)}>
                            <Icon.Add className="margin-right-05em add-tab-icon" />
                            <span>Add new tab</span>
                        </Button>
                    </div>
                }
                modalBody={
                    <div className={styles.modalBody}>
                        {message && (
                            <AlertBanner type={message.type} expiration={message.expiration}>
                                {message.message}
                            </AlertBanner>
                        )}
                        {addEdit ? <AddEditTab tabData={null} onChanged={setNewTab} /> : null}
                        {!addEdit && tabs.length === 0 ? (
                            <>
                                <p>No manageable tabs to show</p>
                                <p>Add a new tab using the button above</p>
                            </>
                        ) : null}
                        {!addEdit && tabs.length !== 0 ? (
                            <ul>
                                {tabs.map((tab, key) => (
                                    <li key={key}>{tab.name}</li>
                                ))}
                            </ul>
                        ) : null}
                    </div>
                }
                modalFooter={
                    <>
                        {!addEdit ? (
                            <ModalToggleButton modalRef={modalRef} onClick={() => resetEditPageTabs()} closer outline>
                                Close
                            </ModalToggleButton>
                        ) : null}
                        {addEdit && !selectedForEdit ? (
                            <div className="margin-bottom-1em add-tab-modal ds-u-text-align--right ">
                                <Button
                                    type="button"
                                    outline
                                    onClick={() => {
                                        setAddEdit(false);
                                        resetEditPageTabs();
                                    }}>
                                    Cancel
                                </Button>
                                <Button onClick={handleAdd} type="button" disabled={!newTab?.name}>
                                    Add tab
                                </Button>
                            </div>
                        ) : null}
                        {addEdit && selectedForEdit ? (
                            <>
                                <ModalToggleButton
                                    modalRef={modalRef}
                                    type="button"
                                    outline
                                    onClick={() => resetEditPageTabs()}
                                    closer>
                                    Cancel
                                </ModalToggleButton>
                                <Button type="button" onClick={() => console.log('save edits to tab')}>
                                    Save
                                </Button>
                            </>
                        ) : null}
                    </>
                }
            />
        </div>
    );
};
