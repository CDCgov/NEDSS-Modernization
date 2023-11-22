import { SetStateAction, useRef, useState, useContext } from 'react';
import './EditPageTabs.scss';
import { Button, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { ModalComponent } from '../../../../../components/ModalComponent/ModalComponent';
import { AddEditTab } from '../../../components/AddEditTab/AddEditTab';
import ManageTabs from '../ManageTabs/ManageTabs';
import { PagesTab, Tab } from 'apps/page-builder/generated';
import { useParams } from 'react-router-dom';
import { UserContext } from 'user';
import { addTab, deleteTab, updateTab } from 'apps/page-builder/services/tabsAPI';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';

type Props = {
    tabs: Tab[];
    active: number;
    setActive: SetStateAction<any>;
    onAddSuccess: () => void;
};

export const EditPageTabs = ({ tabs, active, setActive, onAddSuccess }: Props) => {
    const [isAdding, setIsAdding] = useState(false);
    const [isEditing, setIsEditing] = useState(false);
    const [addSuccess, setAddSuccess] = useState(false);
    const [editSuccess, setEditSuccess] = useState(false);
    const [deleteSuccess, setDeleteSuccess] = useState(false);
    const [selectedEditTab, setSelectedEditTab] = useState<PagesTab | undefined>(undefined);
    const [selectedTabIndex, setSelectedIndex] = useState<number | undefined>(undefined);
    const [selectedForDelete, setSelectedForDelete] = useState<PagesTab | undefined>(undefined);
    const modalRef = useRef<ModalRef>(null);
    const { state } = useContext(UserContext);
    const { pageId } = useParams();
    const token = `Bearer ${state.getToken()}`;
    const [tabDetails, setTabDetails] = useState({ name: '', visible: true });

    const handleEditTab = (tab: PagesTab, index: number) => {
        setSelectedEditTab(tab);
        setSelectedIndex(index);
        setIsEditing(true);
    };

    const handleAddTab = async () => {
        setSelectedEditTab(tabDetails);
        if (pageId) {
            addTab(token, parseInt(pageId), {
                name: tabDetails.name,
                visible: tabDetails.visible
            })
                .then(() => {
                    setAddSuccess(true);
                    setIsAdding(false);
                    onAddSuccess();
                })
                .catch((e) => {
                    console.error(e);
                });
        }
    };

    const handleUpdateTab = async () => {
        if (pageId) {
            updateTab(
                token,
                parseInt(pageId),
                {
                    name: tabDetails.name,
                    visible: tabDetails.visible
                },
                selectedEditTab!.id!
            )
                .then(() => {
                    setEditSuccess(true);
                    setIsEditing(false);
                    onAddSuccess();
                })
                .catch((e) => {
                    console.error(e);
                });
        }
    };

    const handleDeleteTab = async (tab: PagesTab) => {
        setSelectedForDelete(undefined);
        setSelectedEditTab(tab);
        if (pageId) {
            deleteTab(token, parseInt(pageId), tab.id!)
                .then(() => {
                    setDeleteSuccess(true);
                    setIsEditing(false);
                    onAddSuccess();
                })
                .then(() => setTimeout(() => resetEditPageTabs(), 3000));
        }
    };

    const resetEditPageTabs = () => {
        setSelectedEditTab(undefined);
        setSelectedForDelete(undefined);
        setAddSuccess(false);
        setEditSuccess(false);
        setDeleteSuccess(false);
    };

    return (
        <>
            <div className="edit-page-tabs" data-testid="edit-page-tabs">
                {tabs &&
                    tabs.map((tab, i) => {
                        return (
                            tab.visible && (
                                <div
                                    key={i}
                                    className={`edit-page-tabs__tab ${active === i ? 'active' : ''}`}
                                    onClick={() => setActive(i)}>
                                    <h4>{tab.name}</h4>
                                </div>
                            )
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
                    !isAdding ? (
                        <div className="manage-tabs-header">
                            <div>Manage tabs</div>
                            <Button className="" type="button" onClick={() => setIsAdding(true)}>
                                <Icon.Add className="margin-right-05em add-tab-icon" />
                                <span>Add new tab</span>
                            </Button>
                        </div>
                    ) : (
                        'Manage tabs'
                    )
                }
                modalBody={
                    <div className="edit-page-tabs__modal--body">
                        {addSuccess ? (
                            <AlertBanner type="success" expiration={3000}>
                                <p>
                                    You've successfully added <span>{tabDetails.name}</span>
                                </p>
                            </AlertBanner>
                        ) : null}
                        {editSuccess ? (
                            <AlertBanner type="success" onClose={() => resetEditPageTabs()} expiration={3000}>
                                <p>
                                    Successfully edited <span>{selectedEditTab?.name}</span>
                                </p>
                            </AlertBanner>
                        ) : null}
                        {deleteSuccess ? (
                            <AlertBanner type="success" onClose={() => resetEditPageTabs()} expiration={3000}>
                                <p>
                                    Successfuly deleted <span>{selectedEditTab?.name}</span>
                                </p>
                            </AlertBanner>
                        ) : null}
                        {selectedForDelete ? (
                            <AlertBanner type="warning">
                                Are you sure you want to delete this tab? All sections, subsections, and questions
                                within this tab will also be deleted and cannot be undone.
                            </AlertBanner>
                        ) : null}
                        {isAdding ? (
                            <AddEditTab setTabDetails={setTabDetails} />
                        ) : isEditing ? (
                            <AddEditTab tabData={tabs[selectedTabIndex!]} setTabDetails={setTabDetails} />
                        ) : (
                            <ManageTabs
                                tabs={tabs}
                                setSelectedEditTab={handleEditTab}
                                selectedForDelete={selectedForDelete}
                                setSelectedForDelete={setSelectedForDelete}
                                setDeleteTab={handleDeleteTab}
                                reset={resetEditPageTabs}
                            />
                        )}
                    </div>
                }
                modalFooter={
                    isAdding ? (
                        <div className="margin-bottom-1em add-tab-modal ds-u-text-align--right ">
                            <Button className="submit-btn" onClick={handleAddTab} type="button">
                                Add tab
                            </Button>
                            <ModalToggleButton modalRef={modalRef} outline onClick={() => setIsAdding(false)} closer>
                                Cancel
                            </ModalToggleButton>
                        </div>
                    ) : isEditing ? (
                        <>
                            <Button type="button" onClick={() => handleUpdateTab()}>
                                Save
                            </Button>
                            <ModalToggleButton modalRef={modalRef} outline onClick={() => setIsEditing(false)} closer>
                                Cancel
                            </ModalToggleButton>
                        </>
                    ) : (
                        <ModalToggleButton modalRef={modalRef} onClick={() => resetEditPageTabs()} closer>
                            Close
                        </ModalToggleButton>
                    )
                }
            />
        </>
    );
};
