import { Alert, Button, Grid } from '@trussworks/react-uswds';
import { useContext, useEffect, useRef, useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { Config } from '../../config';

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
import { convertCamelCase } from '../../utils/util';
import './AdvancedSearch.scss';
import Chip from './components/Chip';
import { EventSearch } from './components/eventSearch/EventSearch';
import { InvestigationResults } from './components/InvestigationResults';
import { LabReportResults } from './components/LabReportResults';
import { PatientResults } from './components/PatientResults';
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
    // shared variables
    const NBS_URL = Config.nbsUrl;
    const { state } = useContext(UserContext);
    const navigate = useNavigate();
    const [activeTab, setActiveTab] = useState<'person' | 'event'>('person');
    const [lastSearchType, setLastSearchType] = useState<SEARCH_TYPE | undefined>();
    const [validSearch, setValidSearch] = useState<boolean>(true);
    const [searchParams] = useSearchParams();
    const [submitted, setSubmitted] = useState(false);
    const wrapperRef = useRef<any>(null);
    const [loading, setLoading] = useState<boolean>(false);
    const [sort, setSort] = useState<{ sortDirection: SortDirection; sortField: SortField }>({
        sortDirection: SortDirection.Asc,
        sortField: SortField.LastNm
    });
    const PAGE_SIZE = 25;

    // patient search variables
    const [personFilter, setPersonFilter] = useState<PersonFilter>({ recordStatus: [RecordStatus.Active] });
    const addPatiendRef = useRef<any>(null);
    const [resultsChip, setResultsChip] = useState<{ name: string; value: string }[]>([]);
    const [showSorting, setShowSorting] = useState<boolean>(false);
    const [currentPage, setCurrentPage] = useState(1);
    const [showAddNewDropDown, setShowAddNewDropDown] = useState<boolean>(false);
    const [patientData, setPatientData] = useState<FindPatientsByFilterQuery['findPatientsByFilter']>();
    const [findPatients] = useFindPatientsByFilterLazyQuery({
        onCompleted: handlePatientSearchResults
    });

    // investigation search variables
    const [investigationData, setInvestigationData] =
        useState<FindInvestigationsByFilterQuery['findInvestigationsByFilter']>();
    const [investigationFilter, setInvestigationFilter] = useState<InvestigationFilter>();
    const [findInvestigations] = useFindInvestigationsByFilterLazyQuery({
        onCompleted: handleInvestigationSearchResults
    });

    // lab report search variables
    const [labReportData, setLabReportData] = useState<FindLabReportsByFilterQuery['findLabReportsByFilter']>();
    const [labReportFilter, setLabReportFilter] = useState<LabReportFilter>();
    const [findLabReports] = useFindLabReportsByFilterLazyQuery({
        onCompleted: handleLabReportSearchResults
    });

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
        if (!queryParam || !state.isLoggedIn) {
            // no query parameters specified or user is not logged in
            setActiveTab('person');
            setResultsChip([]);
            setValidSearch(false);
            setLoading(false);
            return;
        }

        // decrypt the filter parameter
        EncryptionControllerService.decryptUsingPost({
            encryptedString: queryParam,
            authorization: `Bearer ${state.getToken()}`
        }).then(async (filter: any) => {
            if (isEmpty(filter)) {
                // empty filter, clear content
                setResultsChip([]);
                setValidSearch(false);
                setSubmitted(true);
            }
            // perform the search based on the 'type' parameter
            switch (type) {
                case SEARCH_TYPE.PERSON:
                    performPatientSearch(filter);
                    break;
                case SEARCH_TYPE.INVESTIGATION:
                    performInvestigationSearch(filter);
                    break;
                case SEARCH_TYPE.LAB_REPORT:
                    performLabReportSearch(filter);
                    break;
                default:
                    performPatientSearch(filter);
            }
        });
    }, [searchParams, state.isLoggedIn, sort, currentPage]);

    const handleEventTags = (filter: any) => {
        const chips: any = [];
        if (filter) {
            Object.entries(filter as any).map((re: any) => {
                if (Array.isArray(re[1])) {
                    re[1].map((item: any) => {
                        chips.push({ name: convertCamelCase(re[0]), value: item });
                    });
                } else if (re[1] && typeof re[1] === 'object' && re[1].constructor === Object) {
                    Object.entries(re[1] as any).map((obj: any) => {
                        if (obj[0] !== 'includeUnassigned') {
                            if (obj[0] === 'statusList') {
                                chips.push({ name: convertCamelCase(re[0]), value: obj[1] });
                            } else {
                                chips.push({ name: convertCamelCase(obj[0]), value: obj[1] });
                            }
                        }
                    });
                } else {
                    chips.push({ name: convertCamelCase(re[0]), value: re[1] });
                }
            });
            setResultsChip(chips);
        }
    };

    const handlePersonTags = (filter: any) => {
        const chips: any = [];
        if (filter) {
            Object.entries(filter as any).map((re: any) => {
                if (re[0] !== 'identification') {
                    let name = re[0];
                    switch (re[0]) {
                        case 'lastName':
                            name = 'last';
                            break;
                        case 'firstName':
                            name = 'first';
                            break;
                        case 'gender':
                            name = 'sex';
                            break;
                        case 'dateOfBirth':
                            name = 'dob';
                            break;
                    }
                    chips.push({
                        name: name,
                        value: re[1]
                    });
                }
                if (re[0] === 'identification') {
                    chips.push(
                        {
                            name: 'ID Type',
                            value: re[1]?.identificationType
                        },
                        {
                            name: 'ID Number',
                            value: re[1]?.identificationNumber
                        }
                    );
                }
            });
        }
        setResultsChip(chips);
    };

    const performPatientSearch = (filter: PersonFilter) => {
        setLoading(true);
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
        handlePersonTags(filter);
        setPersonFilter(filter);
    };

    const performInvestigationSearch = (filter: InvestigationFilter) => {
        setLoading(true);
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
        handleEventTags(filter);
        setLabReportFilter(undefined);
        setInvestigationFilter(filter);
    };

    const performLabReportSearch = (filter: LabReportFilter) => {
        setLoading(true);
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
        handleEventTags(filter);
        setInvestigationFilter(undefined);
        setLabReportFilter(filter);
    };

    function handlePatientSearchResults(data: FindPatientsByFilterQuery) {
        // Using a timeout fixes an issue where the apollo cache fails to update the data
        setTimeout(() => {
            setPatientData(data.findPatientsByFilter);
        }, 10);
        setLoading(false);
        setValidSearch(true);
    }

    function handleInvestigationSearchResults(data: FindInvestigationsByFilterQuery) {
        // Using a timeout fixes an issue where the apollo cache fails to update the data
        setTimeout(() => {
            setInvestigationData(data.findInvestigationsByFilter);
        }, 10);
        setLoading(false);
        setValidSearch(true);
    }

    function handleLabReportSearchResults(data: FindLabReportsByFilterQuery) {
        // Using a timeout fixes an issue where the apollo cache fails to update the data
        setTimeout(() => {
            setLabReportData(data.findLabReportsByFilter);
        }, 10);
        setLoading(false);
        setValidSearch(true);
    }

    function isEmpty(obj: any) {
        for (const key in obj) {
            if (obj[key] !== undefined && obj[key] != '' && key !== 'recordStatus') return false;
        }
        return true;
    }

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
            setValidSearch(true);

            // URI encode encrypted filter
            search = `?q=${encodeURIComponent(encryptedFilter.value)}&type=${type}`;
            navigate({
                pathname: '/advanced-search',
                search
            });
            setCurrentPage(1);
            setSubmitted(false);
        } else {
            setLoading(false);
            setValidSearch(false);
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
        setPatientData(undefined);
        setPersonFilter({ recordStatus: [RecordStatus.Active] });
        setInvestigationData(undefined);
        setInvestigationFilter({});
        setLabReportData(undefined);
        setLabReportFilter({});
        setSubmitted(false);
        setValidSearch(false);
        setLastSearchType(undefined);
        navigate('/advanced-search');
    };

    const handleChipClose = (name: string, value: string) => {
        switch (lastSearchType) {
            case SEARCH_TYPE.PERSON:
                handlePersonChipClose(name, value);
                return;
            case SEARCH_TYPE.INVESTIGATION:
                handleInvestigationChipClose(name, value);
                return;
            case SEARCH_TYPE.LAB_REPORT:
                handleLabReportChipClose(name, value);
                return;
        }
    };

    const handleLabReportChipClose = (name: string, value: string) => {
        let tempLabReportFilter = labReportFilter as LabReportFilter;
        // remove the closed chip from the display
        const newChips = resultsChip.filter((c) => c.name != name || c.value != value);
        setResultsChip(newChips);

        // if the last chip was removed, reset search
        if (newChips.length === 0) {
            handleClearAll();
            return;
        }

        // remove the filter criteria associated with closed chip and resubmit search
        switch (name.trim()) {
            case 'Program Areas':
                tempLabReportFilter = {
                    ...tempLabReportFilter,
                    programAreas: tempLabReportFilter?.programAreas?.filter((pa) => pa !== value)
                };
                break;
            case 'Jurisdictions':
                tempLabReportFilter = {
                    ...tempLabReportFilter,
                    jurisdictions: tempLabReportFilter?.jurisdictions?.filter((j) => j !== value)
                };
                break;
            case 'Pregnancy Status':
                tempLabReportFilter = {
                    ...tempLabReportFilter,
                    pregnancyStatus: undefined
                };
                break;
            case 'Event Id Type':
                tempLabReportFilter = {
                    ...tempLabReportFilter,
                    eventIdType: undefined
                };
                break;
            case 'Event Id':
                tempLabReportFilter = {
                    ...tempLabReportFilter,
                    eventId: undefined
                };
                break;
            case 'Event Date Type' || 'From' || 'To':
                tempLabReportFilter = {
                    ...tempLabReportFilter,
                    eventDateSearch: undefined
                };
                break;
            case 'Created By':
                tempLabReportFilter = {
                    ...tempLabReportFilter,
                    createdBy: undefined
                };
                break;
            case 'Last Updated By':
                tempLabReportFilter = {
                    ...tempLabReportFilter,
                    lastUpdatedBy: undefined
                };
                break;
            case 'Entity Type' || 'Id':
                tempLabReportFilter = {
                    ...tempLabReportFilter,
                    providerSearch: undefined
                };
                break;
            case 'Resulted Test':
                tempLabReportFilter = {
                    ...tempLabReportFilter,
                    resultedTest: undefined
                };
                break;
            case 'Coded Result':
                tempLabReportFilter = {
                    ...tempLabReportFilter,
                    codedResult: undefined
                };
                break;
        }
        handleSubmit(tempLabReportFilter, SEARCH_TYPE.LAB_REPORT);
    };

    const handleInvestigationChipClose = (name: string, value: string) => {
        let tempInvestigationFilter = investigationFilter as InvestigationFilter;
        // remove the closed chip from the display
        const newChips = resultsChip.filter((c) => c.name != name || c.value != value);
        setResultsChip(newChips);

        // if the last chip was removed, reset search
        if (newChips.length === 0) {
            handleClearAll();
            return;
        }

        // remove the filter criteria associated with closed chip and resubmit search
        switch (name) {
            case 'Conditions':
                tempInvestigationFilter = {
                    ...tempInvestigationFilter,
                    conditions: tempInvestigationFilter?.conditions?.filter((c) => c !== value)
                };
                break;
            case 'Program Areas':
                tempInvestigationFilter = {
                    ...tempInvestigationFilter,
                    programAreas: tempInvestigationFilter?.programAreas?.filter((pa) => pa !== value)
                };
                break;
            case 'Jurisdictions':
                tempInvestigationFilter = {
                    ...tempInvestigationFilter,
                    jurisdictions: tempInvestigationFilter?.jurisdictions?.filter((j) => j !== value)
                };
                break;
            case 'Investigation Status':
                tempInvestigationFilter = {
                    ...tempInvestigationFilter,
                    investigationStatus: undefined
                };
                break;
            case 'Outbreak Names':
                tempInvestigationFilter = {
                    ...tempInvestigationFilter,
                    outbreakNames: undefined
                };
                break;
            case 'Case Statuses':
                tempInvestigationFilter = {
                    ...tempInvestigationFilter,
                    caseStatuses: undefined
                };
                break;
            case 'Processing Statuses':
                tempInvestigationFilter = {
                    ...tempInvestigationFilter,
                    processingStatuses: undefined
                };
                break;
            case 'Notification Statuses':
                tempInvestigationFilter = {
                    ...tempInvestigationFilter,
                    notificationStatuses: undefined
                };
                break;
            case 'Pregnancy Status':
                tempInvestigationFilter = {
                    ...tempInvestigationFilter,
                    pregnancyStatus: undefined
                };
                break;
            case 'Event Id Type':
                tempInvestigationFilter = {
                    ...tempInvestigationFilter,
                    eventIdType: undefined
                };
                break;
            case 'Event Id':
                tempInvestigationFilter = {
                    ...tempInvestigationFilter,
                    eventId: undefined
                };
                break;
            case 'Event Date Type' || 'From' || 'To':
                tempInvestigationFilter = {
                    ...tempInvestigationFilter,
                    eventDateSearch: undefined
                };
                break;
            case 'Created By':
                tempInvestigationFilter = {
                    ...tempInvestigationFilter,
                    createdBy: undefined
                };
                break;
            case 'Last Updated By':
                tempInvestigationFilter = {
                    ...tempInvestigationFilter,
                    lastUpdatedBy: undefined
                };
                break;
            case 'Entity Type' || 'Id':
                tempInvestigationFilter = {
                    ...tempInvestigationFilter,
                    providerFacilitySearch: undefined
                };
                break;
        }
        handleSubmit(tempInvestigationFilter, SEARCH_TYPE.INVESTIGATION);
    };

    const handlePersonChipClose = (name: string, value: string) => {
        let tempPersonFilter = personFilter as PersonFilter;
        // remove the closed chip from the display
        let newChips = resultsChip.filter((c) => c.name != name || c.value != value);

        // ID Number and ID Type are separate chips but invalid if not together
        if (name === 'ID Number' || name === 'ID Type') {
            newChips = newChips.filter((c) => c.name != 'ID Number' && c.name != 'ID Type');
        }
        setResultsChip(newChips);

        // if the last chip was removed, reset search
        if (newChips.length === 0) {
            handleClearAll();
            return;
        }

        // remove the filter criteria associated with closed chip and resubmit search
        if (personFilter) {
            switch (name) {
                case 'last':
                    tempPersonFilter = { ...tempPersonFilter, lastName: undefined };
                    break;
                case 'first':
                    tempPersonFilter = { ...tempPersonFilter, firstName: undefined };
                    break;
                case 'sex':
                    tempPersonFilter = { ...tempPersonFilter, gender: undefined };
                    break;
                case 'dob':
                    tempPersonFilter = { ...tempPersonFilter, dateOfBirth: undefined };
                    break;
                case 'address':
                    tempPersonFilter = { ...tempPersonFilter, address: undefined };
                    break;
                case 'city':
                    tempPersonFilter = { ...tempPersonFilter, city: undefined };
                    break;
                case 'state':
                    tempPersonFilter = { ...tempPersonFilter, state: undefined };
                    break;
                case 'zip':
                    tempPersonFilter = { ...tempPersonFilter, zip: undefined };
                    break;
                case 'phoneNumber':
                    tempPersonFilter = { ...tempPersonFilter, phoneNumber: undefined };
                    break;
                case 'email':
                    tempPersonFilter = { ...tempPersonFilter, email: undefined };
                    break;
                case 'race':
                    tempPersonFilter = { ...tempPersonFilter, race: undefined };
                    break;
                case 'ethnicity':
                    tempPersonFilter = { ...tempPersonFilter, ethnicity: undefined };
                    break;
                case 'ID Number':
                    tempPersonFilter = { ...tempPersonFilter, identification: undefined };
                    break;
                case 'ID Type':
                    tempPersonFilter = { ...tempPersonFilter, identification: undefined };
                    break;
            }
            handleSubmit(tempPersonFilter, SEARCH_TYPE.PERSON);
        }
    };

    function handleAddNewPatientClick(): void {
        setShowAddNewDropDown(false);
        navigate('/add-patient');
        // RedirectControllerService.preparePatientDetailsUsingGet({ authorization: 'Bearer ' + state.getToken() }).then(
        //     () => {
        //         window.location.href = `${NBS_URL}/PatientSearchResults1.do?ContextAction=Add`;
        //     }
        // );
    }

    function handleAddNewLabReportClick(): void {
        setShowAddNewDropDown(false);
        window.location.href = `${NBS_URL}/MyTaskList1.do?ContextAction=AddLabDataEntry`;
    }

    const handlePagination = (page: number) => {
        setCurrentPage(page);
    };

    return (
        <div
            className={`padding-0 search-page-height bg-light advanced-search ${
                (investigationData?.content && investigationData.content.length > 7) ||
                (labReportData?.content && labReportData.content.length > 7) ||
                (patientData?.content && patientData.content.length > 7)
                    ? 'full-height'
                    : 'partial-height'
            }`}>
            <Grid row className="page-title-bar bg-white">
                <div className="width-full text-bold flex-row display-flex flex-align-center flex-justify">
                    Search
                    <div className="button-group">
                        <Button
                            disabled={
                                !validSearch &&
                                (!patientData?.content || patientData.content.length === 0) &&
                                (!labReportData?.content || labReportData.total === 0) &&
                                (!investigationData?.content || investigationData.total === 0)
                            }
                            className="padding-x-3 add-patient-button"
                            type={'button'}
                            onClick={() => setShowAddNewDropDown(!showAddNewDropDown)}>
                            Add new
                            <img src={'down-arrow-white.svg'} />
                        </Button>
                        {showAddNewDropDown && (
                            <ul
                                ref={addPatiendRef}
                                id="basic-nav-section-one"
                                className="usa-nav__submenu add-patient-menu">
                                <li className="usa-nav__submenu-item">
                                    <Button onClick={handleAddNewPatientClick} type={'button'} unstyled>
                                        Add New Patient
                                    </Button>
                                </li>
                                <li className="usa-nav__submenu-item">
                                    <Button onClick={handleAddNewLabReportClick} type={'button'} unstyled>
                                        Add New Lab Report
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
                        <div className="grid-row flex-align-center" style={{ borderBottom: '1.5px solid lightgray' }}>
                            <h6
                                className={`${
                                    activeTab === ACTIVE_TAB.PERSON && 'active'
                                } text-normal type margin-y-3 font-sans-md padding-bottom-1 margin-x-2 cursor-pointer margin-top-2 margin-bottom-0`}
                                onClick={() => setActiveTab(ACTIVE_TAB.PERSON)}>
                                Patient search
                            </h6>
                            <h6
                                className={`${
                                    activeTab === ACTIVE_TAB.EVENT && 'active'
                                } padding-bottom-1 type text-normal margin-y-3 font-sans-md cursor-pointer margin-top-2 margin-bottom-0`}
                                onClick={() => setActiveTab(ACTIVE_TAB.EVENT)}>
                                Event search
                            </h6>
                        </div>
                        {activeTab === ACTIVE_TAB.PERSON ? (
                            <PatientSearch
                                handleSubmission={(data: PersonFilter) => {
                                    handleSubmit(data, SEARCH_TYPE.PERSON);
                                }}
                                data={personFilter}
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
                        {validSearch ? (
                            <div
                                className="margin-0 font-sans-md margin-top-05 text-normal grid-row"
                                style={{ maxWidth: '55%' }}>
                                <strong className="margin-right-1">
                                    {lastSearchType === SEARCH_TYPE.PERSON && patientData?.total}
                                    {lastSearchType === SEARCH_TYPE.INVESTIGATION && investigationData?.total}
                                    {lastSearchType === SEARCH_TYPE.LAB_REPORT && labReportData?.total}
                                </strong>{' '}
                                Results for
                                {resultsChip.map(
                                    (re, index) =>
                                        re.value && (
                                            <Chip
                                                key={index}
                                                name={re.name}
                                                value={re.value}
                                                handleClose={handleChipClose}
                                            />
                                        )
                                )}
                            </div>
                        ) : (
                            <p className="margin-0 font-sans-md margin-top-05 text-normal">Perform a search</p>
                        )}
                        <div style={{ display: 'flex', alignItems: 'center' }}>
                            <div className="button-group">
                                <Button
                                    disabled={
                                        (!investigationData?.content || investigationData?.content?.length === 0) &&
                                        (!labReportData?.content || labReportData?.content?.length === 0) &&
                                        (!patientData?.content || patientData?.content?.length === 0)
                                    }
                                    className="padding-x-3 margin-top-0"
                                    type={'button'}
                                    onClick={() => setShowSorting(!showSorting)}
                                    outline>
                                    Sort by
                                    <img
                                        style={{ marginLeft: '5px' }}
                                        src={
                                            (!investigationData?.content || investigationData?.content?.length === 0) &&
                                            (!labReportData?.content || labReportData?.content?.length === 0) &&
                                            (!patientData?.content || patientData?.content?.length === 0)
                                                ? 'down-arrow-white.svg'
                                                : 'down-arrow-blue.svg'
                                        }
                                    />
                                </Button>
                                {showSorting && (
                                    <ul ref={wrapperRef} id="basic-nav-section-one" className="usa-nav__submenu">
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
                                type={'button'}
                                onClick={handlePrintClick}
                                outline>
                                Print
                            </Button>
                        </div>
                    </Grid>
                    {!validSearch && !loading && (
                        <>
                            {submitted && (
                                <div className="margin-x-4 margin-y-2 flex-row grid-row flex-align-center flex-justify-center">
                                    <Alert
                                        type="error"
                                        // heading="You did not make a search"
                                        headingLevel="h4"
                                        className="width-full">
                                        <>You must enter at least one item to search</>
                                    </Alert>
                                </div>
                            )}
                            <div
                                className="margin-x-4 margin-y-2 flex-row grid-row flex-align-center flex-justify-center advanced-search-message"
                                style={{
                                    background: 'white',
                                    border: '1px solid #DFE1E2',
                                    borderRadius: '5px',
                                    height: '147px'
                                }}>
                                Perform a search to see results
                            </div>
                        </>
                    )}
                    {validSearch &&
                        (!investigationData?.content || investigationData?.content?.length === 0) &&
                        (!labReportData?.content || labReportData?.content?.length === 0) &&
                        (!patientData?.content || patientData?.content?.length === 0) && (
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
                        !submitted &&
                        patientData?.content &&
                        patientData.content.length > 0 && (
                            <PatientResults
                                validSearch={validSearch}
                                data={patientData.content}
                                totalResults={patientData.total}
                                handlePagination={handlePagination}
                                currentPage={currentPage}
                            />
                        )}
                    {lastSearchType === SEARCH_TYPE.INVESTIGATION &&
                        !submitted &&
                        investigationData?.content &&
                        investigationData?.content?.length > 0 && (
                            <InvestigationResults
                                validSearch={validSearch}
                                data={investigationData?.content as [Investigation]}
                                totalResults={investigationData?.total}
                                handlePagination={handlePagination}
                                currentPage={currentPage}
                            />
                        )}
                    {lastSearchType === SEARCH_TYPE.LAB_REPORT &&
                        !submitted &&
                        labReportData?.content &&
                        labReportData?.content?.length > 0 && (
                            <LabReportResults
                                validSearch={validSearch}
                                data={labReportData?.content as [LabReport]}
                                totalResults={labReportData?.total}
                                handlePagination={handlePagination}
                                currentPage={currentPage}
                            />
                        )}
                </Grid>
            </Grid>
        </div>
    );
};
