import { Button, ButtonGroup, Grid, Icon } from '@trussworks/react-uswds';
import { useEffect, useState } from 'react';
import { Link, useSearchParams } from 'react-router-dom';
import { EventSearch } from '../../components/EventSearch/EventSerach';
import { SimpleSearch } from '../../components/SimpleSearch';
import { PersonFilter, useFindPatientsByFilterLazyQuery } from '../../generated/graphql/schema';
import './AdvancedSearch.scss';
import { SearchItems } from './SearchItems';

export const AdvancedSearch = () => {
    const [searchType, setSearchType] = useState<string>('search');
    const [searchItems, setSearchItems] = useState<any>([]);
    const [initialSearch, setInitialSearch] = useState<boolean>(false);
    const [dynamicHeight, setDynamicHeight] = useState<number>(420);
    const [searchParams] = useSearchParams();

    const [getFilteredData] = useFindPatientsByFilterLazyQuery();

    const onEventSearch = (data: any) => {
        if (data) {
            setSearchItems(data?.findPatientsByEvent);
        }
    };

    useEffect(() => {
        const rowData: PersonFilter = {
            firstName: searchParams?.get('firstName') as string,
            lastName: searchParams?.get('lastName') as string
        };
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
            }).then((items: any) => {
                setSearchItems(items.data.findPatientsByFilter);
            });
        } else {
            setInitialSearch(false);
        }
    }, []);

    const handleSubmit = (data: PersonFilter) => {
        getFilteredData({
            variables: {
                filter: data,
                page: {
                    pageNumber: 0,
                    pageSize: 10
                }
            }
        }).then((items: any) => {
            setInitialSearch(true);
            setSearchItems(items.data.findPatientsByFilter);
        });
    };

    return (
        <div
            className={`padding-0 search-page-height bg-light ${
                searchItems.length > 7 ? 'full-height' : 'partial-height'
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
                            <SimpleSearch handleSubmission={handleSubmit} dynamicHeight={dynamicHeight} />
                        ) : (
                            <EventSearch onSearch={onEventSearch} />
                        )}
                    </div>
                </Grid>
                <Grid col={9}>
                    <Grid
                        row
                        className="flex-align-center flex-justify margin-top-4 margin-x-4 border-bottom padding-bottom-1 border-base-lighter">
                        {initialSearch ? (
                            <p className="margin-0 font-sans-md margin-top-05 text-normal">
                                <strong>{searchItems.length}</strong> Results for:
                            </p>
                        ) : (
                            <p className="margin-0 font-sans-md margin-top-05 text-normal">Perform a search</p>
                        )}
                        <div>
                            <ButtonGroup type="default">
                                <Button
                                    disabled={searchItems.length === 0}
                                    className="width-full margin-top-0"
                                    type={'button'}
                                    outline>
                                    Sort by
                                    <Icon.ArrowDropDown />
                                </Button>
                            </ButtonGroup>
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
                    {initialSearch && searchItems.length === 0 && (
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
                        setSearchHeight={(height) => setDynamicHeight(height > 0 ? height : 380)}
                        data={searchItems}
                    />
                </Grid>
            </Grid>
        </div>
    );
};
