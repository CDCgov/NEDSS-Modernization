import { Icon } from '@trussworks/react-uswds';
import { Button } from 'components/button';

import styles from './search-results-list-options.module.scss';

type Props = {
    disabled?: boolean;
};

const SearchResultsListOptions = ({ disabled = false }: Props) => {
    return <Button className={styles.option} outline icon={<Icon.SortArrow />} disabled={disabled} />;
};

export { SearchResultsListOptions };
