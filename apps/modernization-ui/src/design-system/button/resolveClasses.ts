import classNames from 'classnames';
import { isLabeled, StandardButtonProps } from './buttons';

import styles from './Button.module.scss';

const resolveClasses = (props: StandardButtonProps) => {
    const labeled = isLabeled(props);

    return classNames(
        styles.button,
        {
            [styles.active]: props.active,
            [styles.secondary]: props.secondary,
            [styles.destructive]: props.destructive,
            [styles.tertiary]: props.tertiary,
            [styles.icon]: !labeled,
            [styles['icon-last']]: labeled && props.labelPosition === 'left',
            [styles.small]: props.sizing === 'small'
        },
        props.className
    );
};

export { resolveClasses };
