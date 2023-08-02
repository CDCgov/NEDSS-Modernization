import { Input } from 'components/FormInputs/Input';
import { Button } from '@trussworks/react-uswds';
import { Icon } from '@trussworks/react-uswds';
import { useState } from 'react';

export const SearchBar = ({ onChange }: any) => {
    const [search, setSearch] = useState<any>('');
    const handleSearch = ({ target }: any) => {
        setSearch(target.value);
        onChange(target.value);
    };

    return (
        <div className="valueset-local-library__header">
            <Input
                placeholder="Search pages by keyword"
                type="text"
                htmlFor="searchbar"
                onChange={handleSearch}
                id="searchbar"
                value={search}
            />
            <Button type="submit" onChange={onChange}>
                <Icon.Search size={3} />
            </Button>
        </div>
    );
};
