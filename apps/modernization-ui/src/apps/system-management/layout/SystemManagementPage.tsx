import { Heading } from 'components/heading';
import { SearchBar } from 'design-system/search/SearchBar';
import styles from './SystemManagementPage.module.scss';

const SystemManagementPage = () => {
    return (
        <div className={styles.page}>
            <Heading level={1}>System Management </Heading>
            <SearchBar placeholder={'Filter by keyword'} aria-label="Search" />
        </div>
    );
};

export default SystemManagementPage;
