import { Grid, Pagination } from '@trussworks/react-uswds';
import { LabReport, OrganizationParticipation, PersonParticipation } from 'generated/graphql/schema';
import 'apps/search/advancedSearch/AdvancedSearch.scss';
import { ClassicLink } from 'classic';
import { NoData } from 'components/NoData';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import { PatientDetails } from 'apps/search/event/components/PatientDetails';
import { internalizeDate } from 'date';

type LabReportResultsProps = {
    data: [LabReport];
    totalResults: number;
    handlePagination: (page: number) => void;
    currentPage: number;
};

export const LabReportResults = ({ data, totalResults, handlePagination, currentPage }: LabReportResultsProps) => {
    const handleNext = (page: number) => {
        handlePagination(page);
    };

    return (
        <div className="margin-x-4">
            <div>
                {data &&
                    data?.length > 0 &&
                    data?.map((item, index: number) => <LabReportSearchResult item={item} key={index} />)}
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

const getPatient = (labReport: LabReport): PersonParticipation | undefined | null => {
    return labReport.personParticipations?.find((p) => p?.typeDescTxt === 'Patient subject' || p?.typeCd === 'PATSBJ');
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

type LabReportSearchResultProps = {
    item: LabReport;
};

const LabReportSearchResult = ({ item }: LabReportSearchResultProps) => {
    const patient = getPatient(item);
    return (
        <div className="padding-x-3 padding-top-3 padding-bottom-2 margin-bottom-3 bg-white border border-base-light radius-md">
            <Grid row gap={3}>
                <Grid col={4}>
                    <PatientDetails patient={patient} />
                </Grid>
                <Grid col={3}>
                    <Grid row gap={3}>
                        <Grid col={12} className="margin-bottom-2">
                            <p className="margin-0 text-normal search-result-item-label text-gray-50">DOCUMENT TYPE</p>
                            <ClassicLink
                                className="margin-0 font-sans-md margin-top-05 text-bold text-primary word-break"
                                url={`/nbs/api/profile/${getPatient(item)?.personParentUid}/report/lab/${item.id}`}>
                                Lab Report
                            </ClassicLink>
                        </Grid>
                        <Grid col={12} className="margin-bottom-2">
                            <p className="margin-0 text-normal search-result-item-label text-gray-50 margin-right-1">
                                DATE RECEIVED
                            </p>
                            <p className="margin-0 font-sans-1xs text-normal">
                                {internalizeDate(item.addTime) || 'No Data'}
                            </p>
                        </Grid>
                        <Grid col={12} className="margin-bottom-2">
                            <p className="margin-0 text-normal search-result-item-label text-gray-50 margin-right-1">
                                DESCRIPTION
                            </p>
                            {getDescription(item) === 'No Data' ? (
                                <NoData />
                            ) : (
                                <p className="margin-0 font-sans-1xs text-normal">{getDescription(item)}</p>
                            )}
                        </Grid>
                    </Grid>
                </Grid>
                <Grid col={3}>
                    <Grid row gap={3}>
                        <Grid col={12} className="margin-bottom-2">
                            <p className="margin-0 text-normal search-result-item-label text-gray-50 margin-right-1">
                                REPORTING FACILITY
                            </p>
                            <p className="margin-0 font-sans-1xs text-normal">
                                {getReportingFacility(item)?.name ?? <NoData />}
                            </p>
                        </Grid>
                        <Grid col={12} className="margin-bottom-2">
                            <p className="margin-0 text-normal search-result-item-label text-gray-50 margin-right-1">
                                ORDERING PROVIDOR
                            </p>
                            <p className="margin-0 font-sans-1xs text-normal">
                                {getOrderingProvidorName(item) ?? <NoData />}
                            </p>
                        </Grid>
                        <SearchCriteriaContext.Consumer>
                            {({ searchCriteria }) => (
                                <Grid col={12} className="margin-bottom-2">
                                    <p className="margin-0 text-normal search-result-item-label text-gray-50">
                                        JURISDICTION
                                    </p>
                                    <p className="margin-0 font-sans-1xs text-normal">
                                        {item.jurisdictionCd ? (
                                            searchCriteria.jurisdictions.find(
                                                (j) => j.id === item.jurisdictionCd?.toString()
                                            )?.codeDescTxt
                                        ) : (
                                            <NoData />
                                        )}
                                    </p>
                                </Grid>
                            )}
                        </SearchCriteriaContext.Consumer>
                    </Grid>
                </Grid>
                <Grid col={2}>
                    <Grid row gap={3}>
                        <Grid col={12} className="margin-bottom-2">
                            <p className="margin-0 text-normal search-result-item-label text-gray-50 margin-right-1">
                                ASSOCIATED WITH
                            </p>
                            <div className="margin-0 font-sans-1xs text-normal">
                                {(!item.associatedInvestigations || item.associatedInvestigations.length == 0) && (
                                    <NoData />
                                )}
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
                            <p className="margin-0 text-normal search-result-item-label text-gray-50 margin-right-1">
                                LOCAL ID
                            </p>
                            <p className="margin-0 font-sans-1xs text-normal">{item.localId || <NoData />}</p>
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </div>
    );
};
