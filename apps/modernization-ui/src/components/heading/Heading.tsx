import classNames from 'classnames';
import styles from './heading.module.scss';

type HeadingLevel = 1 | 2 | 3 | 4 | 5;
type HeadingProps = {
    level: HeadingLevel;
} & JSX.IntrinsicElements['h1'];

const Heading = ({ id, level, className, children, ...remaining }: HeadingProps) => {
    switch (level) {
        case 1:
            return (
                <h1 id={id} className={classNames(styles.heading, className)} {...remaining}>
                    {children}
                </h1>
            );
        case 2:
            return (
                <h2 id={id} className={classNames(styles.heading, className)} {...remaining}>
                    {children}
                </h2>
            );
        case 3:
            return (
                <h3 id={id} className={classNames(styles.heading, className)} {...remaining}>
                    {children}
                </h3>
            );
        case 4:
            return (
                <h4 id={id} className={classNames(styles.heading, className)} {...remaining}>
                    {children}
                </h4>
            );
        case 5:
            return (
                <h5 id={id} className={classNames(styles.heading, className)} {...remaining}>
                    {children}
                </h5>
            );
    }
};

export { Heading };
export type { HeadingLevel };
