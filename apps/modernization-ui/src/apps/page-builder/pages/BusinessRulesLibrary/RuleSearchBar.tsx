import { Button, Icon, TextInput } from '@trussworks/react-uswds';
import { KeyboardEvent as ReactKeyboardEvent, ChangeEvent, useState, useEffect } from 'react';
import styles from './rule-search-bar.module.scss';

type Props = {
    onChange: (query: string) => void;
    onDownloadCsv: () => void;
    onDownloadPdf: () => void;
};
export const RuleSearchBar = ({ onChange, onDownloadCsv, onDownloadPdf }: Props) => {
    const [searchTags, setSearchTags] = useState<any>([]);
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
        if (event.key == 'Enter') {
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
                        data-testid="businessRulesSearchBtn">
                        <Icon.Search size={5} className={styles.searchIcon} />
                    </Button>
                </div>
                <Button
                    aria-label="Print this page"
                    type="button"
                    onClick={onDownloadPdf}
                    className={styles.downloadButton}
                    outline
                    data-tooltip-position="top">
                    <Icon.Print size={3} data-testid="print-icon" />
                </Button>
                <Button
                    aria-label="Download as csv"
                    type="button"
                    className={styles.downloadButton}
                    outline
                    onClick={onDownloadCsv}
                    data-testid="file-download"
                    data-tooltip-position="top">
                    <Icon.FileDownload size={3} />
                </Button>
            </div>
        </div>
    );
};
