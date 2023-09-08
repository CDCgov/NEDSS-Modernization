import { Input } from 'components/FormInputs/Input';
import { Button, Icon, Tag } from '@trussworks/react-uswds';
import { useState } from 'react';
export const SearchBar = ({ onChange }: any) => {
    const [searchTags, setSearchTags] = useState<any>([]);
    const [search, setSearch] = useState<string>('');

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
        <div className="question-local-library__header outer-search-box">
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
                    placeholder="Search by keyword"
                    type="text"
                    htmlFor="searchbar"
                    onChange={handleSearch}
                    id="searchbar"
                    value={search}
                />
                <Button type="submit" onClick={handleSubmit}>
                    <Icon.Search size={3} />
                </Button>
            </div>
        </div>
    );
};
