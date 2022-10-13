import { Grid } from '@trussworks/react-uswds';
import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { EventSearch } from '../../components/EventSearch';
import { SimpleSearch } from '../../components/SimpleSearch';
import { Deceased, PersonFilter, useFindPatientsByFilterQuery } from '../../generated/graphql/schema';
import './search.scss';
import { SearchItems } from './SearchItems';

export const SearchEngine = () => {
    const filterObj: PersonFilter = { deceased: Deceased.N };
    const [searchType, setSearchType] = useState<string>('search');
    const { data } = useFindPatientsByFilterQuery({
        variables: {
            filter: filterObj
        }
    });

    const [searchParams] = useSearchParams();
    // const navigate = useNavigate();

    // const [searchValue, setSearchValue] = useState<string>('');
    // const [filtered, setFiltered] = useState<any[]>();

    useEffect(() => {
        data && getFilteredData(searchParams.get('q') || '');
    }, [searchParams, data]);

    // const handleSearch = (e: any) => {
    //     e.preventDefault();
    //     navigate({
    //         pathname: '/search',
    //         search: `?q=${searchValue}`
    //     });
    // };

    const getFilteredData = (value: string) => {
        // const filteredData = data?.findPatientsByFilter?.map((p) => {
        //     return { firstNm: p?.firstNm, id: p?.id };
        // });
        // setFiltered(filteredData);
    };

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
                        {searchType === 'search' ? <SimpleSearch /> : <EventSearch />}
                    </div>
                </Grid>
                <Grid col={9}>
                    <SearchItems />
                </Grid>
            </Grid>
        </div>
    );
};
