import { Grid, ModalRef } from '@trussworks/react-uswds';
import { RefObject } from 'react';

import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { PatientRace } from './PatientRace';
import { internalizeDate } from 'date';
import { maybeDescription, maybeDescriptions } from 'pages/patient/profile/coded';

type Props = {
    modal: RefObject<ModalRef>;
    data: PatientRace | undefined;
};

const noData = <span className="no-data">No data</span>;

const renderDetailed = (data: PatientRace) => {
    const details = maybeDescriptions(data.detailed).join(' | ');

    return details.length > 0 ? <>{details}</> : noData;
};

export const RaceDetailModal = ({ modal, data }: Props) => {
    return (
        <ModalComponent
            modalRef={modal}
            modalHeading="View details - Race"
            modalBody={
                (data && (
                    <div className="modal-body">
                        <Grid row>
                            <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                                <Grid row>
                                    <Grid col={6}>As of:</Grid>
                                    <Grid col={6}>{internalizeDate(data.asOf)}</Grid>
                                </Grid>
                            </Grid>
                            <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                                <Grid row>
                                    <Grid col={6}>Race:</Grid>
                                    <Grid col={6}>{maybeDescription(data.category)}</Grid>
                                </Grid>
                            </Grid>
                            <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                                <Grid row>
                                    <Grid col={6}>Detailed race:</Grid>
                                    <Grid col={6}>{renderDetailed(data)}</Grid>
                                </Grid>
                            </Grid>
                        </Grid>
                    </div>
                )) ||
                noData
            }
        />
    );
};
