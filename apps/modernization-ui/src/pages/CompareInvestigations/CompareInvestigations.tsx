import { Button, Grid, Icon } from '@trussworks/react-uswds';
import { NoData } from 'components/NoData';

export const CompareInvestigations = () => {
    return (
        <div className="height-full main-banner">
            <div className="bg-white grid-row flex-align-center flex-justify border-bottom-style">
                <h1 className="font-sans-xl text-medium">Compare investigations</h1>
                <div>
                    <Button className="display-inline-flex print-btn" type={'submit'}>
                        <Icon.AddCircle className="margin-right-05" />
                        Merge
                    </Button>
                    <Button className="display-inline-flex print-btn" type={'submit'}>
                        <Icon.Print className="margin-right-05" />
                        Print
                    </Button>
                    <Button className="delete-btn display-inline-flex" type={'submit'}>
                        Delete Patient
                    </Button>
                </div>
            </div>
            <div className="main-body">
                <Grid row gap={3}>
                    <Grid col={6}>
                        <div className="bg-white common-card">
                            <Grid
                                row
                                className="border-bottom border-base-lighter padding-x-2 font-sans-sm flex-align-center">
                                <Grid col={6}>
                                    <h2 className="font-sans-lg text-medium">Smith, John</h2>
                                </Grid>
                                <Grid col={6} className="text-right">
                                    <Button className="display-inline-flex print-btn" type={'submit'}>
                                        Select as surviving record
                                    </Button>
                                </Grid>
                            </Grid>
                            <Grid row className="padding-y-2 padding-x-2 font-sans-sm">
                                <Grid col={6}>Patient ID:</Grid>
                                <Grid col={6}>12345</Grid>
                            </Grid>
                            <Grid row className="border-top padding-y-2 padding-x-2 font-sans-sm">
                                <Grid col={6}>Investigation ID:</Grid>
                                <Grid col={6}>CAS10016003GA01</Grid>
                            </Grid>
                            <Grid row className="border-top padding-y-2 padding-x-2 font-sans-sm">
                                <Grid col={6}>Investigation status:</Grid>
                                <Grid col={6}>Open</Grid>
                            </Grid>
                            <Grid row className="border-top padding-y-2 padding-x-2 font-sans-sm">
                                <Grid col={6}>Investigatior:</Grid>
                                <Grid col={6}>
                                    <NoData />
                                </Grid>
                            </Grid>
                            <Grid row className="border-top padding-y-2 padding-x-2 font-sans-sm">
                                <Grid col={6}>Created:</Grid>
                                <Grid col={6}>11/07/22</Grid>
                            </Grid>
                            <Grid row className="border-top padding-y-2 padding-x-2 font-sans-sm">
                                <Grid col={6}>Last updated:</Grid>
                                <Grid col={6}>
                                    <NoData />
                                </Grid>
                            </Grid>
                            <Grid row className="border-top padding-y-2 padding-x-2 font-sans-sm">
                                <Grid col={6}>By:</Grid>
                                <Grid col={6}>Paul Blart</Grid>
                            </Grid>
                            <Grid row className="border-top padding-y-2 padding-x-2 font-sans-sm">
                                <Grid col={6}>Notificatoin status::</Grid>
                                <Grid col={6}>
                                    <NoData />
                                </Grid>
                            </Grid>
                        </div>
                    </Grid>
                    <Grid col={6}>
                        <div className="bg-white common-card">
                            <Grid
                                row
                                className="border-bottom border-base-lighter padding-x-2 font-sans-sm flex-align-center">
                                <Grid col={6}>
                                    <h2 className="font-sans-lg text-medium">Smith, John</h2>
                                </Grid>
                                <Grid col={6} className="text-right">
                                    <Button className="display-inline-flex print-btn" type={'submit'}>
                                        Select as surviving record
                                    </Button>
                                </Grid>
                            </Grid>
                            <Grid row className="padding-y-2 padding-x-2 font-sans-sm">
                                <Grid col={6}>Patient ID:</Grid>
                                <Grid col={6}>12345</Grid>
                            </Grid>
                            <Grid row className="border-top padding-y-2 padding-x-2 font-sans-sm">
                                <Grid col={6}>Investigation ID:</Grid>
                                <Grid col={6}>CAS10016003GA01</Grid>
                            </Grid>
                            <Grid row className="border-top padding-y-2 padding-x-2 font-sans-sm">
                                <Grid col={6}>Investigation status:</Grid>
                                <Grid col={6}>Open</Grid>
                            </Grid>
                            <Grid row className="border-top padding-y-2 padding-x-2 font-sans-sm">
                                <Grid col={6}>Investigatior:</Grid>
                                <Grid col={6}>
                                    <NoData />
                                </Grid>
                            </Grid>
                            <Grid row className="border-top padding-y-2 padding-x-2 font-sans-sm">
                                <Grid col={6}>Created:</Grid>
                                <Grid col={6}>11/07/22</Grid>
                            </Grid>
                            <Grid row className="border-top padding-y-2 padding-x-2 font-sans-sm">
                                <Grid col={6}>Last updated:</Grid>
                                <Grid col={6}>
                                    <NoData />
                                </Grid>
                            </Grid>
                            <Grid row className="border-top padding-y-2 padding-x-2 font-sans-sm">
                                <Grid col={6}>By:</Grid>
                                <Grid col={6}>Paul Blart</Grid>
                            </Grid>
                            <Grid row className="border-top padding-y-2 padding-x-2 font-sans-sm">
                                <Grid col={6}>Notificatoin status::</Grid>
                                <Grid col={6}>
                                    <NoData />
                                </Grid>
                            </Grid>
                        </div>
                    </Grid>
                </Grid>
            </div>
        </div>
    );
};
