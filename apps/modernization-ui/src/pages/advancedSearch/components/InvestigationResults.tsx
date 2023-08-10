import { Grid, Pagination } from '@trussworks/react-uswds';
import { useEffect, useRef } from 'react';
import { Investigation, PersonParticipation } from '../../../generated/graphql/schema';
import { calculateAge } from '../../../utils/util';
import '../AdvancedSearch.scss';
import { useNavigate } from 'react-router';
import { NotificationStatus } from '../../../generated/graphql/schema';

type InvestigationResultsProps = {
    data: [Investigation];
    totalResults: number;
    handlePagination: (page: number) => void;
    currentPage: number;
};

export const InvestigationResults = ({
    data,
    totalResults,
    handlePagination,
    currentPage
}: InvestigationResultsProps) => {
    const searchItemsRef: any = useRef();
    const navigate = useNavigate();

    // Update 'width' and 'height' when the window resizes
    useEffect(() => {
        window.addEventListener('resize', getListSize);
    }, []);

    useEffect(() => {
        getListSize();
    }, [data]);

    const getListSize = () => {
        return searchItemsRef.current?.clientHeight;
    };

    const handleNext = (page: number) => {
        handlePagination(page);
    };

    const formatDate = (date: Date) => {
        return new Date(date).toLocaleDateString('en-US', {
            timeZone: 'UTC'
        });
    };

    const getPatient = (investigation: Investigation): PersonParticipation | undefined | null => {
        return investigation.personParticipations?.find((p) => p?.typeCd === 'SubjOfPHC');
    };

    const getInvestigatorName = (investigation: Investigation): string | undefined => {
        const provider = investigation.personParticipations?.find((p) => p?.typeCd === 'InvestgrOfPHC');
        if (provider) {
            return `${provider.firstName} ${provider.lastName}`;
        } else {
            return undefined;
        }
    };

    const getInvestigationStatusString = (investigation: Investigation): string => {
        switch (investigation.investigationStatusCd) {
            case 'O':
                return 'OPEN';
            case 'C':
                return 'CLOSED';
            default:
                return investigation.investigationStatusCd ?? '';
        }
    };

    const buildPatientDetails = (investigation: Investigation) => {
        const patient = getPatient(investigation);
        let name = '';
        let birthDate: string | undefined;
        let age: string | undefined;
        let sex: string | undefined;
        if (patient) {
            name = !patient.lastName && !patient.firstName ? `No data` : `${patient.lastName}, ${patient.firstName}`;
            if (patient.birthTime) {
                birthDate = formatDate(patient.birthTime);
                age = calculateAge(new Date(patient.birthTime));
            }
            sex = patient.currSexCd === 'M' ? 'Male' : patient.currSexCd === 'F' ? 'Female' : 'Unknown';
        }

        const redirectPatientProfile = async () => {
            navigate(`/patient-profile/${patient?.shortId}`);
        };

        return (
            <Grid row gap={3}>
                <Grid col={12} className="margin-bottom-2">
                    <h5 className="margin-0 text-normal text-gray-50">LEGAL NAME</h5>
                    <p
                        onClick={redirectPatientProfile}
                        className="margin-0 font-sans-md margin-top-05 text-bold text-primary word-break"
                        style={{ wordBreak: 'break-word', cursor: 'pointer' }}>
                        {name}
                    </p>
                </Grid>
                <Grid col={12} className="margin-bottom-2">
                    <div className="grid-row flex-align-center">
                        <h5 className="margin-0 text-normal font-sans-3xs text-gray-50 margin-right-1">
                            DATE OF BIRTH
                        </h5>
                        <p className="margin-0 font-sans-2xs text-normal">
                            <>
                                {birthDate ? birthDate : <span className="font-sans-2xs">--</span>}
                                <span className="font-sans-2xs"> {age ? `(${age})` : ''}</span>
                            </>
                        </p>
                    </div>
                    <div className="grid-row flex-align-center">
                        <h5 className="margin-0 text-normal font-sans-3xs text-gray-50 margin-right-1">SEX</h5>
                        <p className="margin-0 font-sans-2xs text-normal">
                            {sex ? sex : <span className="font-sans-2xs">--</span>}
                        </p>
                    </div>
                    <div className="grid-row flex-align-center">
                        <h5 className="margin-0 text-normal font-sans-3xs text-gray-50 margin-right-1">PATIENT ID</h5>
                        <p className="margin-0 font-sans-2xs text-normal">{patient?.shortId}</p>
                    </div>
                </Grid>
            </Grid>
        );
    };

    return (
        <div className="margin-x-4">
            {Boolean(totalResults && data?.length > 0) && (
                <Grid row className="flex-align-center flex-justify">
                    <p className="margin-0 font-sans-3xs margin-top-05 text-normal text-base">
                        Showing {data.length} of {totalResults}
                    </p>
                    <Pagination
                        style={{ justifyContent: 'flex-end' }}
                        totalPages={Math.ceil(totalResults / 25)}
                        currentPage={currentPage}
                        pathname={'/advanced-search'}
                        onClickNext={() => handleNext(currentPage + 1)}
                        onClickPrevious={() => handleNext(currentPage - 1)}
                        onClickPageNumber={(_, page) => handleNext(page)}
                    />
                </Grid>
            )}
            <div ref={searchItemsRef}>
                {data &&
                    data?.length > 0 &&
                    data?.map((item, index: number) => (
                        <div
                            key={index}
                            className="padding-x-3 padding-top-3 padding-bottom-2 margin-bottom-3 bg-white border border-base-light radius-md">
                            <Grid row gap={3}>
                                <Grid col={3}>{buildPatientDetails(item)}</Grid>
                                <Grid col={3}>
                                    <Grid row gap={3} className="fill-height">
                                        <Grid col={12} className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal text-gray-50">CONDITION</h5>
                                            <p
                                                className="margin-0 font-sans-md margin-top-05 text-bold text-primary word-break"
                                                style={{ wordBreak: 'break-word' }}>
                                                {item.cdDescTxt}
                                            </p>
                                            <span>{item.localId}</span>
                                        </Grid>
                                        <Grid col={12} className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                                START DATE
                                            </h5>
                                            <p className="margin-0 font-sans-1xs text-normal">
                                                {formatDate(item.addTime)}
                                            </p>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid col={3}>
                                    <Grid row gap={3} className="fill-height">
                                        <Grid col={12} className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal text-gray-50">JURISDICTION</h5>
                                            <p className="margin-0 font-sans-1xs text-normal">
                                                {item.jurisdictionCodeDescTxt}
                                            </p>
                                        </Grid>
                                        <Grid col={12} className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                                INVESTIGATOR
                                            </h5>
                                            <p className="margin-0 font-sans-1xs text-normal">
                                                {getInvestigatorName(item) ?? '--'}
                                            </p>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid col={2}>
                                    <Grid row gap={3} className="fill-height">
                                        <Grid col={12} className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal text-gray-50">STATUS</h5>
                                            <p
                                                className="margin-0 font-sans-1xs text-normal status"
                                                style={{ backgroundColor: '#2cb844' }}>
                                                {getInvestigationStatusString(item)}
                                            </p>
                                        </Grid>
                                        <Grid col={12} className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal font-sans-3xs text-gray-50 margin-right-1">
                                                NOTIFICATION
                                            </h5>
                                            <p className="margin-0 font-sans-1xs text-normal">
                                                {item.notificationRecordStatusCd} {NotificationStatus.Approved}
                                            </p>
                                        </Grid>
                                    </Grid>
                                </Grid>
                            </Grid>
                        </div>
                    ))}
            </div>
            {Boolean(totalResults && data?.length > 0) && (
                <Pagination
                    style={{ justifyContent: 'flex-end' }}
                    totalPages={Math.ceil(totalResults / 25)}
                    currentPage={currentPage}
                    pathname={'/advanced-search'}
                    onClickNext={() => handleNext(currentPage + 1)}
                    onClickPrevious={() => handleNext(currentPage - 1)}
                    onClickPageNumber={(_, page) => handleNext(page)}
                />
            )}
        </div>
    );
};
