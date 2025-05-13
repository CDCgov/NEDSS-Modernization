import classNames from 'classnames';
import { StandardButtonProps } from './Button';

import styles from './Button.module.scss';

const buttonClassnames = ({
    className,
    sizing,
    icon,
    labelPosition = 'right',
    tertiary = false,
    secondary = false,
    destructive = false,
    children
}: StandardButtonProps) =>
    classNames(
        styles.button,
        {
            [styles.secondary]: secondary,
            [styles.destructive]: destructive,
            [styles.tertiary]: tertiary,
            [styles.icon]: Boolean(icon && !children),
            [styles['icon-last']]: labelPosition === 'left',
            [styles.small]: sizing === 'small'
        },
        className
    );

export { buttonClassnames };
