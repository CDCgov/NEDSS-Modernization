import { useRef, useState } from 'react';
import {
    ButtonGroup,
    Grid,
    Icon,
    Modal,
    ModalFooter,
    ModalHeading,
    ModalRef,
    ModalToggleButton
} from '@trussworks/react-uswds';
import { NamesTable } from 'pages/patient/profile/names';
import { AddressesTable } from 'pages/patient/profile/addresses';
import { PhoneAndEmailTable } from 'pages/patient/profile/phoneEmail';
import { IdentificationsTable } from 'pages/patient/profile/identification';
import { RacesTable } from 'pages/patient/profile/race';
import { AdministrativeTable } from 'pages/patient/profile/administrative';
import { GeneralPatient } from 'pages/patient/profile/generalInfo';
import { Mortality } from 'pages/patient/profile/moratlity';
import { Ethnicity } from 'pages/patient/profile/ethnicity';
import { SexBirth } from 'pages/patient/profile/sexBirth';

type DemographicProps = {
    handleFormSubmission?: (type: 'error' | 'success' | 'warning' | 'info', message: string, data: any) => void;
    id: string;
};

export const Demographics = ({ id }: DemographicProps) => {
    const deleteModalRef = useRef<ModalRef>(null);

    const [isDeleteModal] = useState<any>(undefined);

    return (
        <>
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <AdministrativeTable patient={id} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <NamesTable patient={id} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <AddressesTable patient={id} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <PhoneAndEmailTable patient={id} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <IdentificationsTable patient={id} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <RacesTable patient={id} />
            </div>

            <Grid row gap className="margin-auto">
                <Grid col={6}>
                    <Grid row>
                        <GeneralPatient patient={id} />
                        <Mortality patient={id} />
                    </Grid>
                </Grid>
                <Grid col={6}>
                    <Grid row>
                        <Ethnicity patient={id} />
                        <SexBirth patient={id} />
                    </Grid>
                </Grid>
            </Grid>

            {isDeleteModal && (
                <Modal
                    ref={deleteModalRef}
                    id="example-modal-1"
                    aria-labelledby="modal-1-heading"
                    className="padding-0"
                    aria-describedby="modal-1-description">
                    <ModalHeading
                        id="modal-1-heading"
                        className="border-bottom border-base-lighter font-sans-lg padding-2">
                        Delete name
                    </ModalHeading>
                    <div className="margin-2 grid-row flex-no-wrap border-left-1 border-accent-warm flex-align-center">
                        <Icon.Warning className="font-sans-2xl margin-x-2" />
                        <p id="modal-1-description">
                            Are you sure you want to delete Name record, Smith, Johnathan Test?
                        </p>
                    </div>
                    <ModalFooter className="border-top border-base-lighter padding-2 margin-left-auto">
                        <ButtonGroup>
                            <ModalToggleButton outline modalRef={deleteModalRef} closer>
                                Cancel
                            </ModalToggleButton>
                            <ModalToggleButton modalRef={deleteModalRef} closer className="padding-105 text-center">
                                Yes, delete
                            </ModalToggleButton>
                        </ButtonGroup>
                    </ModalFooter>
                </Modal>
            )}
        </>
    );
};
