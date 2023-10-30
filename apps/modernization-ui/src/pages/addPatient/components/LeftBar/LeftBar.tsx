import { Grid } from '@trussworks/react-uswds';
import { MenuItem } from './MenuItem';

export enum ACTIVE_TAB {
    PATIENT = 'patient',
    ORGANIZATION = 'organization',
    PROVIDER = 'provider',
    MORBIDITY = 'morbidity'
}

export const LeftBar = ({ activeTab }: any) => {
    return (
        <Grid row className="left-bar">
            <Grid col={12} className="font-sans-lg text-bold padding-3">
                Data entry
            </Grid>
            <div className="border-base-light grid-row flex-no-wrap border-top padding-y-1 margin-x-3 cursor-pointer width-full">
                {activeTab === ACTIVE_TAB.PATIENT && <span className="line"></span>}
                <h6 className="active text-normal margin-0 font-sans-md width-full padding-y-1 padding-left-2">
                    New patient
                </h6>
            </div>
            <MenuItem name={`New ${ACTIVE_TAB.ORGANIZATION}`} link={`nbs/OrgSearchResults1.do?ContextAction=Add`} />
            <MenuItem name={`New ${ACTIVE_TAB.MORBIDITY}`} link={`nbs/MyTaskList1.do?ContextAction=AddMorbDataEntry`} />
            <MenuItem name={`New ${ACTIVE_TAB.PROVIDER}`} link={`nbs/ProvSearchResults1.do?ContextAction=Add`} />
        </Grid>
    );
};
