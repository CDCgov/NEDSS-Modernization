import { Alert, Button, Grid, Icon } from '@trussworks/react-uswds';
import { externalize, internalize } from 'pages/patient/search';
import { useContext, useEffect, useRef, useState } from 'react';
import { useNavigate, useParams, useSearchParams } from 'react-router-dom';
import { Config } from '../../config';

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
} from '../../generated/graphql/schema';
import { EncryptionControllerService } from '../../generated/services/EncryptionControllerService';
import { UserContext } from '../../providers/UserContext';
import {
    downloadInvestigationSearchResultCsv,
    downloadInvestigationSearchResultPdf,
    downloadLabReportSearchResultCsv,
    downloadLabReportSearchResultPdf
} from '../../utils/ExportUtil';
import './AdvancedSearch.scss';
import { AdvancedSearchChips } from './components/chips/AdvancedSearchChips';
import { InvestigationResults } from './components/InvestigationResults';
import { LabReportResults } from './components/LabReportResults';
import { PatientResults } from './components/PatientResults';
import { EventSearch } from './components/eventSearch/EventSearch';
import { PatientSearch } from './components/patientSearch/PatientSearch';

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
    const NBS_URL = Config.nbsUrl;
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

    const handleActiveTab = (searchType: string) => {
        setActiveTab(searchType);
        handleClearAll();
        navigate(`/advanced-search/${searchType}`, { replace: true });
    };

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
        EncryptionControllerService.decryptUsingPost({
            encryptedString: queryParam,
            authorization: `Bearer ${state.getToken()}`
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

    const performPatientSearch = (filter: PersonFilter) => {
        findPatients({
            variables: {
                filter: filter,
                page: {
                    pageNumber: currentPage - 1,
                    pageSize: PAGE_SIZE,
                    ...sort
                }
            }
        });
        setLastSearchType(SEARCH_TYPE.PERSON);
        setActiveTab(ACTIVE_TAB.PERSON);

        const internalized = internalize(filter);

        setPersonFilter(internalized);
    };

    const performInvestigationSearch = (filter: InvestigationFilter) => {
        findInvestigations({
            variables: {
                filter: filter,
                page: {
                    pageNumber: currentPage - 1,
                    pageSize: PAGE_SIZE,
                    ...sort
                }
            }
        });
        setLastSearchType(SEARCH_TYPE.INVESTIGATION);
        setActiveTab(ACTIVE_TAB.EVENT);
        setLabReportFilter(undefined);
        setInvestigationFilter(filter);
    };

    const performLabReportSearch = (filter: LabReportFilter) => {
        findLabReports({
            variables: {
                filter: filter,
                page: {
                    pageNumber: currentPage - 1,
                    pageSize: PAGE_SIZE,
                    ...sort
                }
            }
        });
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
        let search = '';
        if (!isEmpty(filter)) {
            // send filter for encryption
            const encryptedFilter = await EncryptionControllerService.encryptUsingPost({
                authorization: `Bearer ${state.getToken()}`,
                object: filter
            });

            // URI encode encrypted filter
            search = `?q=${encodeURIComponent(encryptedFilter.value)}&type=${type}`;
            navigate({
                pathname: '/advanced-search',
                search
            });
            setCurrentPage(1);
            setSubmitted(false);
        } else {
            setSubmitted(true);
        }
    };

    // Generates a CSV of the results
    const handleExportClick = () => {
        const token = state.getToken();
        switch (lastSearchType) {
            case SEARCH_TYPE.INVESTIGATION:
                if (investigationFilter && token) {
                    downloadInvestigationSearchResultCsv(investigationFilter, token);
                }
                break;
            case SEARCH_TYPE.LAB_REPORT:
                if (labReportFilter && token) {
                    downloadLabReportSearchResultCsv(labReportFilter, token);
                }
                break;
        }
    };

    // Generates a PDF of the results
    const handlePrintClick = () => {
        const token = state.getToken();
        switch (lastSearchType) {
            case SEARCH_TYPE.INVESTIGATION:
                if (investigationFilter && token) {
                    downloadInvestigationSearchResultPdf(investigationFilter, token);
                }
                break;
            case SEARCH_TYPE.LAB_REPORT:
                if (labReportFilter && token) {
                    downloadLabReportSearchResultPdf(labReportFilter, token);
                }
                break;
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
        window.location.href = `${NBS_URL}/MyTaskList1.do?ContextAction=AddLabDataEntry`;
    }

    const handlePagination = (page: number) => {
        setCurrentPage(page);
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
                        Search
                        <div className="button-group">
                            <Button
                                disabled={!lastSearchType}
                                className="padding-x-3 add-patient-button"
                                type={'button'}
                                onClick={() => setShowAddNewDropDown(!showAddNewDropDown)}>
                                Add new
                                <img src={'/icons/down-arrow-white.svg'} />
                            </Button>
                            {showAddNewDropDown && (
                                <ul
                                    ref={addPatiendRef}
                                    id="basic-nav-section-one"
                                    className="usa-nav__submenu add-patient-menu">
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
                        <div className="left-searchbar">
                            <h3 className="padding-x-2 text-medium margin-0 refine-text">Refine your search</h3>
                            <div
                                className="grid-row flex-align-center"
                                style={{ borderBottom: '1.5px solid lightgray' }}>
                                <h6
                                    className={`${
                                        activeTab === ACTIVE_TAB.PERSON && 'active'
                                    } text-normal type font-sans-md padding-bottom-1 margin-x-2 cursor-pointer margin-top-2 margin-bottom-0`}
                                    onClick={() => handleActiveTab(ACTIVE_TAB.PERSON)}>
                                    Patient search
                                </h6>
                                <h6
                                    className={`${
                                        activeTab === ACTIVE_TAB.EVENT && 'active'
                                    } padding-bottom-1 type text-normal font-sans-md cursor-pointer margin-top-2 margin-bottom-0`}
                                    onClick={() => handleActiveTab(ACTIVE_TAB.EVENT)}>
                                    Event search
                                </h6>
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
                                />
                            )}
                        </div>
                    </Grid>
                    <Grid col={9} className="scrollable-results">
                        <Grid
                            row
                            className="flex-align-center flex-justify margin-top-4 margin-x-4 border-bottom padding-bottom-1 border-base-lighter">
                            {submitted && !isError() ? (
                                <div
                                    className="margin-0 font-sans-md margin-top-05 text-normal grid-row"
                                    style={{ maxWidth: '55%' }}>
                                    <strong className="margin-right-1">
                                        {lastSearchType === SEARCH_TYPE.PERSON && patientData?.total}
                                        {lastSearchType === SEARCH_TYPE.INVESTIGATION && investigationData?.total}
                                        {lastSearchType === SEARCH_TYPE.LAB_REPORT && labReportData?.total}
                                    </strong>{' '}
                                    Results for
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
                                <p className="margin-0 font-sans-md margin-top-05 text-normal">Perform a search</p>
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
                                            outline>
                                            Sort by
                                            <img
                                                style={{ marginLeft: '5px' }}
                                                src={
                                                    (!investigationData?.content ||
                                                        investigationData?.content?.length === 0) &&
                                                    (!labReportData?.content || labReportData?.content?.length === 0) &&
                                                    (!patientData?.content || patientData?.content?.length === 0)
                                                        ? '/icons/down-arrow-white.svg'
                                                        : '/icons/down-arrow-blue.svg'
                                                }
                                            />
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
                                <Button
                                    disabled={
                                        (!investigationData?.content || investigationData?.content?.length === 0) &&
                                        (!labReportData?.content || labReportData?.content?.length === 0) &&
                                        (!patientData?.content || patientData?.content?.length === 0)
                                    }
                                    className="width-full margin-top-0"
                                    style={{ display: 'none' }}
                                    type={'button'}
                                    onClick={handleExportClick}
                                    outline>
                                    Export
                                </Button>
                                <Button
                                    disabled={
                                        (!investigationData?.content || investigationData?.content?.length === 0) &&
                                        (!labReportData?.content || labReportData?.content?.length === 0) &&
                                        (!patientData?.content || patientData?.content?.length === 0)
                                    }
                                    className="width-full margin-top-0"
                                    style={{ display: 'none' }}
                                    type={'button'}
                                    onClick={handlePrintClick}
                                    outline>
                                    Print
                                </Button>
                            </div>
                        </Grid>
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
                                            border: '1px solid #DFE1E2',
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
                                    border: '1px solid #DFE1E2',
                                    borderRadius: '5px',
                                    height: '147px'
                                }}>
                                <div className="text-center">
                                    <p>No results found.</p>
                                    <p>
                                        Try refining your search, or{' '}
                                        <a onClick={handleAddNewPatientClick} style={{ color: '#005EA2' }}>
                                            add a new patient
                                        </a>
                                    </p>
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
