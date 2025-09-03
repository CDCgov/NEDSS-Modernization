import { Sizing } from 'design-system/field';
import classNames from 'classnames';
import styles from './tag.module.scss';

export type TagVariant = 'default' | 'success' | 'warning' | 'error' | 'info' | 'gray' | 'accent';

export type TagProps = {
    variant?: TagVariant;
    size?: Sizing;
    weight?: 'regular' | 'bold';
} & JSX.IntrinsicElements['div'];

export const Tag = ({ variant = 'default', size = 'medium', weight = 'regular', ...remaining }: TagProps) => {
    return <div className={classNames(styles[variant], styles[size], styles[weight])} {...remaining} />;
};
