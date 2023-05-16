import { Grid } from '@trussworks/react-uswds';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';

export const DetailsPhoneEmailModal = ({ modalRef, data }: any) => {
    data = data ? JSON.parse(data) : undefined;

    return (
        <ModalComponent
            modalRef={modalRef}
            modalHeading="View details - Phone & Email"
            modalBody={
                <div className="modal-body">
                    <Grid row>
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Grid row>
                                <Grid col={6}>As of:</Grid>
                                <Grid col={6}>No data</Grid>
                            </Grid>
                        </Grid>
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Grid row>
                                <Grid col={6}>Type:</Grid>
                                <Grid col={6}>No data</Grid>
                            </Grid>
                        </Grid>
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Grid row>
                                <Grid col={6}>Phone number:</Grid>
                                <Grid col={6}>{data?.locator?.phoneNbrTxt || 'No data'}</Grid>
                            </Grid>
                        </Grid>
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Grid row>
                                <Grid col={6}>Email address:</Grid>
                                <Grid col={6}>{data?.locator?.emailAddress || 'No data'}</Grid>
                            </Grid>
                        </Grid>
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Grid row>
                                <Grid col={6}>Additional comments:</Grid>
                                <Grid col={6}>No data</Grid>
                            </Grid>
                        </Grid>
                    </Grid>
                </div>
            }
        />
    );
};
