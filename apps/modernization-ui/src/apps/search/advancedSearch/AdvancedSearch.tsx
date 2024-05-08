import { Alert, Grid, Icon, Pagination } from '@trussworks/react-uswds';
import { useContext, useEffect, useRef, useState } from 'react';
import { useNavigate, useParams, useSearchParams } from 'react-router-dom';

import { SearchCriteriaProvider } from 'providers/SearchCriteriaContext';
import {
    FindInvestigationsByFilterQuery,
    FindLabReportsByFilterQuery,
    FindPatientsByFilterQuery,
    Investigation,
    InvestigationFilter,
    LabReport,
    LabReportFilter,
    PersonFilter,
    RecordStatus,
    SortDirection,
    SortField,
    useFindInvestigationsByFilterLazyQuery,
    useFindLabReportsByFilterLazyQuery,
    useFindPatientsByFilterLazyQuery
} from 'generated/graphql/schema';
import { EncryptionControllerService } from 'generated/services/EncryptionControllerService';
import { UserContext } from 'providers/UserContext';
import './AdvancedSearch.scss';
import { AdvancedSearchChips } from 'apps/search/advancedSearch/components/chips/AdvancedSearchChips';
import { InvestigationResults } from 'apps/search/event/components/InvestigationSearch/InvestigationResults';
import { LabReportResults } from 'apps/search/event/components/LabReportSearch/LabReportResults';
import { EventSearch } from 'apps/search/event/components/EventSearch/EventSearch';
import { useSkipLink } from 'SkipLink/SkipLinkContext';
import { externalize, internalize } from 'apps/search/patient';
import { PatientSearch } from 'apps/search/patient/patientSearch/PatientSearch';
import { PatientResults } from 'apps/search/patient/PatientResults';
import { focusedTarget } from 'utils';
import { Icon as NBSIcon } from 'components/Icon/Icon';
import { TabNavigationEntry, TabNavigation } from 'components/TabNavigation/TabNavigation';
import { Button } from 'components/button/Button';

export enum SEARCH_TYPE {
    PERSON = 'search',
    INVESTIGATION = 'investigation',
    LAB_REPORT = 'labReport'
}

enum ACTIVE_TAB {
    PERSON = 'person',
    EVENT = 'event'
}

