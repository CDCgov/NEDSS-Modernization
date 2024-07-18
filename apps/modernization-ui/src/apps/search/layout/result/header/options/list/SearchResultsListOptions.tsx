import { ButtonActionMenu } from 'components/ButtonActionMenu/ButtonActionMenu';
import { Icon } from '@trussworks/react-uswds';
import styles from './search-results-list-options.module.scss';
import { Direction, useSorting } from 'sorting';
import { SortField } from 'generated/graphql/schema';
import { useEffect } from 'react';

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
            className={styles.option}
            outline
            icon={<Icon.SortArrow />}
            disabled={disabled}
            items={[
                {
                    label: 'Closest match',
                    action: () => {
                        sortBy(SortField.Relevance, Direction.Descending);
                        savePreferences(SortField.Relevance, Direction.Descending);
                    }
                },
                {
                    label: 'Patient name (A-Z)',
                    action: () => {
                        sortBy(SortField.LastNm, Direction.Ascending);
                        savePreferences(SortField.LastNm, Direction.Ascending);
                    }
                },
                {
                    label: 'Patient name (Z-A)',
                    action: () => {
                        sortBy(SortField.LastNm, Direction.Descending);
                        savePreferences(SortField.LastNm, Direction.Descending);
                    }
                },
                {
                    label: 'Date of birth (Ascending)',
                    action: () => {
                        sortBy(SortField.BirthTime, Direction.Ascending);
                        savePreferences(SortField.BirthTime, Direction.Ascending);
                    }
                },
                {
                    label: 'Date of birth (Descending)',
                    action: () => {
                        sortBy(SortField.BirthTime, Direction.Descending);
                        savePreferences(SortField.BirthTime, Direction.Descending);
                    }
                }
            ]}
            label={''}
        />
    );
};

export { SearchResultsListOptions };
