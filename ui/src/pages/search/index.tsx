import { Grid, Search } from '@trussworks/react-uswds';
import { useEffect, useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { useAllPatients } from '../../hooks/useAllPatients';

export const SearchEngine = () => {
    const { data } = useAllPatients();
    const [searchParams] = useSearchParams();
    const navigate = useNavigate();

    const [searchValue, setSearchValue] = useState<string>('');
    const [filtered, setFiltered] = useState([]);

    useEffect(() => {
        data && getFilteredData(searchParams.get('q') || '');
    }, [searchParams, data]);

    const handleSearch = (e: any) => {
        e.preventDefault();
        navigate({
            pathname: '/search',
            search: `?q=${searchValue}`
        });
    };

    const getFilteredData = (value: string) => {
        const includedKeys = ['firstNm', 'lastNm'];
        const filteredValue = value.includes('(') ? value.split('(')[0].trim() : value.trim();
        const filteredData = data.characters.results.filter((item: any) => {
            return Object.keys(item).some((key) =>
                includedKeys.includes(key)
                    ? item[key]?.toString().toLowerCase().includes(filteredValue.toLowerCase())
                    : false
            );
        });
        setFiltered(filteredData);
    };

    return (
        <div className="padding-2">
            <div className="bg-base-lighter padding-2 radius-md">
                <h2 className="font-lang-lg margin-top-0 margin-bottom-3">Search</h2>
                <Search size="big" onChange={(e: any) => setSearchValue(e.target.value)} onSubmit={handleSearch} />
            </div>
            <div className="grid-row margin-y-5">
                <div className="grid-col-12">
                    <h2 className="font-sans-lg text-normal margin-top-0 margin-bottom-3">Results (0)</h2>
                    <Grid row gap={2}>
                        {filtered?.map((item: any) => (
                            <div className="grid-col-12" key={item.id}>
                                <h5>{item.firstNm}</h5>
                                <img src={item.lastNm} alt="" />
                            </div>
                        ))}
                        {filtered.length === 0 &&
                            data?.characters?.results?.map((item: any) => (
                                <div className="grid-col-12" key={item.id}>
                                <h5>{item.firstNm}</h5>
                                <img src={item.lastNm} alt="" />
                            </div>
                            ))}
                    </Grid>
                </div>
            </div>
        </div>
    );
};
