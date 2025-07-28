import styles from './search-landing.module.scss';

const SearchLanding = () => {
    return (
        <main className={styles.landing}>
            <header>
                <h2>Perform a search to see results</h2>
                <span>No inquiry has been submitted.</span>
            </header>
        </main>
    );
};

export { SearchLanding };
