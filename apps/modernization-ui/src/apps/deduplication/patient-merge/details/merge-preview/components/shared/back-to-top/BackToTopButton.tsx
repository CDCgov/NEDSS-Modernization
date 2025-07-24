import { Button } from 'design-system/button';
import styles from './BackToTopButton.module.scss';

export const BackToTopButton = () => {
    const handleClick = () => {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    };

    return (
        <Button sizing="small" secondary icon="arrow_upward" className={styles.button} onClick={handleClick}>
            Back to top
        </Button>
    );
};
