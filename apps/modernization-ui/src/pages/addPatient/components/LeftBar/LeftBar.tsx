import { Grid } from '@trussworks/react-uswds';

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
                <h6
                    className={`${
                        activeTab === ACTIVE_TAB.PATIENT && 'active'
                    } text-normal margin-0 font-sans-md padding-bottom-1 width-full padding-y-1 padding-left-2`}>
                    New patient
                </h6>
            </div>
            {/* <div className="border-base-light grid-row flex-no-wrap border-top padding-y-1 margin-x-3 cursor-pointer width-full">
                <h6
                    className={`${
                        activeTab === ACTIVE_TAB.ORGANIZATION && 'active'
                    } text-normal margin-0 font-sans-md padding-bottom-1 width-full padding-y-1 padding-left-2`}>
                    New {ACTIVE_TAB.ORGANIZATION}
                </h6>
            </div> */}
        </Grid>
    );
};