export const AdvancedSearch = () => {
    const { searchType } = useParams();
    // shared variables
    const { state } = useContext(UserContext);
    const navigate = useNavigate();
    const [activeTab, setActiveTab] = useState(searchType === 'event' ? 'event' : 'person');
    const [lastSearchType, setLastSearchType] = useState<SEARCH_TYPE | undefined>();
    const [searchParams] = useSearchParams();
    const [submitted, setSubmitted] = useState(false);
    const wrapperRef = useRef<any>(null);
    const [sort, setSort] = useState<{ sortDirection?: SortDirection; sortField: SortField }>({
        sortField: SortField.Relevance
    });
    const PAGE_SIZE = 25;

    // patient search variables
    const [personFilter, setPersonFilter] = useState<PersonFilter>();
    const addPatiendRef = useRef<any>(null);
    const [showSorting, setShowSorting] = useState<boolean>(false);
    const [currentPage, setCurrentPage] = useState(1);

    // pagination variables
    const [resultStartCount, setResultStartCount] = useState<number>(0);
    const [resultEndCount, setResultEndCount] = useState<number>(0);
    const [resultTotal, setResultTotal] = useState<number>(0);
    const { skipTo } = useSkipLink();

    const [showAddNewDropDown, setShowAddNewDropDown] = useState<boolean>(false);
    const [
        findPatients,
        {
            error: patientDataError,
            loading: patientDataLoading,
            data: { findPatientsByFilter: patientData } = {
                findPatientsByFilter: {} as FindPatientsByFilterQuery['findPatientsByFilter']
            }
        }
    ] = useFindPatientsByFilterLazyQuery();
    // investigation search variables
    const [investigationFilter, setInvestigationFilter] = useState<InvestigationFilter>();
    const [
        findInvestigations,
        {
            error: investigationError,
            loading: investigationLoading,
            data: { findInvestigationsByFilter: investigationData } = {
                findInvestigationsByFilter: {} as FindInvestigationsByFilterQuery['findInvestigationsByFilter']
            }
        }
    ] = useFindInvestigationsByFilterLazyQuery();

    // lab report search variables
    const [labReportFilter, setLabReportFilter] = useState<LabReportFilter>();
    const [
        findLabReports,
        {
            error: labReportError,
            loading: labReportLoading,
            data: { findLabReportsByFilter: labReportData } = {
                findLabReportsByFilter: {} as FindLabReportsByFilterQuery['findLabReportsByFilter']
            }
        }
    ] = useFindLabReportsByFilterLazyQuery();

    useEffect(() => {
        searchType && setActiveTab(searchType);
    }, [searchType]);

    useEffect(() => {
        handleClearAll();
    }, [activeTab]);

    useEffect(() => {
        function handleClickOutside(event: any) {
            if (wrapperRef.current && !wrapperRef.current.contains(event.target)) {
                setShowSorting(false);
            }

            if (addPatiendRef.current && !addPatiendRef.current.contains(event.target)) {
                setShowAddNewDropDown(false);
            }
        }
        // Bind the event listener
        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            // Unbind the event listener on clean up
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, [wrapperRef]);

    /**
     * Handles extracting and submitting the query from the q parameter,
     * this object could be a PersonFilter, InvestigationFilter or LabReportFilter
     */
    useEffect(() => {
        const queryParam = searchParams?.get('q');
        const type = searchParams?.get('type');
        if (!queryParam) {
            // no query parameters specified or user is not logged in
            // setActiveTab('person');
            return;
        }

        // decrypt the filter parameter
        EncryptionControllerService.decrypt({
            requestBody: queryParam
        }).then(async (filter: any) => {
            if (isEmpty(filter)) {
                // empty filter, clear content
                setSubmitted(true);
            }
            // perform the search based on the 'type' parameter
            switch (type) {
                case SEARCH_TYPE.INVESTIGATION:
                    performInvestigationSearch(filter);
                    break;
                case SEARCH_TYPE.LAB_REPORT:
                    performLabReportSearch(filter);
                    break;
                default:
                    performPatientSearch(filter);
            }
            setSubmitted(true);
        });
    }, [searchParams, state.isLoggedIn, sort, currentPage]);

    const performPatientSearch = async (filter: PersonFilter) => {
        const patientsResponse = await findPatients({
            variables: {
                filter: filter,
                page: {
                    pageNumber: currentPage - 1,
                    pageSize: PAGE_SIZE,
                    ...sort
                }
            }
        });
        const patientsResult = patientsResponse.data?.findPatientsByFilter;
        updatePaginationDetails(patientsResult);
        setLastSearchType(SEARCH_TYPE.PERSON);
        setActiveTab(ACTIVE_TAB.PERSON);

        const internalized = internalize(filter);

        setPersonFilter(internalized);
    };

    const performInvestigationSearch = async (filter: InvestigationFilter) => {
        const investigationResponse = await findInvestigations({
            variables: {
                filter: filter,
                page: {
                    pageNumber: currentPage - 1,
                    pageSize: PAGE_SIZE,
                    ...sort
                }
            }
        });
        const investigationsResult = investigationResponse.data?.findInvestigationsByFilter;
        updatePaginationDetails(investigationsResult);
        setLastSearchType(SEARCH_TYPE.INVESTIGATION);
        setActiveTab(ACTIVE_TAB.EVENT);
        setLabReportFilter(undefined);
        setInvestigationFilter(filter);
    };

    const performLabReportSearch = async (filter: LabReportFilter) => {
        const labReportsResponse = await findLabReports({
            variables: {
                filter: filter,
                page: {
                    pageNumber: currentPage - 1,
                    pageSize: PAGE_SIZE,
                    ...sort
                }
            }
        });
        const labReportsResults = labReportsResponse.data?.findLabReportsByFilter;
        updatePaginationDetails(labReportsResults);
        setLastSearchType(SEARCH_TYPE.LAB_REPORT);
        setActiveTab(ACTIVE_TAB.EVENT);
        setInvestigationFilter(undefined);
        setLabReportFilter(filter);
    };

    function isEmpty(obj: any) {
        for (const key in obj) {
            if (obj[key] !== undefined && obj[key] != '' && key !== 'recordStatus') return false;
        }
        return true;
    }

    const handlePersonChipClosed = (filter: PersonFilter): void => {
        setPersonFilter(filter);
        if (isEmpty(filter)) {
            handleClearAll();
        } else {
            handleSubmit(filter, SEARCH_TYPE.PERSON);
        }
    };

    const handleInvestigationChipClosed = (filter: InvestigationFilter): void => {
        setInvestigationFilter(filter);
        if (isEmpty(filter)) {
            handleClearAll();
        } else {
            handleSubmit(filter, SEARCH_TYPE.INVESTIGATION);
        }
    };
    const handleLabReportChipClosed = (filter: LabReportFilter): void => {
        setLabReportFilter(filter);
        if (isEmpty(filter)) {
            handleClearAll();
        } else {
            handleSubmit(filter, SEARCH_TYPE.LAB_REPORT);
        }
    };

    // handles submit from Person Search and Event Search,
    // it simply encrypts the filter object and sets it as the query parameter

    const handleSubmit = async (filter: PersonFilter | InvestigationFilter | LabReportFilter, type: SEARCH_TYPE) => {
        if (!isEmpty(filter)) {
            EncryptionControllerService.encrypt({
                requestBody: filter
            })
                .then((response) => response.value ?? '')
                .then((criteria) => `?q=${encodeURIComponent(criteria)}&type=${type}`)
                .then((search) => {
                    navigate({
                        pathname: `/advanced-search/${activeTab}`,
                        search
                    });
                    setCurrentPage(1);
                    setSubmitted(false);
                });
        } else {
            setSubmitted(true);
        }
    };

    const handleClearAll = () => {
        setPersonFilter({ recordStatus: [RecordStatus.Active] });
        setInvestigationFilter(undefined);
        setLabReportFilter(undefined);
        setSubmitted(false);
        setLastSearchType(undefined);
        navigate(`/advanced-search${activeTab ? '/' + activeTab : ''}`);
    };

    function handleAddNewPatientClick(): void {
        setShowAddNewDropDown(false);
        const criteria = searchParams.get('q');

        if (criteria) {
            navigate('/add-patient', { state: { criteria } });
        } else {
            navigate('/add-patient');
        }
    }

    function handleAddNewLabReportClick(): void {
        setShowAddNewDropDown(false);
        window.location.href = `/nbs/MyTaskList1.do?ContextAction=AddLabDataEntry`;
    }

    const handlePagination = (page: number) => {
        setCurrentPage(page);
    };

    /**
     * Update the pagination variables based on api response
     * @param {FilterQueryResponse} result - response object og patient, investigation or lab results api
     */
    const updatePaginationDetails = (
        result:
            | FindPatientsByFilterQuery['findPatientsByFilter']
            | FindInvestigationsByFilterQuery['findInvestigationsByFilter']
            | FindLabReportsByFilterQuery['findLabReportsByFilter']
            | undefined
    ) => {
        const content = result?.content;
        const total = result?.total || 0;
        const startCount = content?.length ? 1 + PAGE_SIZE * (currentPage - 1) : 0;
        const endCount = content?.length ? startCount + content?.length - 1 : 0;
        setResultStartCount(startCount);
        setResultEndCount(endCount);
        setResultTotal(total);
        skipTo('resultsCount');
        focusedTarget('resultsCount');
    };

    function isLoading() {
        return patientDataLoading || investigationLoading || labReportLoading;
    }

    function isNoResultsFound() {
        switch (lastSearchType) {
            case SEARCH_TYPE.PERSON:
                return !patientData?.content || patientData?.content?.length === 0;
            case SEARCH_TYPE.INVESTIGATION:
                return !investigationData?.content || investigationData?.content?.length === 0;
            case SEARCH_TYPE.LAB_REPORT:
                return !labReportData?.content || labReportData?.content?.length === 0;
        }
    }

    function isError(): boolean {
        return !!(patientDataError || investigationError || labReportError);
    }

    function isEmptyFilter(): boolean {
        return isEmpty(personFilter) && isEmpty(investigationFilter) && isEmpty(labReportFilter);
    }

    const doSubmit = (data: PersonFilter) => {
        handleSubmit(externalize(data), SEARCH_TYPE.PERSON);
    };

    return (
        <SearchCriteriaProvider>
            <div
                className={`padding-0 search-page-height bg-light advanced-search ${
                    (investigationData?.content && investigationData.content.length > 7) ||
                    (labReportData?.content && labReportData.content.length > 7) ||
                    (patientData?.content && patientData?.content.length > 7)
                        ? 'full-height'
                        : 'partial-height'
                }`}>
                <Grid row className="page-title-bar bg-white">
                    <div className="width-full text-bold flex-row display-flex flex-align-center flex-justify">
                        <h1 className="advanced-search-title margin-0">Search</h1>
                        <div className="button-group">
                            <Button
                                disabled={!lastSearchType}
                                className="padding-x-3 add-patient-button"
                                type="button"
                                icon={
                                    <NBSIcon name={lastSearchType ? 'down-arrow-blue' : 'down-arrow-white'} size="s" />
                                }
                                labelPosition="left"
                                onClick={() => setShowAddNewDropDown(!showAddNewDropDown)}
                                outline>
                                Add new
                            </Button>

                            {showAddNewDropDown && (
                                <ul ref={addPatiendRef} id="basic-nav-section-one" className="usa-nav__submenu">
                                    <li className="usa-nav__submenu-item">
                                        <Button onClick={handleAddNewPatientClick} type={'button'} unstyled>
                                            Add new patient
                                        </Button>
                                    </li>
                                    <li className="usa-nav__submenu-item">
                                        <Button onClick={handleAddNewLabReportClick} type={'button'} unstyled>
                                            Add new lab report
                                        </Button>
                                    </li>
                                </ul>
                            )}
                        </div>
                    </div>
                </Grid>
                <Grid row className="search-page-height">
                    <Grid col={3} className="bg-white border-right border-base-light">
                        <h2 className="padding-x-2 text-medium margin-0 refine-text">Refine your search</h2>
                        <div className="search-tabs">
                            <TabNavigation className="margin-top-1 margin-left-2">
                                <TabNavigationEntry path="/advanced-search/person">Patient search</TabNavigationEntry>
                                <TabNavigationEntry path="/advanced-search/event">Event search</TabNavigationEntry>
                            </TabNavigation>
                        </div>
                        {activeTab === ACTIVE_TAB.PERSON ? (
                            <PatientSearch
                                handleSubmission={doSubmit}
                                personFilter={personFilter}
                                clearAll={handleClearAll}
                            />
                        ) : (
                            <EventSearch
                                onSearch={handleSubmit}
                                investigationFilter={investigationFilter}
                                labReportFilter={labReportFilter}
                                clearAll={handleClearAll}
                            />
                        )}
                    </Grid>
                    <Grid col={9} className="scrollable-results">
                        <Grid
                            row
                            className="flex-align-center flex-justify margin-top-4 margin-x-4 border-bottom padding-bottom-1 border-base-lighter">
                            {submitted && !isError() ? (
                                <div
                                    tabIndex={0}
                                    id="resultsCount"
                                    aria-label={resultTotal + ' Results have been found'}
                                    className="margin-0 font-sans-md margin-top-05 text-normal grid-row results-for"
                                    style={{ maxWidth: '55%' }}>
                                    <h2 className="advanced-search-results-title">{resultTotal} Results for:</h2>
                                    <AdvancedSearchChips
                                        lastSearchType={lastSearchType}
                                        personFilter={personFilter}
                                        onPersonFilterChange={handlePersonChipClosed}
                                        investigationFilter={investigationFilter}
                                        onInvestigationFilterChange={handleInvestigationChipClosed}
                                        labReportFilter={labReportFilter}
                                        onLabReportFilterChange={handleLabReportChipClosed}
                                    />
                                </div>
                            ) : (
                                <p id="perform-search" className="margin-0 font-sans-md margin-top-05 text-normal">
                                    Perform a search
                                </p>
                            )}
                            <div style={{ display: 'flex', alignItems: 'center' }}>
                                <div className="button-group">
                                    {lastSearchType && !isNoResultsFound() && !isError() && !isLoading() && (
                                        <Button
                                            disabled={
                                                (!investigationData?.content ||
                                                    investigationData?.content?.length === 0) &&
                                                (!labReportData?.content || labReportData?.content?.length === 0) &&
                                                (!patientData?.content || patientData?.content?.length === 0)
                                            }
                                            className="padding-x-3"
                                            type={'button'}
                                            onClick={() => setShowSorting(!showSorting)}
                                            outline
                                            labelPosition="left"
                                            icon={
                                                <NBSIcon
                                                    name={
                                                        (!investigationData?.content ||
                                                            investigationData?.content?.length === 0) &&
                                                        (!labReportData?.content ||
                                                            labReportData?.content?.length === 0) &&
                                                        (!patientData?.content || patientData?.content?.length === 0)
                                                            ? 'down-arrow-white'
                                                            : 'down-arrow-blue'
                                                    }
                                                />
                                            }>
                                            Sort by
                                        </Button>
                                    )}
                                    {showSorting && (
                                        <ul ref={wrapperRef} id="basic-nav-section-one" className="usa-nav__submenu">
                                            <li className="usa-nav__submenu-item">
                                                <Button
                                                    onClick={() => {
                                                        setSort({
                                                            sortField: SortField.Relevance
                                                        });
                                                        setShowSorting(false);
                                                    }}
                                                    type={'button'}
                                                    unstyled>
                                                    Closest match
                                                </Button>
                                            </li>
                                            <li className="usa-nav__submenu-item">
                                                <Button
                                                    onClick={() => {
                                                        setSort({
                                                            sortDirection: SortDirection.Asc,
                                                            sortField: SortField.LastNm
                                                        });
                                                        setShowSorting(false);
                                                    }}
                                                    type={'button'}
                                                    outline={sort.sortDirection === SortDirection.Asc}
                                                    unstyled>
                                                    Patient name (A-Z)
                                                </Button>
                                            </li>
                                            <li className="usa-nav__submenu-item">
                                                <Button
                                                    onClick={() => {
                                                        setSort({
                                                            sortDirection: SortDirection.Desc,
                                                            sortField: SortField.LastNm
                                                        });
                                                        setShowSorting(false);
                                                    }}
                                                    type={'button'}
                                                    unstyled>
                                                    Patient name (Z-A)
                                                </Button>
                                            </li>
                                            <li className="usa-nav__submenu-item">
                                                <Button
                                                    onClick={() => {
                                                        setSort({
                                                            sortDirection: SortDirection.Asc,
                                                            sortField: SortField.BirthTime
                                                        });
                                                        setShowSorting(false);
                                                    }}
                                                    type={'button'}
                                                    unstyled>
                                                    Date of birth (Ascending)
                                                </Button>
                                            </li>
                                            <li className="usa-nav__submenu-item">
                                                <Button
                                                    onClick={() => {
                                                        setSort({
                                                            sortDirection: SortDirection.Desc,
                                                            sortField: SortField.BirthTime
                                                        });
                                                        setShowSorting(false);
                                                    }}
                                                    type={'button'}
                                                    unstyled>
                                                    Date of birth (Descending)
                                                </Button>
                                            </li>
                                        </ul>
                                    )}
                                </div>
                            </div>
                        </Grid>
                        {submitted && !!resultTotal && (
                            <Grid row className="padding-left-4 padding-right-4 flex-align-center flex-justify">
                                <p className="margin-0 font-sans-3xs margin-top-05 text-normal text-base">
                                    Showing {resultStartCount} - {resultEndCount} of {resultTotal}
                                </p>
                                <Pagination
                                    style={{ justifyContent: 'flex-end' }}
                                    totalPages={Math.ceil(resultTotal / 25)}
                                    currentPage={currentPage}
                                    pathname={'/advanced-search'}
                                    onClickNext={() => handlePagination(currentPage + 1)}
                                    onClickPrevious={() => handlePagination(currentPage - 1)}
                                    onClickPageNumber={(_, page) => handlePagination(page)}
                                />
                            </Grid>
                        )}
                        {isLoading() && (
                            <Grid row className="padding-5 flex-justify-center">
                                <span className="ds-c-spinner" role="status">
                                    <span className="ds-u-visibility--screen-reader">Loading</span>
                                </span>
                            </Grid>
                        )}
                        {!isLoading() && (
                            <>
                                {submitted && isEmptyFilter() && (
                                    <div className="margin-x-4 margin-y-2 flex-row grid-row flex-align-center flex-justify-center advanced-search-alert">
                                        <Alert
                                            type="error"
                                            // heading="You did not make a search"
                                            headingLevel="h4"
                                            className="width-full">
                                            <span className="display-flex flex-justify flex-align-center">
                                                You must enter at least one item to search
                                                <Icon.Close
                                                    onClick={() => setSubmitted(false)}
                                                    className="margin-left-05"
                                                    style={{ cursor: 'pointer' }}
                                                />
                                            </span>
                                        </Alert>
                                    </div>
                                )}
                                {!submitted && (
                                    <div
                                        className="margin-x-4 margin-y-2 flex-row grid-row flex-align-center text-normal flex-justify-center advanced-search-message"
                                        style={{
                                            background: 'white',
                                            border: '1px solid #DCDEE0',
                                            borderRadius: '5px',
                                            height: '147px'
                                        }}>
                                        Perform a search to see results
                                    </div>
                                )}
                            </>
                        )}
                        {!isError() && !isLoading() && isNoResultsFound() && (
                            <div
                                className="margin-x-4 margin-y-2 flex-row grid-row flex-align-center flex-justify-center advanced-search-message"
                                style={{
                                    background: 'white',
                                    border: '1px solid #DCDEE0',
                                    borderRadius: '5px',
                                    height: '147px'
                                }}>
                                <div className="text-center">
                                    <p>No results found.</p>
                                    {searchType === 'event' ? (
                                        <p>Try refining your search</p>
                                    ) : (
                                        <p>
                                            Try refining your search, or{' '}
                                            <a onClick={handleAddNewPatientClick} style={{ color: '#005EA2' }}>
                                                add a new patient
                                            </a>
                                        </p>
                                    )}
                                </div>
                            </div>
                        )}
                        {lastSearchType === SEARCH_TYPE.PERSON &&
                            patientData?.content &&
                            patientData.content.length > 0 && (
                                <PatientResults
                                    data={patientData.content}
                                    totalResults={patientData.total}
                                    handlePagination={handlePagination}
                                    currentPage={currentPage}
                                />
                            )}
                        {lastSearchType === SEARCH_TYPE.INVESTIGATION &&
                            investigationData?.content &&
                            investigationData?.content?.length > 0 && (
                                <InvestigationResults
                                    data={investigationData?.content as [Investigation]}
                                    totalResults={investigationData?.total}
                                    handlePagination={handlePagination}
                                    currentPage={currentPage}
                                />
                            )}
                        {lastSearchType === SEARCH_TYPE.LAB_REPORT &&
                            labReportData?.content &&
                            labReportData?.content?.length > 0 && (
                                <LabReportResults
                                    data={labReportData?.content as [LabReport]}
                                    totalResults={labReportData?.total}
                                    handlePagination={handlePagination}
                                    currentPage={currentPage}
                                />
                            )}
                    </Grid>
                </Grid>
            </div>
        </SearchCriteriaProvider>
    );
};
