import { SetStateAction, useRef } from 'react';
import './EditPageTabs.scss';
import { Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { ModalComponent } from '../../../../components/ModalComponent/ModalComponent';
import { AddTab } from '../AddTabs';

type Props = {
    tabs?: { name: string }[];
    active: number;
    setActive: SetStateAction<any>;
};

export const EditPageTabs = ({ tabs, active, setActive }: Props) => {
    const modalRef = useRef<ModalRef>(null);
    const renderAddTab = (
        <>
            <ModalToggleButton className="" unstyled type="button" modalRef={modalRef}>
                <div className="edit-page-tabs__tab add">
                    <Icon.Edit />
                    <h4>Manage Tabs</h4>
                </div>
            </ModalToggleButton>
            <ModalComponent modalRef={modalRef} modalHeading={'Manage Tabs'} modalBody={<AddTab />} />
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
