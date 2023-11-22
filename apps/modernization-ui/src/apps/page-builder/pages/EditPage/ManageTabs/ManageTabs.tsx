import React from 'react';
import { PagesTab, Tab } from 'apps/page-builder/generated';
import './ManageTabs.scss';
import { Icon } from '@trussworks/react-uswds';
import { Icon as IconComponent } from 'components/Icon/Icon';

type Props = {
    tabs: Tab[];
    setSelectedEditTab: (tab: PagesTab, index: number) => void;
};

const ManageTabs = ({ tabs, setSelectedEditTab }: Props) => {
    return (
        <div className="manage-tabs">
            {tabs.map((tab, i) => {
                return (
                    <div key={i} className="manage-tabs__tile" style={{ listStyle: 'none' }}>
                        <div className="manage-tabs__label">
                            <IconComponent name="drag" />
                            <IconComponent name="folder" />
                            {tab.name}
                        </div>
                        <div className="manage-tabs__buttons">
                            <Icon.Edit onClick={() => setSelectedEditTab(tab, i)} size={3} />
                            <Icon.Delete size={3} />
                        </div>
                    </div>
                );
            })}
        </div>
    );
};

export default ManageTabs;
