import { Input } from 'components/FormInputs/Input';
import { Button, Icon, Tag } from '@trussworks/react-uswds';
import { useState } from 'react';
import { FilterButton } from './FilterButton/Filter';
import { useGetPageDetails } from 'apps/page-builder/page/management';

export const SearchBar = ({ onChange }: any) => {
    const [searchTags, setSearchTags] = useState<any>([]);
    const [search, setSearch] = useState<string>('');
    const { page } = useGetPageDetails();

    const handleSearch = ({ target }: any) => {
        setSearch(target.value);
    };

    const handleSubmit = () => {
        setSearchTags([...searchTags, search]);
        onChange(search);
    };

    const handleRemove = (removeTag: string) => {
        setSearch('');
        onChange('');
        setSearchTags((preTag: string[]) => preTag.filter((tag) => tag !== removeTag));
    };

    return (
        <div className="business-rules-library__header outer-search-box">
            <h3> {page?.name} | business rules </h3>
            <div className="search-tag">
                {searchTags.map((tagName: string, index: number) => (
                    <div className="tag-cover" key={index}>
                        <Tag background="#005EA2">{tagName}</Tag>
                        <Icon.Close onClick={() => handleRemove(tagName)} />
                    </div>
                ))}
            </div>
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
