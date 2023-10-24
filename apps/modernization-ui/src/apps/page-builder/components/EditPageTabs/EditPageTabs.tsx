import { SetStateAction, useRef, useState } from 'react';
import './EditPageTabs.scss';
import { Button, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { ModalComponent } from '../../../../components/ModalComponent/ModalComponent';
import { AddTab } from '../AddTabs';
import ManageTabs from '../ManageTabs/ManageTabs';
import { Tab } from 'apps/page-builder/generated';

type Props = {
    tabs: Tab[];
    active: number;
    setActive: SetStateAction<any>;
    onAddSuccess: () => void;
};

export const EditPageTabs = ({ tabs, active, setActive, onAddSuccess }: Props) => {
    const [isAdding, setIsAdding] = useState(false);
    const modalRef = useRef<ModalRef>(null);

    const handleAddTab = () => {
        setIsAdding(!isAdding);
        onAddSuccess();
    };

    return (
        <>
            <div className="edit-page-tabs">
                {tabs &&
                    tabs.map((tab, i) => {
                        return (
                            <div
                                key={i}
                                className={`edit-page-tabs__tab ${active === i ? 'active' : ''}`}
                                onClick={() => setActive(i)}>
                                <h4>{tab.name}</h4>
                            </div>
                        );
                    })}
                <ModalToggleButton unstyled type="button" modalRef={modalRef} data-testid="openManageTabs">
                    <div className="edit-page-tabs__tab add">
                        <Icon.Edit />
                        <h4>Manage Tabs</h4>
                    </div>
                </ModalToggleButton>
            </div>
            <ModalComponent
                modalRef={modalRef}
                modalHeading={
                    !isAdding ? (
                        <div className="manage-tabs-header">
                            <div>Manage Tabs</div>
                            <Button className="" type="button" onClick={() => setIsAdding(true)}>
                                <Icon.Add className="margin-right-05em add-tab-icon" />
                                <span>Add new tab</span>
                            </Button>
                        </div>
                    ) : (
                        'Manage Tabs'
                    )
                }
                modalBody={
                    isAdding ? (
                        <AddTab onCancel={() => setIsAdding(false)} onAddTab={handleAddTab} />
                    ) : (
                        <ManageTabs tabs={tabs} />
                    )
                }
            />
        </>
    );
};
