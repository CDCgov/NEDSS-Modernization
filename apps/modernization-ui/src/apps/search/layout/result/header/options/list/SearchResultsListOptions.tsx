import { ButtonActionMenu } from 'components/ButtonActionMenu/ButtonActionMenu';
import { Icon } from '@trussworks/react-uswds';
import styles from './search-results-list-options.module.scss';
import { SortDirection, SortField, useSearchResultDisplay } from 'apps/search/useSearchResultDisplay';

type Props = {
    disabled?: boolean;
};

const SearchResultsListOptions = ({ disabled = false }: Props) => {
    const { setSort } = useSearchResultDisplay();

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
                        setSort({
                            sortField: SortField.Relevance
                        });
                    }
                },
                {
                    label: 'Patient name (A-Z)',
                    action: () => {
                        setSort({
                            sortDirection: SortDirection.Asc,
                            sortField: SortField.LastNm
                        });
                    }
                },
                {
                    label: 'Patient name (Z-A)',
                    action: () => {
                        setSort({
                            sortDirection: SortDirection.Desc,
                            sortField: SortField.LastNm
                        });
                    }
                },
                {
                    label: 'Date of birth (Ascending)',
                    action: () => {
                        setSort({
                            sortDirection: SortDirection.Asc,
                            sortField: SortField.BirthTime
                        });
                    }
                },
                {
                    label: 'Date of birth (Descending)',
                    action: () => {
                        setSort({
                            sortDirection: SortDirection.Desc,
                            sortField: SortField.BirthTime
                        });
                    }
                }
            ]}
            label={''}
        />
    );
};

export { SearchResultsListOptions };
