import { NavEntry, SideNav } from 'design-system/side-nav/SideNav';

export const AddPatientExtendedSideNav = () => {
    const entries: NavEntry[] = [
        { name: 'New patient' },
        { name: 'New organization', href: '/nbs/MyTaskList1.do?ContextAction=GlobalOrganization', external: true },
        { name: 'New provider', href: '/nbs/MyTaskList1.do?ContextAction=GlobalProvider', external: true },
        { name: 'Morbidity report', href: '/nbs/MyTaskList1.do?ContextAction=AddMorbDataEntry', external: true }
    ];
    return <SideNav title="Data entry" entries={entries} />;
};
