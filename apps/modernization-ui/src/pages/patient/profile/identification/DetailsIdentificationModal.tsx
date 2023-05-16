import { Grid } from '@trussworks/react-uswds';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { format } from 'date-fns';

export const DetailsIdentificationModal = ({ modalRef, data }: any) => {
    return (
        <ModalComponent
            modalRef={modalRef}
            modalHeading="View details - Identification"
            modalBody={
                <div className="modal-body">
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
                                <Grid col={6}>{'No data'}</Grid>
                            </Grid>
                        </Grid>
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Grid row>
                                <Grid col={6}>Authority:</Grid>
                                <Grid col={6}>{data?.authority?.description || 'No data'}</Grid>
                            </Grid>
                        </Grid>
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Grid row>
                                <Grid col={6}>Value:</Grid>
                                <Grid col={6}>{data?.value || 'No data'}</Grid>
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
