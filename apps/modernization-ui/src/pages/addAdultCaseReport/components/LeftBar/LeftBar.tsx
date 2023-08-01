import { Grid, Button } from '@trussworks/react-uswds';

export enum ACTIVE_TAB {
    FORM_INFO = 'Form Info',
    IDENTIFICATION = 'Identification',
    DEMOGRAPHICS = 'Demographics',
    FACILITY = 'Facility',
    LAB_DATA = 'Lab Data',
    CLINICAL = 'Clinical',
    TREATMENT = 'Treatment',
    TESTING_HISTORY = 'Testing History',
    FOLLOWUP = 'Follow-up Investigation',
    LOCAL_FIELDS = 'Local Fields',
    DUPLICATE_REVIEW = 'Duplicate Review',
    RETIRED = 'Retired',
    COMMENTS = 'Comments'
}
export const LeftBar = ({ activeTab }: any) => {
    return (
        <Grid row className="left-bar">
            <Grid col={12} className="font-sans-lg text-bold padding-3">
                Feed
                <Button type="button" style={{float: 'right'}} className="usa-button--outline">
                Add note
                </Button>
            </Grid>
            <div className="border-base-light grid-row flex-no-wrap border-top padding-y-1 margin-x-3 cursor-pointer width-full">
                {activeTab === ACTIVE_TAB.FORM_INFO && <span className="line"></span>}
                <h6
                    className={`${
                        activeTab === ACTIVE_TAB.FORM_INFO && 'active'
                    } text-normal margin-0 font-sans-md padding-bottom-1 width-full padding-y-1 padding-left-2`}>
                    Notes
                </h6>
            </div>
            <div className="border-base-light grid-row flex-no-wrap border-top padding-y-1 margin-x-3 cursor-pointer width-full">
                {activeTab === ACTIVE_TAB.IDENTIFICATION && <span className="line"></span>}
                <h6
                    className={`${
                        activeTab === ACTIVE_TAB.IDENTIFICATION && 'active'
                    } text-normal margin-0 font-sans-md padding-bottom-1 width-full padding-y-1 padding-left-2`}>
                    History
                </h6>
            </div>
            {/* To be added new in future a list of other pages, for now its commented out as its not needed until designers have built out designs for these pages */}
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
