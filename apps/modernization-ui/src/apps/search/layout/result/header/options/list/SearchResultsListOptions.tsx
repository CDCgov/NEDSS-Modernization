import { ButtonActionMenu } from 'components/ButtonActionMenu/ButtonActionMenu';
import styles from './search-results-list-options.module.scss';
import { Direction, useSorting } from 'sorting';
import { SortField } from 'generated/graphql/schema';
import { useEffect } from 'react';
import { Button } from 'components/button';
import { Icon } from 'design-system/icon';
import classNames from 'classnames';

type Props = {
    disabled?: boolean;
};

const SearchResultsListOptions = ({ disabled = false }: Props) => {
    const { sortBy, property, direction } = useSorting();

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

    const isActive = (field: SortField, dir: Direction) => property === field && direction === dir;

    const renderSortButton = (field: SortField, dir: Direction, label: string) => (
        <Button
            className={classNames(styles.optionItem, { [styles.active]: isActive(field, dir) })}
            icon={isActive(field, dir) ? <Icon name="check" className={styles.check} /> : undefined}
            labelPosition="right"
            type="button"
            onClick={() => {
                sortBy(field, dir);
                savePreferences(field, dir);
            }}>
            {label}
        </Button>
    );

    return (
        <ButtonActionMenu
            className={styles.option}
            ariaLabel="Sort by list"
            outline
            icon={<Icon className={styles.sortArrow} name="sort_arrow" />}
            disabled={disabled}>
            {renderSortButton(SortField.Relevance, Direction.Descending, 'Closest match')}
            {renderSortButton(SortField.LastNm, Direction.Ascending, 'Patient name (A-Z)')}
            {renderSortButton(SortField.LastNm, Direction.Descending, 'Patient name (Z-A)')}
            {renderSortButton(SortField.BirthTime, Direction.Ascending, 'Date of birth (Ascending)')}
            {renderSortButton(SortField.BirthTime, Direction.Descending, 'Date of birth (Descending)')}
        </ButtonActionMenu>
    );
};

export { SearchResultsListOptions };
