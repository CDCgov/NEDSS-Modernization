import { SetStateAction } from 'react';
import './EditPageTabs.scss';
import { Icon } from '@trussworks/react-uswds';

type Props = {
    tabs?: { name: string; id: number }[];
    active?: number | undefined;
    setActive: SetStateAction<any>;
};

export const EditPageTabs = ({ tabs, active, setActive }: Props) => {
    return (
        <div className="edit-page-tabs">
            {tabs &&
                tabs.map((tab, i) => {
                    return (
                        <div
                            key={i}
                            className={`edit-page-tabs__tab ${active === tab.id ? 'active' : ''}`}
                            onClick={() => setActive(tab.id)}>
                            <h4>{tab.name}</h4>
                        </div>
                    );
                })}
            <div className="edit-page-tabs__tab add">
                <Icon.Add></Icon.Add>
                <h4>Add new tab</h4>
            </div>
        </div>
    );
};
