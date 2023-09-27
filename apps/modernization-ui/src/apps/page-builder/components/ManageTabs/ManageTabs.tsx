// import { Button, Icon } from '@trussworks/react-uswds';
import { PageTab } from 'apps/page-builder/generated';
import './ManageTabs.scss';
import { Icon } from '@trussworks/react-uswds';

type Props = {
    tabs: PageTab[];
};

const ManageTabs = ({ tabs }: Props) => {
    return (
        <div className="form-container margin-top-1em margin-right-1em">
            <ul>
                {tabs.map((tab, i) => {
                    return (
                        <li key={i} className="margin-bottom-1em" style={{ listStyle: 'none' }}>
                            <div
                                className="tab-name"
                                style={{
                                    display: 'flex',
                                    flexDirection: 'row',
                                    justifyContent: 'space-between',
                                    color: '#005EA2'
                                }}>
                                <div>{tab.name}</div>
                                <div>
                                    <Icon.Edit />
                                    <Icon.Delete />
                                </div>
                            </div>
                        </li>
                    );
                })}
            </ul>
        </div>
    );
};

export default ManageTabs;
