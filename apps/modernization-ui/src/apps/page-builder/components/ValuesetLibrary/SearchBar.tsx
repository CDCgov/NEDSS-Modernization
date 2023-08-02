import { Input } from 'components/FormInputs/Input';
import { Button } from '@trussworks/react-uswds';
import { Icon } from '@trussworks/react-uswds';
import { useState } from 'react';

export const SearchBar = ({ onChange }: any) => {
    const [search, setSearch] = useState<string>('');
    const handleSearch = ({ target }: any) => {
        setSearch(target.value);
    };

    const handleSubmit = () => {
        onChange(search, {});
    };

    return (
        <div className="valueset-local-library__header">
            <Input
                placeholder="Search pages by keyword"
                onChange={handleSearch}
                defaultValue={search}
                type="text"
                htmlFor="searchbar"
                id="searchbar"
            />
            <Button type="submit" onClick={handleSubmit}>
                <Icon.Search size={3} />
            </Button>
        </div>
    );
};
