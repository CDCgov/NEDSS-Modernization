import { Input } from 'components/FormInputs/Input';
import { Button, Icon } from '@trussworks/react-uswds';
import { useState } from 'react';
import { FilterButton } from './FilterButton/Filter';
import './SearchBar.scss';

export const SearchBar = ({ onChange }: any) => {
    const [searchTags, setSearchTags] = useState<any>([]);
    const [search, setSearch] = useState<string>('');

    const handleSearch = ({ target }: any) => {
        setSearch(target.value);
    };

    const handleSubmit = () => {
        if (search) {
            setSearchTags([...searchTags, search]);
        }
        onChange(search);
    };

    return (
        <div className="searchBar">
            <div className="search-filter">
                <Input
                    placeholder="Search pages by Source field, target field or ID"
                    type="text"
                    htmlFor="searchbar"
                    onChange={handleSearch}
                    id="searchbar"
                    defaultValue={search}
                />
                <Button type="submit" onClick={handleSubmit}>
                    <Icon.Search size={3} />
                </Button>
                <FilterButton />
            </div>
        </div>
    );
};
