import { ReactNode, SetStateAction, useRef, useState } from 'react';
import { Button, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { PagesTab } from 'apps/page-builder/generated';
import { addTab, deleteTab, updateTab } from 'apps/page-builder/services/tabsAPI';
import ManageTabs from 'apps/page-builder/pages/EditPage/ManageTabs/ManageTabs';
import { AddEditTab, TabEntry } from 'apps/page-builder/components/AddEditTab/AddEditTab';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import './EditPageTabs.scss';
import classNames from 'classnames';

type AlertMessage = { type: 'warning' | 'success'; message: string | ReactNode; expiration: number };

type ViewTabs = { type: 'view' };
type AddTab = { type: 'add'; entry: TabEntry };
type EditTab = { type: 'edit'; identifier: number; entry: TabEntry };
type DeleteTab = { type: 'delete'; tab: PagesTab };

type Actions = ViewTabs | AddTab | EditTab | DeleteTab;

type Props = {
    page: number;
    tabs: PagesTab[];
    active: number;
    setActive: SetStateAction<any>;
    onAddSuccess: () => void;
};

export const EditPageTabs = ({ page, tabs, active, setActive, onAddSuccess }: Props) => {
    const modalRef = useRef<ModalRef>(null);

    const [action, setAction] = useState<Actions>({ type: 'view' });

    const [message, setMessage] = useState<AlertMessage | undefined>(undefined);

    const selectForAdd = () => {
        setAction({ type: 'add', entry: { name: '', visible: true, order: tabs.length } });
    };

    const selectForEdit = (tab: PagesTab) => {
        setAction({ type: 'edit', identifier: tab.id, entry: tab });
    };

    const selectForDelete = (tab?: PagesTab) => {
        if (tab) {
            setAction({ type: 'delete', tab });
        } else {
            setAction({ type: 'view' });
        }
    };

    const handleChanged = (changed: TabEntry) => {
        setAction((current) => {
            if (action.type === 'add' || action.type === 'edit') {
                return { ...current, entry: changed };
            }

            return current;
        });
    };

    const handleAdd = () => {
        if (action.type === 'add') {
            addTab(page, {
                name: action.entry.name,
                visible: action.entry.visible
            })
                .catch((e) => {
                    console.error(e);
                })
                .then(() => {
                    setMessage({
                        type: 'success',
                        expiration: 3000,
                        message: (
                            <p>
                                You've successfully added <span>{action.entry.name}!</span>
                            </p>
                        )
                    });
                    resetEditPageTabs();
                    onAddSuccess();
                });
        }
    };

    const handleUpdate = () => {
        if (action.type === 'edit') {
            updateTab(
                page,
                {
                    name: action.entry.name,
                    visible: action.entry.visible
                },
                action.identifier
            )
                .catch((e) => {
                    console.error(e);
                })
                .then(() => {
                    setMessage({
                        type: 'success',
                        expiration: 3000,
                        message: (
                            <p>
                                You've successfully edited <span>{action.entry.name}!</span>
                            </p>
                        )
                    });
                    resetEditPageTabs();
                    onAddSuccess();
                });
        }
    };

    const handleDelete = (tab: PagesTab) => {
        deleteTab(Number(page), tab.id!)
            .then(() => {
                setMessage({
                    type: 'success',
                    expiration: 3000,
                    message: (
                        <p>
                            Successfuly deleted <span>{tab?.name}!</span>
                        </p>
                    )
                });
                resetEditPageTabs();
                onAddSuccess();
            })
            .then(() => setTimeout(() => resetEditPageTabs(), 3000));
    };

    const resetEditPageTabs = () => setAction({ type: 'view' });

    return (
        <>
            <div className="edit-page-tabs" data-testid="edit-page-tabs">
                {tabs.map((tab, i) => {
                    return (
                        <div
                            key={i}
                            className={classNames('edit-page-tabs__tab', { active: active === i })}
                            onClick={() => setActive(i)}>
                            <h4>{tab.name}</h4>
                        </div>
                    );
                })}
                <ModalToggleButton unstyled type="button" modalRef={modalRef} data-testid="openManageTabs">
                    <div className="edit-page-tabs__tab add">
                        <Icon.Edit />
                        <h4>Manage tabs</h4>
                    </div>
                </ModalToggleButton>
            </div>
            <ModalComponent
                size={'tall'}
                modalRef={modalRef}
                modalHeading={
                    <>
                        {action.type === 'add' ||
                            (action.type === 'edit' && (
                                <div className="manage-tabs-header">
                                    <h3>Manage tabs</h3>
                                    <Button type="button" onClick={selectForAdd}>
                                        <Icon.Add className="margin-right-05em add-tab-icon" />
                                        <span>Add new tab</span>
                                    </Button>
                                </div>
                            ))}
                        {action.type === 'view' || (action.type === 'delete' && 'Manage tabs')}
                    </>
                }
                modalBody={
                    <div className="edit-page-tabs__modal--body">
                        {message && (
                            <AlertBanner type={message.type} expiration={message.expiration}>
                                {message.message}
                            </AlertBanner>
                        )}
                        {action.type === 'delete' && (
                            <AlertBanner type="warning">
                                <p>
                                    Are you sure you want to delete this tab? All sections, subsections, and questions
                                    within this tab will also be deleted and cannot be undone.
                                </p>
                            </AlertBanner>
                        )}
                        {action.type === 'add' && <AddEditTab tabData={action.entry} onChanged={handleChanged} />}
                        {action.type === 'edit' && <AddEditTab tabData={action.entry} onChanged={handleChanged} />}
                        {action.type === 'view' && tabs.length > 0 && (
                            <>
                                <p>No manageable tabs to show</p>
                                <p>Add a new tab using the button above</p>
                            </>
                        )}
                        {(action.type === 'view' || action.type === 'delete') && tabs.length === 0 && (
                            <ManageTabs
                                setSelectedEditTab={selectForEdit}
                                selectedForDelete={action.type === 'delete' ? action.tab : undefined}
                                setSelectedForDelete={selectForDelete}
                                setDeleteTab={handleDelete}
                                reset={resetEditPageTabs}
                            />
                        )}
                    </div>
                }
                modalFooter={
                    <>
                        {action.type === 'view' && (
                            <ModalToggleButton modalRef={modalRef} onClick={() => resetEditPageTabs()} closer>
                                Close
                            </ModalToggleButton>
                        )}
                        {action.type === 'add' && (
                            <div className="margin-bottom-1em add-tab-modal ds-u-text-align--right ">
                                <Button className="submit-btn" onClick={handleAdd} type="button">
                                    Add tab
                                </Button>
                                <Button type="button" outline onClick={() => resetEditPageTabs()}>
                                    Cancel
                                </Button>
                            </div>
                        )}
                        {action.type === 'edit' && (
                            <>
                                <Button type="button" onClick={handleUpdate}>
                                    Save
                                </Button>
                                <Button type="button" outline onClick={() => resetEditPageTabs()}>
                                    Cancel
                                </Button>
                            </>
                        )}
                    </>
                }
            />
        </>
    );
};
