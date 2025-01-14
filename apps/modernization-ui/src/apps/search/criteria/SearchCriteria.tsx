import { ReactNode } from 'react';

import styles from './search-criteria.module.scss';

type Props = {
    children: ReactNode;
};

const SearchCriteria = ({ children }: Props) => <div className={styles.criteria}>{children}</div>;

export { SearchCriteria };
