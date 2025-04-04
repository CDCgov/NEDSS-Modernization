import { ReactNode } from 'react';

import styles from './search-criteria.module.scss';
import { Sizing } from 'design-system/field';
import classNames from 'classnames';

type Props = {
    children: ReactNode;
    sizing?: Sizing;
};

const SearchCriteria = ({ children, sizing }: Props) => (
    <div className={classNames(styles.criteria, { [styles.small]: sizing === 'small' })}>{children}</div>
);

export { SearchCriteria };
