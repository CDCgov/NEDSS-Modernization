import { Grid } from '@trussworks/react-uswds';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { format } from 'date-fns';

export const DetailsAddressModal = ({ modalRef, data, bodyRef }: any) => {
    console.log('data:', data);
    return (
        <ModalComponent
            modalRef={modalRef}
            modalHeading="View details - Address"
            modalBody={
                <div className="modal-body" ref={bodyRef}>
                    <Grid row>
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Grid row>
                                <Grid col={6}>As of:</Grid>
                                <Grid col={6}>
                                    {data?.asOf ? format(new Date(data?.asOf), 'MM/dd/yyyy') : 'No data'}
                                </Grid>
                            </Grid>
                        </Grid>
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Grid row>
                                <Grid col={6}>Type:</Grid>
                                <Grid col={6}>{data?.type?.description ? data?.type?.description : 'No data'}</Grid>
                            </Grid>
                        </Grid>
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Grid row>
                                <Grid col={6}>Address:</Grid>
                                <Grid col={6}>
                                    {data?.address1 + ' ' + (data?.locator?.address2 || '') || 'No data'}
                                </Grid>
                            </Grid>
                        </Grid>
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Grid row>
                                <Grid col={6}>City:</Grid>
                                <Grid col={6}>{data?.city || 'No data'}</Grid>
                            </Grid>
                        </Grid>
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Grid row>
                                <Grid col={6}>State:</Grid>
                                <Grid col={6}>{data?.state?.description || 'No data'}</Grid>
                            </Grid>
                        </Grid>
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Grid row>
                                <Grid col={6}>Zip:</Grid>
                                <Grid col={6}>{data?.zipcode}</Grid>
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
