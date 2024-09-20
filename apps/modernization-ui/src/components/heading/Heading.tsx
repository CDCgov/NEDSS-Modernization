import classNames from 'classnames';
import styles from './heading.module.scss';
import { ReactNode } from 'react';

type HeadingLevel = 1 | 2 | 3 | 4 | 5;
type HeadingProps = {
    level: HeadingLevel;
    className?: string;
    children: ReactNode;
};

const Heading = ({ level, className, children }: HeadingProps) => {
    switch (level) {
        case 1:
            return <h1 className={classNames(styles.heading, className)}>{children}</h1>;
        case 2:
            return <h2 className={classNames(styles.heading, className)}>{children}</h2>;
        case 3:
            return <h3 className={classNames(styles.heading, className)}>{children}</h3>;
        case 4:
            return <h4 className={classNames(styles.heading, className)}>{children}</h4>;
        case 5:
            return <h5 className={classNames(styles.heading, className)}>{children}</h5>;
    }
};

export { Heading };
export type { HeadingLevel };
