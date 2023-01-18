import { Button, Grid, Icon } from '@trussworks/react-uswds';
import './style.scss';
import { useContext, useEffect, useState } from 'react';
import { EncryptionControllerService } from '../../generated';
import { useSearchParams } from 'react-router-dom';
import { UserContext } from '../../providers/UserContext';
import { FindPatientsByFilterQuery } from '../../generated/graphql/schema';
import { calculateAge } from '../../utils/util';
import { TableComponent } from '../../components/TableComponent/TableComponent';

// To be added for future table list
{
    /* <div>
    <strong>Reporting facility:</strong>
    <br />
    <span>Lab Corp</span>
    <br />
    <strong>Ordering facility:</strong>
    <br />
    <span>Dekalb General</span>
    <br />
    <strong>Ordering provider:</strong>
    <br />
    <span>Dr. Gene Davis SR</span>
</div>; */
}

export const PatientProfile = () => {
    const { state } = useContext(UserContext);
    const [searchParams] = useSearchParams();
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [profileData, setProfileData] = useState<FindPatientsByFilterQuery['findPatientsByFilter']['content'][0]>();

    useEffect(() => {
        EncryptionControllerService.decryptUsingPost({
            encryptedString: searchParams?.get('data') || '',
            authorization: `Bearer ${state.getToken()}`
        }).then(async (data: any) => {
            setProfileData(data);
        });
    }, []);

    const [tableBody, setTableBody] = useState<any>([]);

    useEffect(() => {
        const tempArr = [];
        for (let i = 0; i < 10; i++) {
            tempArr.push({
                id: i + 1,
                checkbox: true,
                tableDetails: [
                    { id: 1, title: `12/${i + 1}/2021` },
                    { id: 2, title: 'Acute flaccid myelitis' },
                    { id: 3, title: i === 3 ? 'Confirmed' : i === 7 ? 'Not a case' : null },
                    { id: 4, title: i === 1 ? 'Completed' : null },
                    { id: 5, title: 'Cobb County' },
                    { id: 6, title: i === 4 ? 'John Xerogeanes' : null },
                    { id: 7, title: 'CAS10004022GA01' },
                    { id: 8, title: i === 4 ? 'COIN1000XX01' : null }
                ]
            });
        }
        setTableBody(tempArr);
    }, []);

    return (
        <div style={{ backgroundColor: '#F1F6F9' }} className="height-full">
            <div
                className="bg-white grid-row flex-align-center flex-justify"
                style={{ borderBottom: '1px solid #DFE1E2' }}>
                <h1 className="font-sans-xl text-medium">Patient Profile</h1>
                <div>
                    <Button style={{ display: 'inline-flex' }} type={'submit'}>
                        <Icon.Print className="margin-right-05" />
                        Print
                    </Button>
                    <Button className="delete-btn" style={{ display: 'inline-flex' }} type={'submit'}>
                        <Icon.Delete className="margin-right-05" />
                        Delete Patient
                    </Button>
                </div>
            </div>
            <div className="main-body">
                <div className="margin-y-2 flex-row common-card">
                    <div className="grid-row flex-align-center flex-justify padding-2 border-bottom border-base-lighter">
                        <p className="font-sans-xl text-bold margin-0">
                            {profileData?.lastNm}, {profileData?.firstNm}
                        </p>
                        <h5 className="font-sans-md text-medium margin-0">Patient ID: {profileData?.localId}</h5>
                    </div>
                    <Grid row gap={3} className="padding-3">
                        <Grid row col={3}>
                            <Grid col={12}>
                                <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">SEX</h5>
                                <p className="margin-0 font-sans-1xs text-normal">
                                    {profileData?.currSexCd === 'M'
                                        ? 'Male'
                                        : profileData?.currSexCd === 'F'
                                        ? 'Female'
                                        : 'Unknown'}
                                </p>
                            </Grid>
                            <Grid col={12} className="margin-top-3">
                                <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                    DATE OF BIRTH
                                </h5>
                                <p className="margin-0 font-sans-1xs text-normal">
                                    {new Date(profileData?.birthTime).toLocaleDateString('en-US', {
                                        timeZone: 'UTC'
                                    })}{' '}
                                    ({calculateAge(new Date(profileData?.birthTime))})
                                </p>
                            </Grid>
                        </Grid>

                        <Grid row col={3}>
                            <Grid col={12}>
                                <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                    PHONE
                                </h5>
                                <p className="margin-0 font-sans-1xs text-normal">(555) 555-5555</p>
                            </Grid>
                            <Grid col={12} className="margin-top-3">
                                <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                    EMAIL
                                </h5>
                                <p className="margin-0 font-sans-1xs text-normal">sjohn@helloworld.com</p>
                            </Grid>
                        </Grid>

                        <Grid row col={3}>
                            <Grid col={12}>
                                <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                    ADDRESS
                                </h5>
                                <p className="margin-0 font-sans-1xs text-normal">
                                    12 Main St, Apt 12 Atlanta, GA, 30342
                                </p>
                            </Grid>
                        </Grid>

                        <Grid row col={3}>
                            <Grid col={12}>
                                <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">RACE</h5>
                                <p className="margin-0 font-sans-1xs text-normal">White</p>
                            </Grid>
                            <Grid col={12} className="margin-top-3">
                                <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                    ETHNICITY
                                </h5>
                                <p className="margin-0 font-sans-1xs text-normal">Not Hispanic or Latino</p>
                            </Grid>
                        </Grid>
                    </Grid>
                </div>

                <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                    <TableComponent
                        isPagination={true}
                        tableHeader={'Open investigations'}
                        tableHead={[
                            { name: 'Start Date', sortable: true },
                            { name: 'Condition', sortable: true },
                            { name: 'Case status', sortable: true },
                            { name: 'Notification', sortable: true },
                            { name: 'Jurisdiction', sortable: true },
                            { name: 'Investigator', sortable: true },
                            { name: 'Investigation #', sortable: false },
                            { name: 'Co-infection #', sortable: false }
                        ]}
                        tableBody={tableBody}
                        currentPage={currentPage}
                        handleNext={(e) => setCurrentPage(e)}
                    />
                </div>
            </div>
        </div>
    );
};
