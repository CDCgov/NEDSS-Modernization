import { Input } from 'components/FormInputs/Input';
import { Button } from '@trussworks/react-uswds';
import { Icon } from '@trussworks/react-uswds';
import { useNavigate } from 'react-router-dom';
import React, { useEffect, useState } from 'react';
import './TableMenu.scss';
import { FilterButton } from '../../pages/FilterModal/Filter';

type Props = {
    tableType: string;
    searchQuery: string;
    setSearchQuery: (query: string) => void;
    onDownloadIconClick?: () => void;
    onPrintIconClick?: () => void;
};

export const TableMenu = ({ tableType, searchQuery, setSearchQuery, onDownloadIconClick }: Props) => {
    const navigate = useNavigate();
    const [keywords, setKeywords] = useState<string>('');
    const addNew = () => {
        navigate(`/page-builder/add/${tableType}`);
    };
    const handleEnter = (event: React.KeyboardEvent<HTMLInputElement>) => {
        if (event.key == 'Enter') {
            setSearchQuery(keywords);
        }
    };
    useEffect(() => {
        if (searchQuery && searchQuery !== '') {
            setKeywords(searchQuery);
        }
    }, [searchQuery]);

    return (
        <div className="table-menu">
            <Input
                placeholder="Search pages by keyword"
                type="search"
                htmlFor="searchbar"
                id="searchbar"
                defaultValue={keywords}
                onChange={(e: any) => setKeywords(e.target.value)}
                onKeyDown={handleEnter}
            />
            <Button type="submit" onClick={() => setSearchQuery(keywords)}>
                <Icon.Search size={3} />
            </Button>
            <Button type="button" outline>
                <Icon.Print size={3} />
            </Button>
            <FilterButton />
            <Button type="button" outline onClick={onDownloadIconClick} data-testid="file-download">
                <Icon.FileDownload size={3} />
            </Button>
            <Button type="button" onClick={addNew}>
                <p>Add new {tableType}</p>
            </Button>
        </div>
    );
};
