import classNames from 'classnames';
import styles from './heading.module.scss';

type HeadingProps = {
    level: 1 | 2 | 3 | 4 | 5;
    className?: string;
    children: string;
};

const Heading = ({ level, className, children }: HeadingProps) => (
    <span
        className={classNames(styles.heading, className, {
            [styles.one]: level === 1,
            [styles.two]: level === 2,
            [styles.three]: level === 3,
            [styles.four]: level === 4,
            [styles.five]: level === 5
        })}>
        {children}
    </span>
);

export { Heading };
