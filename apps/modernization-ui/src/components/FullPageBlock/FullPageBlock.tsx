import { ReactNode } from 'react';
import classNames from 'classnames';
import styles from './full-page-block.module.scss';

// Needs to be inside a space that will grow to space, otherwise defaults to minimum height
const FullPageBlock = ({ className, children }: { className?: string; children?: ReactNode }) => {
    return <div className={classNames(className, styles.layout)}>{children}</div>;
};

export { FullPageBlock };
