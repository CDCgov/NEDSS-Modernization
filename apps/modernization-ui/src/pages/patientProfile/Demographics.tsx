import { useEffect, useRef, useState } from 'react';
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
import { HorizontalTable } from '../../components/Table/HorizontalTable';
import { Deceased, FindPatientByIdQuery } from '../../generated/graphql/schema';
import { format } from 'date-fns';
import { NamesTable } from 'pages/patient/profile/names';
import { AddressesTable } from 'pages/patient/profile/addresses';
import { PhoneAndEmailTable } from 'pages/patient/profile/phoneEmail';
import { IdentificationsTable } from 'pages/patient/profile/identification';
import { RacesTable } from 'pages/patient/profile/race';
import { AdministrativeTable } from 'pages/patient/profile/administrative';

type DemographicProps = {
    patientProfileData: FindPatientByIdQuery['findPatientById'] | undefined;
    handleFormSubmission?: (type: 'error' | 'success' | 'warning' | 'info', message: string, data: any) => void;
    ethnicity?: string;
    race?: any;
    id: string;
};

export const Demographics = ({ patientProfileData, ethnicity, id }: DemographicProps) => {
    const [generalTableData, setGeneralTableData] = useState<any>(undefined);
    const [morbidityTableData, setMorbidityTableData] = useState<any>(undefined);
    const [ethnicityTableData, setEthnicityTableData] = useState<any>(undefined);
    const [sexAndBirthData, setSexAndBirthData] = useState<any>(undefined);

    const deleteModalRef = useRef<ModalRef>(null);

    useEffect(() => {
        if (patientProfileData) {
            setGeneralTableData([
                { title: 'As of:', text: format(new Date(patientProfileData.asOfDateGeneral), 'MM/dd/yyyy') },
                { title: 'Marital status:', text: patientProfileData.maritalStatusCd },
                { title: 'Mother’s maiden name:', text: patientProfileData.mothersMaidenNm },
                { title: 'Number of adults in residence:', text: patientProfileData.adultsInHouseNbr },
                { title: 'Number of children in residence:', text: patientProfileData.childrenInHouseNbr },
                { title: 'Primary occupation:', text: patientProfileData.occupationCd },
                { title: 'Highest level of education:', text: patientProfileData.educationLevelCd },
                { title: 'Primary language:', text: patientProfileData.primLangCd },
                { title: 'Speaks english:', text: patientProfileData.speaksEnglishCd },
                { title: 'State HIV case ID:', text: patientProfileData.eharsId }
            ]);
            setMorbidityTableData([
                { title: 'As of:', text: format(new Date(patientProfileData.asOfDateMorbidity), 'MM/dd/yyyy') },
                {
                    title: 'Is the patient deceased:',
                    text:
                        patientProfileData.deceasedIndCd === Deceased.N
                            ? 'No'
                            : patientProfileData.deceasedIndCd === Deceased.Y
                            ? 'Yes'
                            : 'Unknown'
                },
                { title: 'Date of death:', text: patientProfileData.deceasedTime },
                { title: 'City of death:', text: '' },
                { title: 'State of death:', text: '' },
                { title: 'County of death:', text: '' },
                { title: 'Country of death:', text: '' }
            ]);
            setEthnicityTableData([
                { title: 'As of:', text: format(new Date(patientProfileData.asOfDateEthnicity), 'MM/dd/yyyy') },
                { title: 'Ethnicity::', text: ethnicity },
                { title: 'Spanish origin:', text: '' },
                { title: 'Reasons unknown:', text: '' }
            ]);
            setSexAndBirthData([
                { title: 'As of:', text: format(new Date(patientProfileData.asOfDateSex), 'MM/dd/yyyy') },
                { title: 'Date of death:', text: patientProfileData.deceasedTime },
                { title: 'Current age:', text: patientProfileData.ageReported },
                { title: 'Current sex:', text: patientProfileData.currSexCd },
                { title: 'Unknown reason:', text: patientProfileData.sexUnkReasonCd },
                { title: 'Transgender information:', text: patientProfileData.preferredGenderCd },
                { title: 'Additional gender:', text: patientProfileData.additionalGenderCd },
                { title: 'Birth sex:', text: patientProfileData.birthGenderCd },
                { title: 'Multiple birth:', text: patientProfileData.multipleBirthInd },
                { title: 'Birth order:', text: patientProfileData.birthOrderNbr },
                { title: 'Birth city:', text: patientProfileData.birthCityCd },
                { title: 'Birth state:', text: patientProfileData.birthStateCd },
                { title: 'Birth county:', text: '' },
                { title: 'Birth country:', text: '' }
            ]);
        }
    }, [patientProfileData]);

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
                        <Grid col={12} className="margin-top-3 margin-bottom-2">
                            <HorizontalTable
                                type="general"
                                tableHeader="General Patient Information"
                                tableData={generalTableData}
                            />
                        </Grid>

                        <Grid col={12} className="margin-top-3 margin-bottom-2">
                            <HorizontalTable type="mortality" tableHeader="Mortality" tableData={morbidityTableData} />
                        </Grid>
                    </Grid>
                </Grid>
                <Grid col={6}>
                    <Grid row>
                        <Grid col={12} className="margin-top-3 margin-bottom-2">
                            <HorizontalTable type="ethnicity" tableHeader="Ethnicity" tableData={ethnicityTableData} />
                        </Grid>

                        <Grid col={12} className="margin-top-3 margin-bottom-2">
                            <HorizontalTable type="sex" tableHeader="Sex & Birth" tableData={sexAndBirthData} />
                        </Grid>
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
