// import { Input } from 'components/FormInputs/Input';
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
                <TextInput
                    name="searchbar"
                    type="search"
                    placeholder="Search pages by a Source field, target field or ID"
                    onChange={handleSearch}
                    id="business-rules-search"
                    defaultValue={search}
                    onKeyDown={handleEnter}
                />
                <Button type="submit" onClick={handleSubmit} className={styles.searchButton}>
                    <Icon.Search size={5} className={styles.searchIcon} />
                </Button>
                <Button
                    aria-label="download search results as pdf"
                    type="button"
                    onClick={onDownloadPdf}
                    className={styles.downloadButton}
                    outline>
                    <Icon.Print size={3} data-testid="print-icon" />
                </Button>
                <Button
                    aria-label="download search results as csv"
                    type="button"
                    className={styles.downloadButton}
                    outline
                    onClick={onDownloadCsv}
                    data-testid="file-download">
                    <Icon.FileDownload size={3} />
                </Button>
            </div>
        </div>
    );
};
