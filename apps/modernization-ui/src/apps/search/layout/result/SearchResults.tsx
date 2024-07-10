import { ReactNode } from 'react';
import { useSearchResultDisplay } from 'apps/search';
import { SearchResultsHeader } from './header/SearchResultsHeader';
import styles from './search-results.module.scss';
import { usePage } from 'page';
import { AlertBanner } from 'alert';
import { Link } from 'react-router-dom';

type Props = {
    children: ReactNode;
};

const SearchResults = ({ children }: Props) => {
    const {
        page: { total }
    } = usePage();

    const { view, terms } = useSearchResultDisplay();

    return (
        <div className={styles.results}>
            <SearchResultsHeader className={styles.header} view={view} total={total} terms={terms} />
            <main className={styles.content}>
                {total > 0 ? (
                    children
                ) : (
                    <div className={styles.noResults}>
                        <AlertBanner type="info" iconSize={4}>
                            <div className={styles.noResultsContent}>
                                <span className={styles.noResultsHeader}> No result found</span>
                                <span className={styles.noResultsSubHeading}>
                                    Try refining your search, or
                                    <Link className={styles.link} to="/add-patient">
                                        {' '}
                                        add a new patient.
                                    </Link>
                                </span>
                            </div>
                        </AlertBanner>
                    </div>
                )}
            </main>

            <div className={styles.pagingation}></div>
        </div>
    );
};

export { SearchResults };
