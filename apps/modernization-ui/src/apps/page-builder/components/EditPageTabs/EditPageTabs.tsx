import { SetStateAction, useRef, useState } from 'react';
import './EditPageTabs.scss';
import { Button, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { ModalComponent } from '../../../../components/ModalComponent/ModalComponent';
import { AddTab } from '../AddTabs';
import ManageTabs from '../ManageTabs/ManageTabs';

type Props = {
    tabs?: { name: string }[];
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

    const renderAddTab = (
        <>
            <ModalToggleButton className="" unstyled type="button" modalRef={modalRef}>
                <div className="edit-page-tabs__tab add">
                    <Icon.Edit />
                    <h4>Manage Tabs</h4>
                </div>
            </ModalToggleButton>
            <ModalComponent
                modalRef={modalRef}
                modalHeading={
                    !isAdding ? (
                        <div style={{ display: 'flex', flexDirection: 'row', justifyContent: 'space-between' }}>
                            <span>Manage Tabs</span>
                            <Button className="add-tab-button" type="button" onClick={() => setIsAdding(true)}>
                                <Icon.Add className="margin-right-05em add-tab-icon" />
                                <span>Add new tab</span>
                            </Button>
                        </div>
                    ) : (
                        'Manage Tabs'
                    )
                }
                modalBody={
                    isAdding ? <AddTab onCancel={() => setIsAdding(false)} onAddTab={handleAddTab} /> : <ManageTabs />
                }
            />
        </>
    );

    return (
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
            {renderAddTab}
        </div>
    );
};
