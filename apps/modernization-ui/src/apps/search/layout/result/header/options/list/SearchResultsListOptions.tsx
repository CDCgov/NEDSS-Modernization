import { ButtonActionMenu } from 'components/ButtonActionMenu/ButtonActionMenu';
import { Icon } from '@trussworks/react-uswds';
import styles from './search-results-list-options.module.scss';
import { Direction, useSorting } from 'sorting';
import { SortField } from 'generated/graphql/schema';

type Props = {
    disabled?: boolean;
};

const SearchResultsListOptions = ({ disabled = false }: Props) => {
    const { sortBy } = useSorting();

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
                    }
                },
                {
                    label: 'Patient name (A-Z)',
                    action: () => {
                        sortBy(SortField.LastNm, Direction.Ascending);
                    }
                },
                {
                    label: 'Patient name (Z-A)',
                    action: () => {
                        sortBy(SortField.LastNm, Direction.Descending);
                    }
                },
                {
                    label: 'Date of birth (Ascending)',
                    action: () => {
                        sortBy(SortField.BirthTime, Direction.Ascending);
                    }
                },
                {
                    label: 'Date of birth (Descending)',
                    action: () => {
                        sortBy(SortField.BirthTime, Direction.Descending);
                    }
                }
            ]}
            label={''}
        />
    );
};

export { SearchResultsListOptions };
