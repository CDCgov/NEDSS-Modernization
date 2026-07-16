import { ReactNode } from 'react';
import classNames from 'classnames';
import styles from './full-page-block.module.scss';

// Will not take up full page unless inside an element that will grow to the full space, otherwise defaults to minimum height
const FullPageBlock = ({ className, children }: { className?: string; children?: ReactNode }) => {
    return <div className={classNames(className, styles.layout)}>{children}</div>;
};

export { FullPageBlock };
