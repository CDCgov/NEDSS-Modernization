import classNames from 'classnames';
import { Button } from 'design-system/button';
import { Sizing } from 'design-system/field';

import styles from './back-to-top.module.scss';

type Target = Pick<Element, 'scrollTo'>;

type BackToTopProps = {
    className?: string;
    sizing?: Sizing;
    target?: Target | null;
};

const BackToTop = ({ className, sizing, target = window }: BackToTopProps) => {
    const handleClick = () => {
        if (target) {
            target.scrollTo({ top: 0, behavior: 'smooth' });
        }
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
