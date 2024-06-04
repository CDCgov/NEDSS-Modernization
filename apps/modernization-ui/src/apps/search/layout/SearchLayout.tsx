import { ReactNode } from 'react';

import styles from './search-layout.module.scss';
import { Button } from 'components/button';

type Renderer = () => ReactNode;

type Props = {
    navigation: Renderer;
    criteria: Renderer;
    results: Renderer;
};

const SearchLayout = ({ navigation, criteria, results }: Props) => {
    return (
        <section className={styles.search}>
            {navigation()}
            <div className={styles.content}>
                <div className={styles.criteria}>
                    <search>{criteria()}</search>
                    <div className={styles.actions}>
                        <Button type="button">Search</Button>
                        <Button type="button" outline>
                            Clear all
                        </Button>
                    </div>
                </div>
                <main>{results()}</main>
            </div>
        </section>
    );
};

export { SearchLayout };
