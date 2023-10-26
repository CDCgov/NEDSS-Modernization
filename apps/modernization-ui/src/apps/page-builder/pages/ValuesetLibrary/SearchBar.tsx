import { Input } from 'components/FormInputs/Input';
import { Button, Tag } from '@trussworks/react-uswds';
import { Icon } from '@trussworks/react-uswds';
import React, { useState } from 'react';
import { QuestionLibraryFilterModal } from './FilterModal/ValuesetLibraryFilterModal';

export const SearchBar = ({ onChange }: any) => {
    const [search, setSearch] = useState<string>('');
    const [searchTags, setSearchTags] = useState<any>([]);

    const handleSearch = ({ target }: any) => {
        setSearch(target.value);
    };

    const handleSubmit = () => {
        setSearchTags([...searchTags, search]);
        onChange(search);
    };
    const handleRemove = (removeTag: string) => {
        setSearch('');
        onChange('', '');
        setSearchTags((preTag: string[]) => preTag.filter((tag) => tag !== removeTag));
    };

    return (
        <div className="valueset-local-library__header outer-search-box">
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
                <QuestionLibraryFilterModal />
            </div>
        </div>
    );
};
