import { NavEntry, SideNavigation } from 'design-system/side-nav/SideNavigation';

export const AddPatientSideNav = () => {
    return (
        <SideNavigation title="Data entry">
            <NavEntry name="New patient" active />
            <NavEntry name="New organization" href="/nbs/MyTaskList1.do?ContextAction=GlobalOrganization" external />
            <NavEntry name="New provider" href="/nbs/MyTaskList1.do?ContextAction=GlobalProvider" external />
            <NavEntry name="Morbidity report" href="/nbs/MyTaskList1.do?ContextAction=AddMorbDataEntry" external />
        </SideNavigation>
    );
};
