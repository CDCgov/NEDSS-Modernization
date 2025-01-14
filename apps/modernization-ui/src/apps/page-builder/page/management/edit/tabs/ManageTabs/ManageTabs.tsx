import { Button, Icon, Modal, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { PagesTab, Tab } from 'apps/page-builder/generated';
import { AddEditTab } from 'apps/page-builder/page/management/edit/tabs/AddEditTab/AddEditTab';
import { addTab, updateTab } from 'apps/page-builder/services/tabsAPI';
import { ReactNode, useEffect, useRef, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import './ManageTabModal.scss';
import { ManageTabsHeader } from './header/ManageTabsHeader';
import { ReorderableTabs } from '../ReorderableTabs/ReorderableTabs';
import styles from './manageTabs.module.scss';

type Props = {
    pageId: number;
    tabs: PagesTab[];
    onAddSuccess: () => void;
};

export const ManageTabs = ({ pageId, onAddSuccess, tabs }: Props) => {
    const modalRef = useRef<ModalRef>(null);
    const [message, setMessage] = useState<AlertMessage | undefined>(undefined);
    const [addEdit, setAddEdit] = useState(false);
    const [selectedForEdit, setSelectedForEdit] = useState<PagesTab | undefined>(undefined);

    const form = useForm<Tab>({
        mode: 'onBlur',
        defaultValues: {
            name: selectedForEdit ? selectedForEdit.name : undefined,
            visible: selectedForEdit ? selectedForEdit.visible : true
        }
    });

    type AlertMessage = {
        type: 'warning' | 'success' | 'error';
        message: string | ReactNode;
        expiration: number | undefined;
    };

    useEffect(() => {
        if (selectedForEdit) {
            setAddEdit(true);
            form.setValue('name', selectedForEdit.name);
            form.setValue('visible', selectedForEdit.visible);
        }
    }, [selectedForEdit]);

    useEffect(() => {
        setTimeout(() => setMessage(undefined), 5000);
    }, [message]);

    const handleAdd = form.handleSubmit((data) => {
        addTab(pageId, { name: data.name!, visible: data.visible ?? true })
            .catch((e) => {
                console.error(e);
            })
            .then(() => {
                setMessage({
                    type: 'success',
                    expiration: 3000,
                    message: (
                        <p>
                            You've successfully added <span>{data?.name}!</span>
                        </p>
                    )
                });
            })
            .then(() => {
                onAddSuccess();
                resetEditPageTabs();
            });
    });

    const handleSave = form.handleSubmit((data) => {
        if (selectedForEdit) {
            updateTab(pageId, { name: data.name ?? '', visible: data.visible ?? true }, selectedForEdit.id)
                .catch((e) => {
                    console.error(e);
                })
                .then(() => {
                    setMessage({
                        type: 'success',
                        expiration: 3000,
                        message: (
                            <p>
                                You've successfully edited <span>{data?.name}!</span>
                            </p>
                        )
                    });
                })
                .then(() => {
                    onAddSuccess();
                })
                .then(() => resetEditPageTabs());
        }
    });

    const addNew = () => {
        setAddEdit(true);
        form.setValue('name', undefined);
        form.setValue('visible', undefined);
    };

    const resetEditPageTabs = () => {
        setAddEdit(false);
        setSelectedForEdit(undefined);
        form.clearErrors();
        form.reset();
    };

    const handleDeleteError = () => {
        setMessage({
            type: 'error',
            expiration: 3000,
            message: (
                <p>
                    This tab cannot be deleted because there are other elements inside it. Please remove or delete all
                    child elements by managing the content at the subsection/section/question level.
                </p>
            )
        });
    };

    const handleTabChanged = (message: string) => {
        onAddSuccess();
        resetEditPageTabs();
        setMessage({
            type: 'success',
            expiration: 3000,
            message: <p>{message}</p>
        });
    };

    return (
        <FormProvider {...form}>
            <ModalToggleButton
                className={styles.manageButton}
                unstyled
                type="button"
                modalRef={modalRef}
                data-testid="openManageTabs">
                <Icon.Edit />
                <h2>Manage tabs</h2>
            </ModalToggleButton>
            <Modal id={'manage-tab-modal'} ref={modalRef} className={'manage-tab-modal'} isLarge forceAction>
                <div className={styles.manageTabModal}>
                    <ManageTabsHeader showAddTab={!addEdit} onAddNew={addNew} />
                    <div className={styles.modalBody}>
                        {message && (
                            <AlertBanner
                                type={message.type}
                                expiration={message.expiration}
                                onClose={() => setMessage(undefined)}>
                                {message.message}
                            </AlertBanner>
                        )}
                        {addEdit && <AddEditTab />}
                        {!addEdit && tabs.length === 0 ? (
                            <>
                                <p>No manageable tabs to show</p>
                                <p>Add a new tab using the button above</p>
                            </>
                        ) : null}
                        {!addEdit && tabs.length !== 0 && (
                            <ReorderableTabs
                                page={pageId}
                                tabs={tabs}
                                onEdit={(e) => setSelectedForEdit(e)}
                                onTabChanged={handleTabChanged}
                                onDeleteError={handleDeleteError}
                            />
                        )}
                    </div>
                    <div className={styles.buttonBar}>
                        {!addEdit ? (
                            <ModalToggleButton modalRef={modalRef} onClick={() => resetEditPageTabs()} closer outline>
                                Close
                            </ModalToggleButton>
                        ) : null}
                        {addEdit && !selectedForEdit ? (
                            <>
                                <Button type="button" outline onClick={() => resetEditPageTabs()}>
                                    Cancel
                                </Button>
                                <Button onClick={handleAdd} type="button" disabled={!form.formState.isValid}>
                                    Add tab
                                </Button>
                            </>
                        ) : null}
                        {addEdit && selectedForEdit ? (
                            <>
                                <Button type="button" outline onClick={() => resetEditPageTabs()}>
                                    Cancel
                                </Button>
                                <Button type="button" onClick={handleSave}>
                                    Update
                                </Button>
                            </>
                        ) : null}
                    </div>
                </div>
            </Modal>
        </FormProvider>
    );
};
