import { ReactNode } from 'react';

import classNames from 'classnames';
import { Sizing } from 'design-system/field';

import styles from './search-criteria.module.scss';

type Props = {
    children: ReactNode;
    sizing?: Sizing;
};

const SearchCriteria = ({ children, sizing }: Props) => (
    <div className={classNames(styles.criteria, { [styles.small]: sizing === 'small' })}>{children}</div>
);

export { SearchCriteria };
