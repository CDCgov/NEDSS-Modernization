import classNames from 'classnames';
import { Button } from 'design-system/button';
import { Sizing } from 'design-system/field';

import styles from './back-to-top.module.scss';

type BackToTopProps = {
    className?: string;
    sizing?: Sizing;
};

const BackToTop = ({ className, sizing }: BackToTopProps) => {
    const handleClick = () => {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    };

    return (
        <div className={classNames(styles.back, className)}>
            <Button className={styles.button} sizing={sizing} secondary icon="arrow_upward" onClick={handleClick}>
                Back to top
            </Button>
        </div>
    );
};

export { BackToTop };
