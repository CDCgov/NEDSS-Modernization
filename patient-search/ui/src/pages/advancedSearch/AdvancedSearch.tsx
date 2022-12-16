import { Alert, Button, Grid } from '@trussworks/react-uswds';
import { useContext, useEffect, useRef, useState } from 'react';
import { Link, useNavigate, useSearchParams } from 'react-router-dom';
import { EventSearch } from '../../components/EventSearch/EventSearch';
import { SimpleSearch } from '../../components/SimpleSearch';
import {
    EventFilter,
    FindPatientsByEventQuery,
    FindPatientsByFilterQuery,
    PersonFilter,
    PersonSortField,
    SortDirection,
    useFindPatientsByEventLazyQuery,
    useFindPatientsByFilterLazyQuery
} from '../../generated/graphql/schema';
import { EncryptionControllerService } from '../../generated/services/EncryptionControllerService';
import { RedirectControllerService } from '../../generated/services/RedirectControllerService';
import { UserContext } from '../../providers/UserContext';
import './AdvancedSearch.scss';
import Chip from './Chip';
import { SearchItems } from './SearchItems';
export const AdvancedSearch = () => {
    const NBS_URL = process.env.REACT_APP_NBS_URL ? process.env.REACT_APP_NBS_URL : '/nbs';
    const { state } = useContext(UserContext);
    const navigate = useNavigate();
    const [searchType, setSearchType] = useState<string>('search');
    const [sort, setSort] = useState<{ sortDirection: SortDirection; sortField: PersonSortField }>({
        sortDirection: SortDirection.Asc,
        sortField: PersonSortField.LastNm
    });
    const [initialSearch, setInitialSearch] = useState<boolean>(false);
    const [searchParams] = useSearchParams();
    const [formData, setFormData] = useState<PersonFilter>();
    const [resultsChip, setResultsChip] = useState<{ name: string; value: string }[]>([]);
    const [showSorting, setShowSorting] = useState<boolean>(false);
    const [currentPage, setCurrentPage] = useState(1);
    const [showAddNewDropDown, setShowAddNewDropDown] = useState<boolean>(false);
    const [fetch, { data, loading }] = useFindPatientsByFilterLazyQuery({
        onCompleted: handleSearchResults
    });
    const [getEventSearch] = useFindPatientsByEventLazyQuery({
        onCompleted: handleEventSearchResults
    });

    const [submitted, setSubmitted] = useState(false);
    const wrapperRef = useRef<any>(null);
    const addPatiendRef = useRef<any>(null);
    const PAGE_SIZE = 25;

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

    const handleTags = (filter: any) => {
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

    /**
     * Handles extracting and submitting the query from the q parameter,
     * this object could either be PersonFilter or EventFilter
     */
    useEffect(() => {
        const queryParam = searchParams?.get('q');
        if (queryParam && state.isLoggedIn) {
            EncryptionControllerService.decryptUsingPost({
                encryptedString: queryParam,
                authorization: `Bearer ${state.getToken()}`
            }).then(async (filter: PersonFilter | EventFilter) => {
                // if filter has eventType property, then it is an EventFilter
                if ((filter as EventFilter).eventType) {
                    filter = filter as EventFilter;
                    getEventSearch({
                        variables: {
                            filter,
                            page: {
                                pageNumber: currentPage,
                                pageSize: PAGE_SIZE,
                                ...sort
                            }
                        }
                    });
                } else {
                    filter = filter as PersonFilter;
                    if (!isEmpty(filter)) {
                        // JSON.stringify(filter) !== JSON.stringify(formData) &&
                        fetch({
                            variables: {
                                filter: filter,
                                page: {
                                    pageNumber: currentPage - 1,
                                    pageSize: PAGE_SIZE,
                                    ...sort
                                }
                            }
                        });
                        handleTags(filter);
                        setFormData(filter);
                    } else {
                        // empty filter, clear content
                        setResultsChip([]);
                        setInitialSearch(false);
                        setSubmitted(true);
                    }
                }
            });
        } else {
            setInitialSearch(false);
        }
    }, [searchParams, state.isLoggedIn, sort, currentPage]);

    useEffect(() => {
        if (submitted) {
            navigate({ pathname: '/advanced-search', search: `submitted=${true}` });
        }
    }, [submitted]);

    useEffect(() => {
        if (searchParams?.get('submitted')) {
            setSubmitted(true);
        }
    }, [searchParams]);

    function handleSearchResults(data: FindPatientsByFilterQuery) {
        setInitialSearch(true);
    }

    function handleEventSearchResults(data: FindPatientsByEventQuery) {
        setInitialSearch(true);
        setResultsChip([]);
    }

    function isEmpty(obj: any) {
        for (const key in obj) {
            if (obj[key] !== undefined && obj[key] != '') return false;
        }
        return true;
    }

    // handles submit from Person Search and Event Search,
    // it simply encrypts the filter object and sets it as the query parameter
    const handleSubmit = async (filter: PersonFilter | EventFilter) => {
        let search = '';
        if (!isEmpty(filter)) {
            // send filter for encryption
            const encryptedFilter = await EncryptionControllerService.encryptUsingPost({
                authorization: `Bearer ${state.getToken()}`,
                object: filter
            });
            setInitialSearch(true);

            // URI encode encrypted filter
            search = `?q=${encodeURIComponent(encryptedFilter.value)}`;
            navigate({
                pathname: '/advanced-search',
                search
            });
            setSubmitted(false);
        } else {
            setSubmitted(true);
        }
    };

    const handleClearAll = () => {
        navigate('/');
    };

    const handleChipClose = (value: string) => {
        let tempFormData: PersonFilter = formData as PersonFilter;
        setResultsChip(resultsChip.filter((c) => c.name != value));
        if (formData) {
            switch (value) {
                case 'last':
                    tempFormData = { ...tempFormData, lastName: undefined };
                    break;
                case 'first':
                    tempFormData = { ...tempFormData, firstName: undefined };
                    break;
                case 'sex':
                    tempFormData = { ...tempFormData, gender: undefined };
                    break;
                case 'dob':
                    tempFormData = { ...tempFormData, dateOfBirth: undefined };
                    break;
                case 'address':
                    tempFormData = { ...tempFormData, address: undefined };
                    break;
                case 'city':
                    tempFormData = { ...tempFormData, city: undefined };
                    break;
                case 'state':
                    tempFormData = { ...tempFormData, state: undefined };
                    break;
                case 'zip':
                    tempFormData = { ...tempFormData, zip: undefined };
                    break;
                case 'phoneNumber':
                    tempFormData = { ...tempFormData, phoneNumber: undefined };
                    break;
                case 'email':
                    tempFormData = { ...tempFormData, email: undefined };
                    break;
                case 'race':
                    tempFormData = { ...tempFormData, race: undefined };
                    break;
                case 'ethnicity':
                    tempFormData = { ...tempFormData, ethnicity: undefined };
                    break;
                case 'ID Number':
                    tempFormData = { ...tempFormData, identification: undefined };
                    break;
                case 'ID Type':
                    tempFormData = { ...tempFormData, identification: undefined };
                    break;
            }
            handleSubmit(tempFormData);
            resultsChip.length === 1 || ((value === 'ID Number' || value === 'ID Type') && navigate('/'));
        }
    };

    const handleSort = (sortDirection: SortDirection, sortField: PersonSortField) => {
        setSort({ sortDirection, sortField });
    };

    function handleAddNewPatientClick(): void {
        RedirectControllerService.preparePatientDetailsUsingGet({ authorization: 'Bearer ' + state.getToken() }).then(
            () => {
                window.location.href = `${NBS_URL}/PatientSearchResults1.do?ContextAction=Add`;
            }
        );
    }

    const handlePagination = (page: number) => {
        setCurrentPage(page);
    };

    return (
        <div
            className={`padding-0 search-page-height bg-light advanced-search ${
                data?.findPatientsByFilter?.content && data?.findPatientsByFilter?.content.length > 7
                    ? 'full-height'
                    : 'partial-height'
            }`}>
            <Grid row className="page-title-bar bg-white">
                <div className="width-full text-bold flex-row display-flex flex-align-center flex-justify">
                    Search
                    <div className="button-group">
                        <Button
                            disabled={
                                !data?.findPatientsByFilter?.content ||
                                data?.findPatientsByFilter?.content?.length === 0
                            }
                            className="padding-x-3 add-patient-button"
                            type={'button'}
                            onClick={() => setShowAddNewDropDown(!showAddNewDropDown)}
                            outline>
                            Add new patient
                            <img
                                style={{ marginLeft: '5px' }}
                                src={
                                    !data?.findPatientsByFilter?.content ||
                                    data?.findPatientsByFilter?.content?.length === 0
                                        ? 'down-arrow-white.svg'
                                        : 'down-arrow-blue.svg'
                                }
                            />
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
                                    <Button
                                        onClick={() => handleSort(SortDirection.Desc, PersonSortField.LastNm)}
                                        type={'button'}
                                        unstyled>
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
                        <div className="grid-row flex-align-center">
                            <h6
                                className={`${
                                    searchType === 'search' && 'active'
                                } text-normal type margin-y-3 font-sans-md padding-bottom-1 margin-x-2 cursor-pointer margin-top-2 margin-bottom-0`}
                                onClick={() => setSearchType('search')}>
                                Patient Search
                            </h6>
                            <h6
                                className={`${
                                    searchType !== 'search' && 'active'
                                } padding-bottom-1 type text-normal margin-y-3 font-sans-md cursor-pointer margin-top-2 margin-bottom-0`}
                                onClick={() => setSearchType('event')}>
                                Event Search
                            </h6>
                        </div>
                        {searchType === 'search' ? (
                            <SimpleSearch handleSubmission={handleSubmit} data={formData} clearAll={handleClearAll} />
                        ) : (
                            <EventSearch onSearch={handleSubmit} />
                        )}
                    </div>
                </Grid>
                <Grid col={9} className="scrollable-results">
                    <Grid
                        row
                        className="flex-align-center flex-justify margin-top-4 margin-x-4 border-bottom padding-bottom-1 border-base-lighter">
                        {initialSearch ? (
                            <div className="margin-0 font-sans-md margin-top-05 text-normal grid-row">
                                <strong className="margin-right-1">{data?.findPatientsByFilter?.total}</strong> Results
                                for:
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
                        <div>
                            <div className="button-group">
                                <Button
                                    disabled={
                                        !data?.findPatientsByFilter?.content ||
                                        data?.findPatientsByFilter?.content?.length === 0
                                    }
                                    className="width-full margin-top-0"
                                    type={'button'}
                                    onClick={() => setShowSorting(!showSorting)}
                                    outline>
                                    Sort by
                                    <img
                                        style={{ marginLeft: '5px' }}
                                        src={
                                            !data?.findPatientsByFilter?.content ||
                                            data?.findPatientsByFilter?.content?.length === 0
                                                ? 'down-arrow-white.svg'
                                                : 'down-arrow-blue.svg'
                                        }
                                    />
                                </Button>
                                {showSorting && (
                                    <ul ref={wrapperRef} id="basic-nav-section-one" className="usa-nav__submenu">
                                        <li className="usa-nav__submenu-item">
                                            <Button
                                                onClick={() => handleSort(SortDirection.Asc, PersonSortField.LastNm)}
                                                type={'button'}
                                                unstyled>
                                                Patient name (A-Z)
                                            </Button>
                                        </li>
                                        <li className="usa-nav__submenu-item">
                                            <Button
                                                onClick={() => handleSort(SortDirection.Desc, PersonSortField.LastNm)}
                                                type={'button'}
                                                unstyled>
                                                Patient name (Z-A)
                                            </Button>
                                        </li>
                                        <li className="usa-nav__submenu-item">
                                            <Button
                                                onClick={() => handleSort(SortDirection.Asc, PersonSortField.BirthTime)}
                                                type={'button'}
                                                unstyled>
                                                Date of birth (Ascending)
                                            </Button>
                                        </li>
                                        <li className="usa-nav__submenu-item">
                                            <Button
                                                onClick={() =>
                                                    handleSort(SortDirection.Desc, PersonSortField.BirthTime)
                                                }
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
                    {!initialSearch && !loading && (
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
                                className="margin-x-4 margin-y-2 flex-row grid-row flex-align-center flex-justify-center"
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
                    {initialSearch && data?.findPatientsByFilter?.content?.length === 0 && (
                        <div
                            className="margin-x-4 margin-y-2 flex-row grid-row flex-align-center flex-justify-center"
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
                                    <Link to="/" style={{ color: '#005EA2' }}>
                                        add a new patient
                                    </Link>
                                </p>
                            </div>
                        </div>
                    )}
                    {!submitted &&
                        !loading &&
                        data?.findPatientsByFilter?.content &&
                        data?.findPatientsByFilter?.content.length > 0 && (
                            <SearchItems
                                initialSearch={initialSearch}
                                data={data?.findPatientsByFilter.content}
                                totalResults={Number(data?.findPatientsByFilter.total)}
                                handlePagination={handlePagination}
                                currentPage={currentPage}
                            />
                        )}
                </Grid>
            </Grid>
        </div>
    );
};
