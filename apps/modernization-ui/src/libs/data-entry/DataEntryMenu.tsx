import { NavEntry, SideNavigation } from 'design-system/side-nav';
import { Permitted } from 'libs/permission';

type DataEntryMenuProps = {
    className?: string;
};

const DataEntryMenu = ({ className }: DataEntryMenuProps) => {
    return (
        <SideNavigation title="Data entry" className={className}>
            <Permitted permission="find-patient">
                <NavEntry name="New patient" path="/search/patient" active />
            </Permitted>
            <Permitted permission="manage-organization">
                <NavEntry name="New organization" href="/nbs/MyTaskList1.do?ContextAction=GlobalOrganization" />
            </Permitted>
            <Permitted permission="manage-provider">
                <NavEntry name="New provider" href="/nbs/MyTaskList1.do?ContextAction=GlobalProvider" />
            </Permitted>
            <Permitted permission="add-observationmorbidityreport">
                <NavEntry name="Morbidity report" href="/nbs/MyTaskList1.do?ContextAction=AddMorbDataEntry" />
            </Permitted>
        </SideNavigation>
    );
};

export { DataEntryMenu };
