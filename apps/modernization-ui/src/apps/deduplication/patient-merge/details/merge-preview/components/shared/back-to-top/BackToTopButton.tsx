import { Button } from 'design-system/button';
import styles from './BackToTopButton.module.scss';
import { Icon } from 'design-system/icon';

export const BackToTopButton = () => {
    const handleClick = () => {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    };

    return (
        <Button
            sizing="small"
            secondary
            icon={<Icon name="arrow_upward" aria-label="Back to top icon" />}
            className={styles.button}
            onClick={handleClick}>
            Back to top
        </Button>
    );
};
