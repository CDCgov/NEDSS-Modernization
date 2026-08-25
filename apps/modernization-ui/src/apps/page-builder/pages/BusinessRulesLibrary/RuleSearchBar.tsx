import { ChangeEvent, KeyboardEvent as ReactKeyboardEvent, useEffect, useState } from 'react';

import { Button, Icon, TextInput } from '@trussworks/react-uswds';

import styles from './rule-search-bar.module.scss';

type Props = {
    onChange: (query: string) => void;
    onDownloadCsv: () => void;
    onDownloadPdf: () => void;
};
export const RuleSearchBar = ({ onChange, onDownloadCsv, onDownloadPdf }: Props) => {
    const [searchTags, setSearchTags] = useState<string[]>([]);
    const [search, setSearch] = useState<string>('');

    const handleSearch = ({ target }: ChangeEvent<HTMLInputElement>) => {
        setSearch(target.value);
    };

    const handleSubmit = () => {
        if (search) {
            setSearchTags([...searchTags, search]);
        }
        onChange(search);
    };

    const handleEnter = (event: ReactKeyboardEvent<HTMLInputElement>) => {
        if (event.key === 'Enter') {
            handleSubmit();
        }
    };

    useEffect(() => {
        if (search === '') {
            handleSubmit();
        }
    }, [search]);

    return (
        <div className={styles.searchBar}>
            <div className={styles.searchFilter}>
                <div className={styles.field}>
                    <TextInput
                        name="searchbar"
                        type="search"
                        placeholder="Search by source question, target(s) or ID"
                        onChange={handleSearch}
                        id="business-rules-search"
                        defaultValue={search}
                        onKeyDown={handleEnter}
                    />
                    <Button
                        type="submit"
                        onClick={handleSubmit}
                        className={styles.searchButton}
                        data-testid="businessRulesSearchBtn"
                    >
                        <Icon.Search aria-label="search" size={5} className={styles.searchIcon} />
                    </Button>
                </div>
                <Button
                    type="button"
                    onClick={onDownloadPdf}
                    className={styles.downloadButton}
                    outline={true}
                    data-tooltip-position="top"
                >
                    <Icon.Print aria-label="print this page" size={3} data-testid="print-icon" />
                </Button>
                <Button
                    type="button"
                    className={styles.downloadButton}
                    outline={true}
                    onClick={onDownloadCsv}
                    data-testid="file-download"
                    data-tooltip-position="top"
                >
                    <Icon.FileDownload aria-label="Download as csv" size={3} />
                </Button>
            </div>
        </div>
    );
};
