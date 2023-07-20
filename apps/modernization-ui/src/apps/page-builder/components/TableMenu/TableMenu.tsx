import { Input } from 'components/FormInputs/Input';
import { Button } from '@trussworks/react-uswds';
import { Icon } from '@trussworks/react-uswds';
import { useNavigate } from 'react-router-dom';
import React, { useEffect, useState } from 'react';
import './TableMenu.scss';

type Props = {
    tableType: string;
    searchQuery: string;
    setSearchQuery: (query: string) => void;
};

export const TableMenu = ({ tableType, searchQuery, setSearchQuery }: Props) => {
    const navigate = useNavigate();
    const [keywords, setKeywords] = useState<string>('');
    const clearX = document.getElementById('searchbar');
    const addNew = () => {
        navigate(`/page-builder/add/${tableType}`);
    };
    const handleEnter = (event: React.KeyboardEvent<HTMLInputElement>) => {
        if (event.key == 'Enter') {
            setSearchQuery(keywords);
        }
    };
    if (clearX) {
        clearX.addEventListener('search', (e) => {
            console.log(e);
        });
    }
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
            <Button type="button" outline>
                <Icon.FileDownload size={3} />
            </Button>
            <Button type="button" onClick={addNew}>
                <p>Add new {tableType}</p>
            </Button>
        </div>
    );
};
