import { Grid } from '@trussworks/react-uswds';
import { Config } from 'config';

export enum ACTIVE_TAB {
    PATIENT = 'patient',
    ORGANIZATION = 'organization',
    PROVIDER = 'provider',
    MORBIDITY = 'morbidity'
}

const NBS_URL = Config.nbsUrl;

export const LeftBar = ({ activeTab }: any) => {
    return (
        <Grid row className="left-bar">
            <Grid col={12} className="font-sans-lg text-bold padding-3">
                Data entry
            </Grid>
            <div className="border-base-light grid-row flex-no-wrap border-top padding-y-1 margin-x-3 cursor-pointer width-full">
                {activeTab === ACTIVE_TAB.PATIENT && <span className="line"></span>}
                <h6
                    className={`${
                        activeTab === ACTIVE_TAB.PATIENT && 'active'
                    } text-normal margin-0 font-sans-md padding-bottom-1 width-full padding-y-1 padding-left-2`}>
                    New patient
                </h6>
            </div>
            <div className="border-base-light grid-row flex-no-wrap border-top padding-y-1 margin-x-3 cursor-pointer width-full">
                <a href={`${NBS_URL}/OrgSearchResults1.do?ContextAction=Add`} className="text-black">
                    <h6
                        className={`${
                            activeTab === ACTIVE_TAB.ORGANIZATION && 'active'
                        } text-normal margin-0 font-sans-md padding-bottom-1 width-full padding-y-1 padding-left-2`}>
                        New {ACTIVE_TAB.ORGANIZATION}
                    </h6>
                </a>
            </div>
            <div className="border-base-light grid-row flex-no-wrap border-top padding-y-1 margin-x-3 cursor-pointer width-full">
                <a href={`${NBS_URL}/ProvSearchResults1.do?ContextAction=Add`} className="text-black">
                    <h6
                        className={`${
                            activeTab === ACTIVE_TAB.PROVIDER && 'active'
                        } text-normal margin-0 font-sans-md padding-bottom-1 width-full padding-y-1 padding-left-2`}>
                        New {ACTIVE_TAB.PROVIDER}
                    </h6>
                </a>
            </div>
            <div className="border-base-light grid-row flex-no-wrap border-top padding-y-1 margin-x-3 cursor-pointer width-full">
                <a href={`${NBS_URL}/MyTaskList1.do?ContextAction=AddMorbDataEntry`} className="text-black">
                    <h6
                        className={`${
                            activeTab === ACTIVE_TAB.MORBIDITY && 'active'
                        } text-normal margin-0 font-sans-md padding-bottom-1 width-full padding-y-1 padding-left-2`}>
                        New {ACTIVE_TAB.MORBIDITY}
                    </h6>
                </a>
            </div>
        </Grid>
    );
};
