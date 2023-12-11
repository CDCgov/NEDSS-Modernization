import { PagesTab } from 'apps/page-builder/generated';
import './ManageTabs.scss';
import { Icon } from '@trussworks/react-uswds';
import { Icon as IconComponent } from 'components/Icon/Icon';

type Props = {
    tabs: PagesTab[];
    setSelectedEditTab: (tab: PagesTab, index: number) => void;
    setDeleteTab: (tab: PagesTab) => void;
    selectedForDelete: PagesTab | undefined;
    setSelectedForDelete: (tab: PagesTab | undefined) => void;
    reset: () => void;
};

const ManageTabs = ({
    tabs,
    setSelectedEditTab,
    setDeleteTab,
    selectedForDelete,
    setSelectedForDelete,
    reset
}: Props) => {
    return (
        <div className="manage-tabs">
            {tabs.map((tab, i) => {
                return (
                    <div
                        key={i}
                        className={`manage-tabs__tile ${tab.id === selectedForDelete?.id ? 'delete' : ''}`}
                        style={{ listStyle: 'none' }}>
                        <div className="manage-tabs__handle">
                            <IconComponent name="drag" />
                            <p>Delete this tab?</p>
                        </div>
                        <div className="manage-tabs__label">
                            <IconComponent name="folder" />
                            {tab.name} {`(${tab.sections?.length ?? 0})`}
                        </div>
                        <div className={`manage-tabs__buttons ${tab.sections?.length !== 0 ? 'locked' : ''}`}>
                            <Icon.Edit
                                onClick={() => {
                                    reset();
                                    setSelectedEditTab(tab, i);
                                }}
                                size={3}
                            />
                            <Icon.Delete
                                onClick={() => {
                                    tab.sections?.length === 0 ? (reset(), setSelectedForDelete(tab)) : null;
                                }}
                                size={3}
                            />
                            <div className="delete">
                                <p onClick={() => setSelectedForDelete(undefined)}>Cancel</p>
                                <p onClick={() => setDeleteTab(selectedForDelete!)}>Delete</p>
                            </div>
                        </div>
                    </div>
                );
            })}
        </div>
    );
};

export default ManageTabs;
