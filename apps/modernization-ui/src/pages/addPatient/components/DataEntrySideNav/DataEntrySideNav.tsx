import { NavigationEntry } from 'components/SideNavigation/NavigationEntry';
import { SideNavigation } from 'components/SideNavigation/SideNavigation';
import './DataEntrySideNav.scss';

export const DataEntrySideNav = () => {
    return (
        <div className="data-entry-side-nav">
            <SideNavigation
                title="Data entry"
                active={1}
                entries={[
                    <NavigationEntry key={1} label="New patient" href="/add-patient" useNav />,
                    <NavigationEntry
                        key={2}
                        label="Organization"
                        href="nbs/MyTaskList1.do?ContextAction=GlobalOrganization"
                    />,
                    <NavigationEntry
                        key={3}
                        label="Morbidity"
                        href="nbs/MyTaskList1.do?ContextAction=AddMorbDataEntry"
                    />,
                    <NavigationEntry key={4} label="Provider" href="nbs/MyTaskList1.do?ContextAction=GlobalProvider" />
                ]}
            />
        </div>
    );
};
