import { Button, Icon } from '@trussworks/react-uswds';
import classNames from 'classnames';
import { Direction } from 'libs/sorting';
import { Sorting } from './useTableSorting';
import { Header } from './Table';

import styles from './table.module.scss';

type TableHeaderProps = {
    sorting: Sorting;
    header: Header;
};

const TableHeader = ({ sorting, header }: TableHeaderProps) => {
    const direction = sorting.currentDirection(header.name);
    const ariaSort = resolveSortAria(direction);

    return (
        <th
            className={classNames({ [styles.sorted]: direction !== Direction.None })}
            {...(ariaSort && { 'aria-sort': ariaSort })}>
            <div>
                {header.name}
                {header.sortable && (
                    <Button
                        disabled={!sorting.enabled}
                        className="usa-button--unstyled"
                        type={'button'}
                        aria-label="sort"
                        onClick={() => sorting.toggleSort(header.name)}>
                        {resolveSortIcon(direction)}
                    </Button>
                )}
            </div>
        </th>
    );
};

const resolveSortIcon = (direction: Direction) => {
    switch (direction) {
        case Direction.Ascending:
            return <Icon.ArrowDownward color="black" />;
        case Direction.Descending:
            return <Icon.ArrowUpward color="black" />;
        default:
            return <Icon.SortArrow color="black" />;
    }
};

const resolveSortAria = (direction: Direction) => {
    switch (direction) {
        case Direction.Ascending:
            return 'ascending';
        case Direction.Descending:
            return 'descending';
        default:
            return;
    }
};

export { TableHeader };
