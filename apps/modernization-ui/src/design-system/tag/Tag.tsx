import React from 'react';
import { Sizing } from 'design-system/field';
import styles from './tag.module.scss';
import classNames from 'classnames';

export type TagVariant = 'default' | 'success' | 'warning' | 'error' | 'info' | 'gray';

export type TagProps = {
    children: React.ReactNode | React.ReactNode[];
    variant?: TagVariant;
    size?: Sizing;
    weight?: 'regular' | 'bold';
};

export const Tag = ({ children, variant = 'default', size = 'medium', weight = 'regular' }: TagProps) => {
    return <div className={classNames(styles[variant], styles[size], styles[weight])}>{children}</div>;
};
