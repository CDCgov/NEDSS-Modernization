import { Grid } from '@trussworks/react-uswds';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { format } from 'date-fns';

export const DetailsNameModal = ({ modalRef, data }: any) => {
    return (
        <ModalComponent
            modalRef={modalRef}
            modalHeading="View details - Name"
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
                                <Grid col={6}>{data?.use ? data?.use?.description : 'No data'}</Grid>
                            </Grid>
                        </Grid>
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Grid row>
                                <Grid col={6}>Prefix:</Grid>
                                <Grid col={6}>{data?.prefix?.description || 'No data'}</Grid>
                            </Grid>
                        </Grid>
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Grid row>
                                <Grid col={6}>Suffix:</Grid>
                                <Grid col={6}>{data?.suffix?.description || 'No data'}</Grid>
                            </Grid>
                        </Grid>
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Grid row>
                                <Grid col={6}>Degree:</Grid>
                                <Grid col={6}>{data?.degree?.description || 'No data'}</Grid>
                            </Grid>
                        </Grid>
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Grid row>
                                <Grid col={6}>First name:</Grid>
                                <Grid col={6}>{data?.first || 'No data'}</Grid>
                            </Grid>
                        </Grid>
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Grid row>
                                <Grid col={6}>Middle name:</Grid>
                                <Grid col={6}>{data?.middle || 'No data'}</Grid>
                            </Grid>
                        </Grid>
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Grid row>
                                <Grid col={6}>Last name:</Grid>
                                <Grid col={6}>{data?.last || 'No data'}</Grid>
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
