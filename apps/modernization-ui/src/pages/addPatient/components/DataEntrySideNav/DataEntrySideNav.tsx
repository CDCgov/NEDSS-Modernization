import { SideNavigation, NavigationEntry, LinkEntry } from 'components/SideNavigation/SideNavigation';
import './DataEntrySideNav.scss';

export const DataEntrySideNav = () => (
    <SideNavigation title="Data entry" className="data-entry-side-nav">
        <NavigationEntry path="/add-patient">New patient</NavigationEntry>
        <LinkEntry href="nbs/MyTaskList1.do?ContextAction=GlobalOrganization">Organization</LinkEntry>
        <LinkEntry href="nbs/MyTaskList1.do?ContextAction=AddMorbDataEntry">Morbidity</LinkEntry>
        <LinkEntry href="nbs/MyTaskList1.do?ContextAction=GlobalProvider">Provider</LinkEntry>
    </SideNavigation>
);
