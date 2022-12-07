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
    const [searchItems, setSearchItems] = useState<any>([]);
    const [initialSearch, setInitialSearch] = useState<boolean>(false);
    const [searchParams] = useSearchParams();
    const [formData, setFormData] = useState<PersonFilter>();
    const [resultsChip, setResultsChip] = useState<{ name: string; value: string }[]>([]);
    const [showSorting, setShowSorting] = useState<boolean>(false);
    const [getFilteredData, { data }] = useFindPatientsByFilterLazyQuery({
        onCompleted: handleSearchResults
    });
    const [getEventSearch] = useFindPatientsByEventLazyQuery({
        onCompleted: handleEventSearchResults
    });

    const [submitted, setSubmitted] = useState(false);
    const wrapperRef = useRef<any>(null);
    const PAGE_SIZE = 25;

    useEffect(() => {
        function handleClickOutside(event: any) {
            if (wrapperRef.current && !wrapperRef.current.contains(event.target)) {
                setShowSorting(false);
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
                                pageNumber: 0,
                                pageSize: PAGE_SIZE,
                                ...sort
                            }
                        }
                    });
                } else {
                    filter = filter as PersonFilter;
                    setFormData(filter);
                    if (!isEmpty(filter)) {
                        getFilteredData({
                            variables: {
                                filter: filter,
                                page: {
                                    pageNumber: 0,
                                    pageSize: PAGE_SIZE,
                                    ...sort
                                }
                            }
                        });
                    } else {
                        // empty filter, clear content
                        setSearchItems([]);
                        setResultsChip([]);
                        setInitialSearch(false);
                        setSubmitted(true);
                    }
                }
            });
        } else {
            setInitialSearch(false);
        }
    }, [searchParams, state.isLoggedIn, sort]);

    function handleSearchResults(data: FindPatientsByFilterQuery) {
        setInitialSearch(true);
        const chips: any = [];
        if (formData) {
            Object.entries(formData as any).map((re) => {
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
            });
        }
        setResultsChip(chips);
        setSearchItems(data.findPatientsByFilter.content);
    }

    function handleEventSearchResults(data: FindPatientsByEventQuery) {
        setInitialSearch(true);
        setResultsChip([]);
        setSearchItems(data?.findPatientsByEvent.content);
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
        setSubmitted(true);
        if (!isEmpty(data)) {
            // send filter for encryption
            const encryptedFilter = await EncryptionControllerService.encryptUsingPost({
                authorization: `Bearer ${state.getToken()}`,
                object: filter
            });

            // URI encode encrypted filter
            search = `?q=${encodeURIComponent(encryptedFilter.value)}`;
            setInitialSearch(true);
        } else {
            setInitialSearch(false);
            setSearchItems([]);
        }

        // Update query param to trigger search
        navigate({
            pathname: '/advanced-search',
            search
        });
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
                case 'zip':
                    tempFormData = { ...tempFormData, zip: undefined };
                    break;
                case 'phoneNumber':
                    tempFormData = { ...tempFormData, phoneNumber: undefined };
                    break;
                case 'email':
                    tempFormData = { ...tempFormData, email: undefined };
                    break;
            }
            setFormData(tempFormData);
            handleSubmit(tempFormData);
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
        formData &&
            getFilteredData({
                variables: {
                    filter: formData,
                    page: {
                        pageNumber: page,
                        pageSize: PAGE_SIZE,
                        ...sort
                    }
                }
            });
    };

    return (
        <div
            className={`padding-0 search-page-height bg-light advanced-search ${
                searchItems?.length > 7 ? 'full-height' : 'partial-height'
            }`}>
            <Grid row className="page-title-bar bg-white">
                <div className="width-full text-bold flex-row display-flex flex-align-center flex-justify">
                    Search
                    <Button
                        className="pxadding-x-3 add-patient-button"
                        type={'submit'}
                        onClick={handleAddNewPatientClick}>
                        Add new patient
                    </Button>
                </div>
            </Grid>
            <Grid row className="search-page-height">
                <Grid col={3} className="bg-white border-right border-base-light">
                    <div className="left-searchbar">
                        <h3 className="padding-x-2 text-medium margin-bottom-0 refine-text">Refine your search</h3>
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
                            <SimpleSearch handleSubmission={handleSubmit} data={formData} />
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
                                <strong className="margin-right-1">{searchItems?.length}</strong> Results for:
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
                                    disabled={searchItems?.length === 0}
                                    className="width-full margin-top-0"
                                    type={'button'}
                                    onClick={() => setShowSorting(!showSorting)}
                                    // onBlur={() => setShowSorting(false)}
                                    outline>
                                    Sort by
                                    <img
                                        style={{ marginLeft: '5px' }}
                                        src={searchItems?.length === 0 ? 'down-arrow-white.svg' : 'down-arrow-blue.svg'}
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
                                        <li className="usa-nav__submenu-item">
                                            <Button
                                                onClick={() => handleSort(SortDirection.Asc, PersonSortField.AddTime)}
                                                type={'button'}
                                                unstyled>
                                                Start Date (Ascending)
                                            </Button>
                                        </li>
                                        <li className="usa-nav__submenu-item">
                                            <Button
                                                onClick={() => handleSort(SortDirection.Desc, PersonSortField.AddTime)}
                                                type={'button'}
                                                unstyled>
                                                Start Date (Descending)
                                            </Button>
                                        </li>
                                    </ul>
                                )}
                            </div>
                        </div>
                    </Grid>
                    {!initialSearch && (
                        <>
                            {submitted && (
                                <div className="margin-x-4 margin-y-2 flex-row grid-row flex-align-center flex-justify-center">
                                    <Alert
                                        type="error"
                                        heading="You did not make a search"
                                        headingLevel="h4"
                                        className="width-full">
                                        <>Please make sure to enter atleast one search criteria.</>
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
                    {initialSearch && searchItems?.length === 0 && (
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
                    <SearchItems
                        initialSearch={initialSearch}
                        data={searchItems}
                        totalResults={Number(data?.findPatientsByFilter.total)}
                        handlePagination={handlePagination}
                    />
                </Grid>
            </Grid>
        </div>
    );
};
