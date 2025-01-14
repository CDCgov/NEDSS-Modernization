import styles from './search-landing.module.scss';

const SearchLanding = () => {
    return (
        <div className={styles.landing}>
            <header>
                <h2>Perform a search to see results</h2>
                <span>No inquiry has been submitted.</span>
            </header>
        </div>
    );
};

export { SearchLanding };
