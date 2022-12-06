import { Button, Grid } from '@trussworks/react-uswds';
import { useEffect, useRef, useState } from 'react';
import { Link, useSearchParams } from 'react-router-dom';
import { EventSearch } from '../../components/EventSearch/EventSerach';
import { SimpleSearch } from '../../components/SimpleSearch';
import {
    FindPatientsByFilterQueryResult,
    PersonFilter,
    useFindPatientsByFilterLazyQuery
} from '../../generated/graphql/schema';
import './AdvancedSearch.scss';
import Chip from './Chip';
import { SearchItems } from './SearchItems';

export const AdvancedSearch = () => {
    const [searchType, setSearchType] = useState<string>('search');
    const [searchItems, setSearchItems] = useState<any>([]);
    const [initialSearch, setInitialSearch] = useState<boolean>(false);
    const [searchParams] = useSearchParams();
    const [formData, setFormData] = useState<PersonFilter>();
    const [resultsChip, setResultsChip] = useState<{ name: string; value: string }[]>([]);
    const [showSorting, setShowSorting] = useState<boolean>(false);
    const wrapperRef = useRef<any>(null);

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

    const [getFilteredData] = useFindPatientsByFilterLazyQuery();

    const onEventSearch = (data: any) => {
        if (data) {
            setSearchItems(data?.findPatientsByEvent);
        }
    };

    useEffect(() => {
        // TODO decrypt url parameters (after SimpleSearch is updated to encrypt before redirect)
        // After decryption we also need to set the value of the input fields
        const rowData: PersonFilter = {
            firstName: searchParams?.get('firstName') as string,
            lastName: searchParams?.get('lastName') as string
        };
        console.log('rowData', rowData);
        if (rowData.firstName || rowData.lastName) {
            setInitialSearch(true);
            searchParams?.get('city') && (rowData.city = searchParams?.get('city') as string);
            searchParams?.get('zip') && (rowData.zip = searchParams?.get('zip') as string);
            searchParams?.get('id') && (rowData.city = searchParams?.get('id') as string);
            searchParams?.get('DateOfBirth') &&
                (rowData.dateOfBirth = searchParams?.get('DateOfBirth') as unknown as Date);
            getFilteredData({
                variables: {
                    filter: rowData,
                    page: {
                        pageNumber: 0,
                        pageSize: 10
                    }
                }
            }).then((items: FindPatientsByFilterQueryResult) => {
                setSearchItems(items?.data?.findPatientsByFilter.content);
            });
        } else {
            setInitialSearch(false);
        }
    }, []);

    function isEmpty(obj: any) {
        for (const key in obj) {
            if (obj[key] !== undefined && obj[key] != '') return true;
        }
        return false;
    }

    const handleSubmit = (data: PersonFilter) => {
        if (isEmpty(data)) {
            getFilteredData({
                variables: {
                    filter: data,
                    page: {
                        pageNumber: 0,
                        pageSize: 50
                    }
                }
            }).then((items: FindPatientsByFilterQueryResult) => {
                setFormData(items as any); // TODO - not sure what this does but I'm pretty sure its broken
                setInitialSearch(true);
                const chips: any = [];
                Object.entries(data).map((re) => {
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
                setResultsChip(chips);
                setSearchItems(items?.data?.findPatientsByFilter.content);
            });
        }
    };

    const handleChipClose = (value: string) => {
        if (formData) {
            switch (value) {
                case 'last':
                    formData.lastName = undefined;
                    break;
                case 'first':
                    formData.firstName = undefined;
                    break;
                case 'sex':
                    formData.gender = undefined;
                    break;
                case 'dob':
                    formData.dateOfBirth = undefined;
                    break;
            }
            getFilteredData({
                variables: {
                    filter: formData,
                    page: {
                        pageNumber: 0,
                        pageSize: 10
                    }
                }
            }).then((items: any) => {
                setResultsChip(resultsChip.filter((item) => item.name !== value));
                setSearchItems(items?.data?.findPatientsByFilter);
            });
        }
    };

    const handleSort = () => {
        setShowSorting(false);
        console.log('assa');
    };

    return (
        <div
            className={`padding-0 search-page-height bg-light advanced-search ${
                searchItems?.length > 7 ? 'full-height' : 'partial-height'
            }`}>
            <Grid row className="page-title-bar bg-white">
                <div className="width-full text-bold flex-row display-flex flex-align-center flex-justify">
                    Search
                    <Button className="pxadding-x-3 add-patient-button" type={'submit'}>
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
                            <SimpleSearch handleSubmission={handleSubmit} />
                        ) : (
                            <EventSearch onSearch={onEventSearch} />
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
                                            <Button onClick={() => handleSort()} type={'button'} unstyled>
                                                Patient name (A-Z)
                                            </Button>
                                        </li>
                                        <li className="usa-nav__submenu-item">
                                            <Button onClick={() => handleSort()} type={'button'} unstyled>
                                                Patient name (Z-A)
                                            </Button>
                                        </li>
                                        <li className="usa-nav__submenu-item">
                                            <Button onClick={() => handleSort()} type={'button'} unstyled>
                                                Date of birth (Ascending)
                                            </Button>
                                        </li>
                                        <li className="usa-nav__submenu-item">
                                            <Button onClick={() => handleSort()} type={'button'} unstyled>
                                                Date of birth (Descending)
                                            </Button>
                                        </li>
                                        <li className="usa-nav__submenu-item">
                                            <Button onClick={() => handleSort()} type={'button'} unstyled>
                                                Start Date (Ascending)
                                            </Button>
                                        </li>
                                        <li className="usa-nav__submenu-item">
                                            <Button onClick={() => handleSort()} type={'button'} unstyled>
                                                Start Date (Descending)
                                            </Button>
                                        </li>
                                    </ul>
                                )}
                            </div>
                        </div>
                    </Grid>
                    {!initialSearch && (
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
                    <SearchItems initialSearch={initialSearch} data={searchItems} />
                </Grid>
            </Grid>
        </div>
    );
};
