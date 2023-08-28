import { Grid, Pagination } from '@trussworks/react-uswds';
import { useEffect, useRef } from 'react';
import { LabReport, OrganizationParticipation, PersonParticipation } from '../../../generated/graphql/schema';
import { calculateAge } from '../../..//utils/util';
import '../AdvancedSearch.scss';
import { useNavigate } from 'react-router';
import { ClassicLink } from 'classic';
import NoData from 'components/NoData/NoData';

type LabReportResultsProps = {
    data: [LabReport];
    totalResults: number;
    handlePagination: (page: number) => void;
    currentPage: number;
};

export const LabReportResults = ({ data, totalResults, handlePagination, currentPage }: LabReportResultsProps) => {
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

    const getPatient = (labReport: LabReport): PersonParticipation | undefined | null => {
        return labReport.personParticipations?.find(
            (p) => p?.typeDescTxt === 'Patient subject' || p?.typeCd === 'PATSBJ'
        );
    };

    const getOrderingProvidorName = (labReport: LabReport): string | undefined => {
        const provider = labReport.personParticipations?.find((p) => p?.typeCd === 'ORD' && p?.personCd === 'PRV');
        if (provider) {
            return `${provider.firstName} ${provider.lastName}`;
        } else {
            return undefined;
        }
    };

    const getReportingFacility = (labReport: LabReport): OrganizationParticipation | undefined | null => {
        return labReport.organizationParticipations?.find((o) => o?.typeCd === 'AUT');
    };

    const getDescription = (labReport: LabReport): string => {
        // TODO - there could be multiple tests associated with one lab report. How to display them in UI
        const observation = labReport.observations?.find((o) => o?.altCd && o?.displayName && o?.cdDescTxt);
        if (observation) {
            return `${observation.cdDescTxt} = ${observation.displayName}`;
        } else {
            return 'No Data';
        }
    };

    const buildPatientDetails = (labReport: LabReport) => {
        const patient = getPatient(labReport);
        let name = '';
        let birthDate: string | undefined;
        let age: string | undefined;
        let sex: string | undefined;
        if (patient) {
            name = !patient.lastName && !patient.firstName ? `No Data` : `${patient.lastName}, ${patient.firstName}`;
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
                    {name ? (
                        <a
                            onClick={redirectPatientProfile}
                            className="margin-0 font-sans-md margin-top-05 text-bold text-primary word-break"
                            style={{ wordBreak: 'break-word', cursor: 'pointer' }}>
                            {name}
                        </a>
                    ) : (
                        <NoData />
                    )}
                </Grid>
                <Grid col={12} className="margin-bottom-2">
                    <div className="grid-row flex-align-center">
                        <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                            DATE OF BIRTH
                        </h5>
                        <p className="margin-0 font-sans-1xs text-normal">
                            <>
                                {birthDate ? birthDate : <NoData />}
                                <span className="font-sans-2xs"> {age ? `(${age})` : ''}</span>
                            </>
                        </p>
                    </div>
                    <div className="grid-row flex-align-center">
                        <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">SEX</h5>
                        <p className="margin-0 font-sans-1xs text-normal">{sex ? sex : <NoData />}</p>
                    </div>
                    <div className="grid-row flex-align-center">
                        <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">PATIENT ID</h5>
                        <p className="margin-0 font-sans-1xs text-normal">{patient?.shortId}</p>
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
                                <Grid col={4}>{buildPatientDetails(item)}</Grid>
                                <Grid col={3}>
                                    <Grid row gap={3}>
                                        <Grid col={12} className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal text-gray-50">DOCUMENT TYPE</h5>
                                            <ClassicLink
                                                className="margin-0 font-sans-md margin-top-05 text-bold text-primary word-break"
                                                url={`/nbs/api/profile/${getPatient(item)?.shortId}/report/lab/${
                                                    item.id
                                                }`}>
                                                Lab Report
                                            </ClassicLink>
                                        </Grid>
                                        <Grid col={12} className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                                DATE RECEIVED
                                            </h5>
                                            <p className="margin-0 font-sans-1xs text-normal">
                                                {formatDate(item.addTime) || 'No Data'}
                                            </p>
                                        </Grid>
                                        <Grid col={12} className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                                DESCRIPTION
                                            </h5>
                                            {getDescription(item) === 'No Data' ? (
                                                <NoData />
                                            ) : (
                                                <p className="margin-0 font-sans-1xs text-normal">
                                                    {getDescription(item)}
                                                </p>
                                            )}
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid col={3}>
                                    <Grid row gap={3}>
                                        <Grid col={12} className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                                REPORTING FACILITY
                                            </h5>
                                            <p className="margin-0 font-sans-1xs text-normal">
                                                {getReportingFacility(item)?.name ?? <NoData />}
                                            </p>
                                        </Grid>
                                        <Grid col={12} className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                                ORDERING PROVIDOR
                                            </h5>
                                            <p className="margin-0 font-sans-1xs text-normal">
                                                {getOrderingProvidorName(item) ?? <NoData />}
                                            </p>
                                        </Grid>
                                        <Grid col={12} className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal text-gray-50">JURISDICTION</h5>
                                            <p className="margin-0 font-sans-1xs text-normal">
                                                {item.jurisdictionCodeDescTxt || <NoData />}
                                            </p>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid col={2}>
                                    <Grid row gap={3}>
                                        <Grid col={12} className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                                ASSOCIATED WITH
                                            </h5>
                                            <div className="margin-0 font-sans-1xs text-normal">
                                                {(!item.associatedInvestigations ||
                                                    item.associatedInvestigations.length == 0) && <NoData />}
                                                {item.associatedInvestigations &&
                                                    item.associatedInvestigations?.length > 0 &&
                                                    item.associatedInvestigations?.map((i, index) => (
                                                        <div key={index}>
                                                            <p
                                                                className="margin-0 text-primary text-bold"
                                                                style={{ wordBreak: 'break-word' }}>
                                                                {i?.localId}
                                                            </p>
                                                            <p className="margin-0">{i?.cdDescTxt}</p>
                                                        </div>
                                                    ))}
                                            </div>
                                        </Grid>
                                        <Grid col={12} className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                                LOCAL ID
                                            </h5>
                                            <p className="margin-0 font-sans-1xs text-normal">
                                                {item.localId || <NoData />}
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
