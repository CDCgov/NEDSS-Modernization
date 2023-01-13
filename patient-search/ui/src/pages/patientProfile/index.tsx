import { Button, Grid, Icon } from '@trussworks/react-uswds';
import './style.scss';
import { useContext, useEffect, useState } from 'react';
import { EncryptionControllerService } from '../../generated';
import { useSearchParams } from 'react-router-dom';
import { UserContext } from '../../providers/UserContext';
import { FindPatientsByFilterQuery } from '../../generated/graphql/schema';
import { calculateAge } from '../../utils/util';

export const PatientProfile = () => {
    const { state } = useContext(UserContext);
    const [searchParams] = useSearchParams();
    const [profileData, setProfileData] = useState<FindPatientsByFilterQuery['findPatientsByFilter']['content'][0]>();

    useEffect(() => {
        EncryptionControllerService.decryptUsingPost({
            encryptedString: searchParams?.get('data') || '',
            authorization: `Bearer ${state.getToken()}`
        }).then(async (data: any) => {
            setProfileData(data);
        });
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
            </div>
        </div>
    );
};
