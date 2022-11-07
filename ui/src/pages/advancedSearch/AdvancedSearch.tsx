import { Grid } from '@trussworks/react-uswds';
import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { EventSearch } from '../../components/EventSearch/EventSerach';
import { SimpleSearch } from '../../components/SimpleSearch';
import { PersonFilter, useFindPatientsByFilterLazyQuery } from '../../generated/graphql/schema';
import './AdvancedSearch.scss';
import { SearchItems } from './SearchItems';

export const AdvancedSearch = () => {
    const [searchType, setSearchType] = useState<string>('search');
    const [searchItems, setSearchItems] = useState<any>([]);
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
        searchParams?.get('city') && (rowData.city = searchParams?.get('city') as string);
        searchParams?.get('zip') && (rowData.zip = searchParams?.get('zip') as string);
        searchParams?.get('id') && (rowData.city = searchParams?.get('id') as string);
        searchParams?.get('DateOfBirth') && (rowData.dateOfBirth = searchParams?.get('DateOfBirth') as unknown as Date);
        getFilteredData({
            variables: {
                filter: rowData,
                page: {
                    pageNumber: 0,
                    pageSize: 100
                }
            }
        }).then((items: any) => {
            setSearchItems(items.data.findPatientsByFilter);
        });
    }, []);

    return (
        <div className="padding-0 search-page-height bg-base-lightest">
            <Grid row className="search-page-height">
                <Grid col={3} className="bg-white border-right border-base-light">
                    <div className="left-searchbar">
                        <div className="grid-row flex-align-center">
                            <h6
                                className={`${
                                    searchType === 'search' && 'active'
                                } text-normal type font-sans-md padding-bottom-1 margin-x-2 cursor-pointer`}
                                onClick={() => setSearchType('search')}>
                                Simple Search
                            </h6>
                            <h6
                                className={`${
                                    searchType !== 'search' && 'active'
                                } padding-bottom-1 type text-normal font-sans-md cursor-pointer`}
                                onClick={() => setSearchType('event')}>
                                Event Search
                            </h6>
                        </div>
                        {searchType === 'search' ? <SimpleSearch /> : <EventSearch onSearch={onEventSearch} />}
                    </div>
                </Grid>
                <Grid col={9}>
                    <SearchItems data={searchItems} />
                </Grid>
            </Grid>
        </div>
    );
};
