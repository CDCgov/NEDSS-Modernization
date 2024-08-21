import { ButtonActionMenu } from 'components/ButtonActionMenu/ButtonActionMenu';
import { Icon } from '@trussworks/react-uswds';
import styles from './search-results-list-options.module.scss';
import { Direction, useSorting } from 'sorting';
import { SortField } from 'generated/graphql/schema';
import { useEffect } from 'react';
import { Button } from 'components/button';

type Props = {
    disabled?: boolean;
};

const SearchResultsListOptions = ({ disabled = false }: Props) => {
    const { sortBy } = useSorting();

    const savePreferences = (selection: SortField, direction: Direction) => {
        localStorage.setItem('searchResultsSortBy', selection);
        localStorage.setItem('searchResultsSortDirection', direction);
    };

    useEffect(() => {
        const sortName = localStorage.getItem('searchResultsSortBy');
        const sortDirection = localStorage.getItem('searchResultsSortDirection');

        if (sortName && sortDirection) {
            sortBy(sortName, sortDirection as Direction);
        }
    }, []);

    return (
        <ButtonActionMenu
            header="Sort list by"
            className={styles.option}
            outline
            icon={<Icon.SortArrow />}
            disabled={disabled}>
            <>
                <Button
                    type="button"
                    onClick={() => {
                        sortBy(SortField.Relevance, Direction.Descending);
                        savePreferences(SortField.Relevance, Direction.Descending);
                    }}>
                    Closest match
                </Button>
                <Button
                    type="button"
                    onClick={() => {
                        sortBy(SortField.LastNm, Direction.Ascending);
                        savePreferences(SortField.LastNm, Direction.Ascending);
                    }}>
                    Patient name (A-Z)
                </Button>
                <Button
                    type="button"
                    onClick={() => {
                        sortBy(SortField.LastNm, Direction.Descending);
                        savePreferences(SortField.LastNm, Direction.Descending);
                    }}>
                    Patient name (Z-A)
                </Button>
                <Button
                    type="button"
                    onClick={() => {
                        sortBy(SortField.BirthTime, Direction.Ascending);
                        savePreferences(SortField.BirthTime, Direction.Ascending);
                    }}>
                    Date of birth (Ascending)
                </Button>
                <Button
                    type="button"
                    onClick={() => {
                        sortBy(SortField.BirthTime, Direction.Descending);
                        savePreferences(SortField.BirthTime, Direction.Descending);
                    }}>
                    Date of birth (Descending)
                </Button>
            </>
        </ButtonActionMenu>
    );
};

export { SearchResultsListOptions };
