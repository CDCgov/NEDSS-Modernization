import { SetStateAction, useRef, useState, useContext, useEffect } from 'react';
import './EditPageTabs.scss';
import { Button, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { ModalComponent } from '../../../../../components/ModalComponent/ModalComponent';
import { AddEditTab } from '../../../components/AddEditTab/AddEditTab';
import ManageTabs from '../ManageTabs/ManageTabs';
import { PagesTab, Tab } from 'apps/page-builder/generated';
import { useParams } from 'react-router-dom';
import { UserContext } from 'user';
import { addTab, updateTab } from 'apps/page-builder/services/tabsAPI';
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
    const [selectedEditTab, setSelectedEditTab] = useState<PagesTab | undefined>(undefined);
    const [selectedTabIndex, setSelectedIndex] = useState<number | undefined>(undefined);
    const modalRef = useRef<ModalRef>(null);
    const { state } = useContext(UserContext);
    const { pageId } = useParams();
    const token = `Bearer ${state.getToken()}`;
    const [tabDetails, setTabDetails] = useState({ name: '', visible: true });

    useEffect(() => {
        if (addSuccess || editSuccess) {
            setTimeout(() => {
                resetEditPageTabs();
            }, 5000);
        }
    }, [addSuccess, editSuccess]);

    const handleEditTab = (tab: PagesTab, index: number) => {
        setSelectedEditTab(tab);
        setSelectedIndex(index);
        setIsEditing(true);
    };

    const handleAddTab = async () => {
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

    const resetEditPageTabs = () => {
        setSelectedEditTab(undefined);
        setAddSuccess(false);
        setEditSuccess(false);
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
                            <AlertBanner type="success">You've successfully Added a new tab!</AlertBanner>
                        ) : editSuccess ? (
                            <AlertBanner type="success" onClose={() => resetEditPageTabs()}>
                                You've successfully saved your changes to <b>&nbsp;{tabDetails?.name}!</b>
                            </AlertBanner>
                        ) : null}
                        {isAdding ? (
                            <AddEditTab setTabDetails={setTabDetails} />
                        ) : isEditing ? (
                            <AddEditTab tabData={tabs[selectedTabIndex!]} setTabDetails={setTabDetails} />
                        ) : (
                            <ManageTabs tabs={tabs} setSelectedEditTab={handleEditTab} />
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
                        <ModalToggleButton modalRef={modalRef} closer outline>
                            Close
                        </ModalToggleButton>
                    )
                }
            />
        </>
    );
};
