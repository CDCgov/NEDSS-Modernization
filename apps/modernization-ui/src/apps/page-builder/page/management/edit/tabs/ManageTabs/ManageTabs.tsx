import { useRef, useState, useEffect, ReactNode } from 'react';
import { Button, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { AddEditTab } from 'apps/page-builder/page/management/edit/tabs/AddEditTab/AddEditTab';
import { addTab, updateTab, deleteTab } from 'apps/page-builder/services/tabsAPI';
import { PagesTab, Tab } from 'apps/page-builder/generated';
import { Heading } from 'components/heading';
import styles from './manageTabs.module.scss';
import { useDragDrop } from 'apps/page-builder/context/DragDropProvider';
import { DragDropContext, Droppable } from 'react-beautiful-dnd';
import { ManageTabsTile } from '../ManageTabsTile/ManageTabsTile';
import { FormProvider, useForm } from 'react-hook-form';

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
    const [selectedForDelete, setSelectedForDelete] = useState<PagesTab | undefined>(undefined);

    const form = useForm<Tab>({
        mode: 'onBlur',
        defaultValues: {
            name: selectedForEdit ? selectedForEdit.name : undefined,
            visible: selectedForEdit ? selectedForEdit.visible : true
        }
    });

    const { handleDragEnd, handleDragStart, handleDragUpdate } = useDragDrop();
    type AlertMessage = {
        type: 'warning' | 'success' | 'error';
        message: string | ReactNode;
        expiration: number | undefined;
    };

    useEffect(() => {
        if (selectedForEdit) {
            console.log('SET', selectedForEdit);
            setAddEdit(true);
            form.setValue('name', selectedForEdit.name);
            form.setValue('visible', selectedForEdit.visible);
        }
    }, [selectedForEdit, selectedForDelete]);

    useEffect(() => {
        setTimeout(() => setMessage(undefined), 5000);
    }, [message]);

    const handleAdd = form.handleSubmit((data) => {
        if (onAddSuccess) {
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
                })
                .then(() => resetEditPageTabs());
        }
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

    const handleDelete = () => {
        if (selectedForDelete && onAddSuccess) {
            if (selectedForDelete.sections.length > 0) {
                setMessage({
                    type: 'error',
                    expiration: 3000,
                    message: (
                        <p>
                            This tab cannot be deleted because there are other elements inside it. Please remove or
                            delete all child elements by managing the content at the subsection/section/question level.
                        </p>
                    )
                });
                setSelectedForDelete(undefined);
            } else {
                deleteTab(pageId, selectedForDelete.id)
                    .catch((e) => {
                        console.error(e);
                    })
                    .then(() => {
                        setMessage({
                            type: 'success',
                            expiration: 3000,
                            message: (
                                <p>
                                    You've successfully deleted <span>{selectedForDelete.name}!</span>
                                </p>
                            )
                        });
                    })
                    .then(() => {
                        onAddSuccess();
                    })
                    .then(() => resetEditPageTabs());
            }
        }
    };

    const addNew = () => {
        setAddEdit(true);
        form.setValue('name', undefined);
        form.setValue('visible', undefined);
    };

    const resetEditPageTabs = () => {
        setAddEdit(false);
        setSelectedForEdit(undefined);
        setSelectedForDelete(undefined);
        form.clearErrors();
        form.reset();
    };

    return (
        <FormProvider {...form}>
            <div className={styles.manage}>
                <ModalToggleButton unstyled type="button" modalRef={modalRef} data-testid="openManageTabs">
                    <Icon.Edit />
                    <Heading level={3}>Manage tabs</Heading>
                </ModalToggleButton>
                <ModalComponent
                    size={'tall'}
                    modalRef={modalRef}
                    modalHeading={
                        <>
                            Manage tabs
                            <Button type="button" onClick={() => addNew()}>
                                <Icon.Add className="margin-right-05em add-tab-icon" />
                                <span>Add new tab</span>
                            </Button>
                        </>
                    }
                    modalBody={
                        <div className={styles.modalBody}>
                            {message ? (
                                <AlertBanner
                                    type={message.type}
                                    expiration={message.expiration}
                                    onClose={() => setMessage(undefined)}>
                                    {message.message}
                                </AlertBanner>
                            ) : null}
                            {addEdit ? <AddEditTab /> : null}
                            {!addEdit && tabs.length === 0 ? (
                                <>
                                    <p>No manageable tabs to show</p>
                                    <p>Add a new tab using the button above</p>
                                </>
                            ) : null}
                            {!addEdit && tabs.length !== 0 ? (
                                <DragDropContext
                                    onDragEnd={handleDragEnd}
                                    onDragStart={handleDragStart}
                                    onDragUpdate={handleDragUpdate}>
                                    <Droppable droppableId="all-tabs" type="tab">
                                        {(provided, snapshot) => (
                                            <div
                                                {...provided.droppableProps}
                                                ref={provided.innerRef}
                                                style={{
                                                    backgroundColor: snapshot.isDraggingOver ? '#d9e8f6' : 'white'
                                                }}>
                                                {tabs.map((tab, i) => {
                                                    return (
                                                        <ManageTabsTile
                                                            key={tab.id!.toString()}
                                                            tab={tab}
                                                            index={i}
                                                            setSelectedForEdit={setSelectedForEdit}
                                                            setSelectedForDelete={setSelectedForDelete}
                                                            selectedForDelete={selectedForDelete}
                                                            deleteTab={handleDelete}
                                                            reset={resetEditPageTabs}
                                                        />
                                                    );
                                                })}
                                                {provided.placeholder}
                                            </div>
                                        )}
                                    </Droppable>
                                </DragDropContext>
                            ) : null}
                        </div>
                    }
                    modalFooter={
                        <>
                            {!addEdit ? (
                                <ModalToggleButton
                                    modalRef={modalRef}
                                    onClick={() => resetEditPageTabs()}
                                    closer
                                    outline>
                                    Close
                                </ModalToggleButton>
                            ) : null}
                            {addEdit && !selectedForEdit ? (
                                <div className="margin-bottom-1em add-tab-modal ds-u-text-align--right ">
                                    <Button type="button" outline onClick={() => resetEditPageTabs()}>
                                        Cancel
                                    </Button>
                                    <Button onClick={handleAdd} type="button" disabled={!form.getValues.name}>
                                        Add tab
                                    </Button>
                                </div>
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
                        </>
                    }
                />
            </div>
        </FormProvider>
    );
};
