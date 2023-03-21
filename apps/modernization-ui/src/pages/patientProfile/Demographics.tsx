import { useContext, useEffect, useRef, useState } from 'react';
import { TableComponent } from '../../components/Table/Table';
import {
    Button,
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
import { AddCommentModal } from './components/AddCommentModal';
import { AddNameModal } from './components/AddNameModal';
import { AddPhoneEmailModal } from './components/AddPhoneEmailModal';
import { AddAddressModal } from './components/AddressModal';
import { Deceased, FindPatientByIdQuery, FindPatientsByFilterQuery } from '../../generated/graphql/schema';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import { format } from 'date-fns';
import { DetailsNameModal } from './components/DetailsNameModal';

type DemographicProps = {
    patientProfileData: FindPatientByIdQuery['findPatientById'] | undefined;
    handleFormSubmission?: (type: 'error' | 'success' | 'warning' | 'info', message: string) => void;
    ethnicity?: string;
};

export const Demographics = ({ patientProfileData, handleFormSubmission, ethnicity }: DemographicProps) => {
    const { searchCriteria } = useContext(SearchCriteriaContext);

    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const [tableBody, setTableBody] = useState<any>([]);
    const [nameTableBody, setNameTableBody] = useState<any>([]);
    const [addressTableBody, setAddressTableBody] = useState<any>([]);
    const [phoneEmailTableBody, setPhoneEmailTableBody] = useState<any>([]);
    const [identificationTableBody, setIdentificationTableBody] = useState<any>([]);
    const [raceTableBody, setRaceTableBody] = useState<any>([]);
    const [generalTableData, setGeneralTableData] = useState<any>(undefined);
    const [morbidityTableData, setMorbidityTableData] = useState<any>(undefined);
    const [ethnicityTableData, setEthnicityTableData] = useState<any>(undefined);

    const [currentPage, setCurrentPage] = useState<number>(1);
    const addCommentModalRef = useRef<ModalRef>(null);
    const addNameModalRef = useRef<ModalRef>(null);
    const addAddressModalRef = useRef<ModalRef>(null);
    const addPhoneEmailRef = useRef<ModalRef>(null);

    const detailsNameModalRef = useRef<ModalRef>(null);

    const deleteModalRef = useRef<ModalRef>(null);

    useEffect(() => {
        const tempArr = [];
        for (let i = 0; i < 3; i++) {
            tempArr.push({
                id: i + 1,
                checkbox: false,
                tableDetails: [
                    {
                        id: 1,
                        title: '07/27/2022'
                    },
                    {
                        id: 2,
                        title: 'Legal'
                    },
                    {
                        id: 3,
                        title: 'Dr.'
                    },
                    {
                        id: 4,
                        title: 'Smith, Johnny'
                    },
                    {
                        id: 5,
                        title: ''
                    },
                    {
                        id: 6,
                        title: 'Smith, Johnny'
                    },
                    {
                        id: 7,
                        title: (
                            <Button type="button" unstyled>
                                <Icon.MoreHoriz />
                            </Button>
                        )
                    }
                ]
            });
        }
        setTableBody([
            {
                id: 1,
                checkbox: false,
                tableDetails: [
                    { id: 1, title: `11/19/2022` },
                    {
                        id: 2,
                        title: 'This patient is currently waiting for a call from an investigator to get investigation started.'
                    },
                    {
                        id: 3,
                        title: (
                            <Button type="button" unstyled>
                                <Icon.MoreHoriz className="font-sans-lg" />
                            </Button>
                        ),
                        textAlign: 'center',
                        type: 'actions'
                    }
                ]
            }
        ]);
    }, []);

    const namesTableData = (names: FindPatientsByFilterQuery['findPatientsByFilter']['content'][0]['names']) => {
        const tempArr: any = [];
        names?.map((item, i: number) => {
            tempArr.push({
                id: i + 1,
                checkbox: false,
                tableDetails: [
                    {
                        id: 1,
                        title: 'Not available yet'
                    },
                    {
                        id: 2,
                        title: 'Not available yet'
                    },
                    {
                        id: 3,
                        title: item?.nmPrefix
                    },
                    {
                        id: 4,
                        title: `${item?.lastNm}, ${item?.firstNm}, ${item?.middleNm}`
                    },
                    {
                        id: 5,
                        title: item?.nmSuffix
                    },
                    {
                        id: 6,
                        title: 'Not available yet'
                    },
                    {
                        id: 7,
                        title: (
                            <Button type="button" unstyled>
                                <Icon.MoreHoriz className="font-sans-lg" />
                            </Button>
                        ),
                        textAlign: 'center',
                        type: 'actions'
                    }
                ],
                data: item
            });
        });
        setNameTableBody(tempArr);
    };

    const idTableData = (entityIds: FindPatientsByFilterQuery['findPatientsByFilter']['content'][0]['entityIds']) => {
        const tempArr: any = [];
        entityIds?.map((item, i: number) => {
            tempArr.push({
                id: i + 1,
                checkbox: false,
                tableDetails: [
                    {
                        id: 1,
                        title: 'Not available yet'
                    },
                    {
                        id: 2,
                        title: item?.typeDescTxt
                    },
                    {
                        id: 3,
                        title: item?.assigningAuthorityDescTxt
                    },
                    {
                        id: 4,
                        title: item?.rootExtensionTxt
                    },
                    {
                        id: 5,
                        title: (
                            <Button type="button" unstyled>
                                <Icon.MoreHoriz className="font-sans-lg" />
                            </Button>
                        ),
                        textAlign: 'center',
                        type: 'actions'
                    }
                ]
            });
        });
        setIdentificationTableBody(tempArr);
    };

    const phoneEmailTableData = (
        phoneEmailData: FindPatientsByFilterQuery['findPatientsByFilter']['content'][0]['nbsEntity']['entityLocatorParticipations']
    ) => {
        const tempArr: any = [];
        phoneEmailData?.map((element, i: number) => {
            if (element?.classCd !== 'PST') {
                tempArr.push({
                    id: i + 1,
                    checkbox: false,
                    tableDetails: [
                        {
                            id: 1,
                            title: 'Not available yet'
                        },
                        {
                            id: 2,
                            title: 'Not available yet'
                        },
                        {
                            id: 3,
                            title: element?.locator?.phoneNbrTxt
                        },
                        {
                            id: 4,
                            title: element?.locator?.emailAddress
                        },
                        {
                            id: 5,
                            title: (
                                <Button type="button" unstyled>
                                    <Icon.MoreHoriz className="font-sans-lg" />
                                </Button>
                            ),
                            textAlign: 'center',
                            type: 'actions'
                        }
                    ]
                });
            }
        });
        setPhoneEmailTableBody(tempArr);
    };

    const addressTableData = (
        entityLocatorParticipations: FindPatientsByFilterQuery['findPatientsByFilter']['content'][0]['nbsEntity']['entityLocatorParticipations']
    ) => {
        const tempArr: any = [];
        entityLocatorParticipations?.forEach((element, i: number) => {
            if (element?.classCd === 'PST') {
                tempArr.push({
                    id: i + 1,
                    checkbox: false,
                    tableDetails: [
                        {
                            id: 1,
                            title: 'Not available yet'
                        },
                        {
                            id: 2,
                            title: 'Not available yet'
                        },
                        {
                            id: 3,
                            title: element?.locator?.streetAddr1 + ' ' + element?.locator?.streetAddr1
                        },
                        {
                            id: 4,
                            title: element?.locator?.cityDescTxt
                        },
                        {
                            id: 5,
                            title: 'Not available yet'
                        },
                        {
                            id: 6,
                            title: element?.locator?.zipCd
                        },
                        {
                            id: 7,
                            title: (
                                <Button type="button" unstyled>
                                    <Icon.MoreHoriz className="font-sans-lg" />
                                </Button>
                            ),
                            textAlign: 'center',
                            type: 'actions'
                        }
                    ]
                });
            }
        });
        setAddressTableBody(tempArr);
    };

    const raceTableData = (
        raceId: FindPatientsByFilterQuery['findPatientsByFilter']['content'][0]['ethnicGroupInd']
    ) => {
        const tempArr: any = [];
        searchCriteria.races.map((race) => {
            if (race.id.code === raceId) {
                tempArr.push({
                    id: 1,
                    checkbox: false,
                    tableDetails: [
                        {
                            id: 1,
                            title: 'Not available yet'
                        },
                        {
                            id: 2,
                            title: race.codeDescTxt
                        },
                        {
                            id: 3,
                            title: 'Not available yet'
                        },
                        {
                            id: 5,
                            title: (
                                <Button type="button" unstyled>
                                    <Icon.MoreHoriz className="font-sans-lg" />
                                </Button>
                            ),
                            textAlign: 'center',
                            type: 'actions'
                        }
                    ]
                });
            }
        });
        setRaceTableBody(tempArr);
    };

    useEffect(() => {
        if (patientProfileData) {
            console.log('patientProfileData:', patientProfileData);
            namesTableData(patientProfileData?.names);
            idTableData(patientProfileData?.entityIds);
            raceTableData(patientProfileData?.electronicInd);
            addressTableData(patientProfileData?.nbsEntity.entityLocatorParticipations);
            phoneEmailTableData(patientProfileData?.nbsEntity.entityLocatorParticipations);
            setGeneralTableData([
                { title: 'As of:', text: format(new Date(patientProfileData.asOfDateGeneral), 'MM/dd/yyyy') },
                { title: 'Marital status::', text: '' },
                { title: 'Mother’s maiden name:', text: patientProfileData.mothersMaidenNm },
                { title: 'Number of adults in residence:', text: patientProfileData.adultsInHouseNbr },
                { title: 'Number of children in residence:', text: patientProfileData.childrenInHouseNbr },
                { title: 'Primary occupation:', text: '' },
                { title: 'Highest level of education:', text: '' },
                { title: 'Primary language:', text: '' },
                { title: 'Speaks english:', text: '' },
                { title: 'State HIV case ID:', text: '' }
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
                { title: 'Date of death:', text: '' },
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
        }
    }, [patientProfileData]);

    const [isEditModal, setIsEditModal] = useState<boolean>(false);
    const [isDeleteModal, setIsDeleteModal] = useState<boolean>(false);
    const [nameDetails, setNameDetails] = useState<any>(undefined);

    useEffect(() => {
        if (isDeleteModal) {
            deleteModalRef.current?.toggleModal();
        }
    }, [isDeleteModal]);

    return (
        <>
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    isPagination={true}
                    buttons={
                        <div className="grid-row">
                            <ModalToggleButton modalRef={addCommentModalRef} opener className="display-inline-flex">
                                <Icon.Add className="margin-right-05" />
                                Add comment
                            </ModalToggleButton>
                            <AddCommentModal modalRef={addCommentModalRef} />
                        </div>
                    }
                    tableHeader={'Administrative'}
                    tableHead={[
                        { name: 'As of', sortable: true },
                        { name: 'General comment', sortable: true },
                        { name: 'Actions', sortable: true }
                    ]}
                    tableBody={[]}
                    currentPage={currentPage}
                    handleNext={(e) => setCurrentPage(e)}
                />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    handleAction={(type, data) => {
                        console.log('type:', data);
                        if (type === 'edit') {
                            setIsEditModal(true);
                            addNameModalRef.current?.toggleModal();
                        }
                        if (type === 'delete') {
                            setIsDeleteModal(true);
                        }
                        if (type === 'details') {
                            setNameDetails(data);
                            detailsNameModalRef.current?.toggleModal();
                        }
                    }}
                    isPagination={true}
                    buttons={
                        <div className="grid-row">
                            <ModalToggleButton modalRef={addNameModalRef} opener className="display-inline-flex">
                                <Icon.Add className="margin-right-05" />
                                Add name
                            </ModalToggleButton>
                            <AddNameModal
                                modalHead={isEditModal ? 'Edit - Name' : 'Add - Name'}
                                handleSubmission={handleFormSubmission}
                                modalRef={addNameModalRef}
                            />
                            <DetailsNameModal data={nameDetails} modalRef={detailsNameModalRef} />
                        </div>
                    }
                    tableHeader={'Name'}
                    tableHead={[
                        { name: 'As of', sortable: true },
                        { name: 'Type', sortable: true },
                        { name: 'Prefix', sortable: true },
                        { name: 'Name ( last, first middle )', sortable: true },
                        { name: 'Suffix', sortable: true },
                        { name: 'Degree', sortable: true },
                        { name: 'Actions', sortable: true }
                    ]}
                    tableBody={nameTableBody}
                    currentPage={currentPage}
                    handleNext={(e) => setCurrentPage(e)}
                />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    isPagination={true}
                    buttons={
                        <div className="grid-row">
                            <ModalToggleButton modalRef={addAddressModalRef} opener className="display-inline-flex">
                                <Icon.Add className="margin-right-05" />
                                Add address
                            </ModalToggleButton>
                            <AddAddressModal modalRef={addAddressModalRef} />
                        </div>
                    }
                    tableHeader={'Address'}
                    tableHead={[
                        { name: 'As of', sortable: true },
                        { name: 'Type', sortable: true },
                        { name: 'Address', sortable: true },
                        { name: 'City', sortable: true },
                        { name: 'State', sortable: true },
                        { name: 'Zip', sortable: true },
                        { name: 'Actions', sortable: true }
                    ]}
                    tableBody={addressTableBody}
                    currentPage={currentPage}
                    handleNext={(e) => setCurrentPage(e)}
                />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    isPagination={true}
                    buttons={
                        <div className="grid-row">
                            <ModalToggleButton modalRef={addPhoneEmailRef} opener className="display-inline-flex">
                                <Icon.Add className="margin-right-05" />
                                Add phone & email
                            </ModalToggleButton>
                            <AddPhoneEmailModal modalRef={addPhoneEmailRef} />
                        </div>
                    }
                    tableHeader={'Phone & email'}
                    tableHead={[
                        { name: 'As of', sortable: true },
                        { name: 'Type', sortable: true },
                        { name: 'Phone number', sortable: true },
                        { name: 'Email address', sortable: true },
                        { name: 'Actions', sortable: true }
                    ]}
                    tableBody={phoneEmailTableBody}
                    currentPage={currentPage}
                    handleNext={(e) => setCurrentPage(e)}
                />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    isPagination={true}
                    buttons={
                        <div className="grid-row">
                            <Button type="button" className="grid-row">
                                <Icon.Add className="margin-right-05" />
                                Add identification
                            </Button>
                        </div>
                    }
                    tableHeader={'Identification'}
                    tableHead={[
                        { name: 'As of', sortable: true },
                        { name: 'Type', sortable: true },
                        { name: 'Authority', sortable: true },
                        { name: 'Value', sortable: true },
                        { name: 'Actions', sortable: true }
                    ]}
                    tableBody={identificationTableBody}
                    currentPage={currentPage}
                    handleNext={(e) => setCurrentPage(e)}
                />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    isPagination={true}
                    buttons={
                        <div className="grid-row">
                            <Button type="button" className="grid-row">
                                <Icon.Add className="margin-right-05" />
                                Add race
                            </Button>
                        </div>
                    }
                    tableHeader={'Race'}
                    tableHead={[
                        { name: 'As of', sortable: true },
                        { name: 'Race', sortable: true },
                        { name: 'Detailed race', sortable: true },
                        { name: 'Actions', sortable: false }
                    ]}
                    tableBody={raceTableBody}
                    currentPage={currentPage}
                    handleNext={(e) => setCurrentPage(e)}
                />
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
                            <HorizontalTable
                                type="sex"
                                tableHeader="Sex & Birth"
                                tableData={[
                                    { title: 'As of:', text: '' },
                                    { title: 'Date of death:', text: '' },
                                    { title: 'Current age:', text: '' },
                                    { title: 'Current sex:', text: '' },
                                    { title: 'Unknown reason:', text: '' },
                                    { title: 'Transgender information:', text: '' },
                                    { title: 'Additional gender:', text: '' },
                                    { title: 'Birth sex:', text: '' },
                                    { title: 'Multiple birth:', text: '' },
                                    { title: 'Birth order:', text: '' },
                                    { title: 'Birth city:', text: '' },
                                    { title: 'Birth state:', text: '' },
                                    { title: 'Birth county:', text: '' },
                                    { title: 'Birth country:', text: '' }
                                ]}
                            />
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
