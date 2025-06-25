import { Button } from 'design-system/button';
import styles from './BackToTopButton.module.scss';
import { Icon } from 'design-system/icon';

export const BackToTopButton = () => {
    const handleClick = () => {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    };

    return (
        <div className={styles.button}>
            <Button
                sizing={'small'}
                secondary={true}
                icon={<Icon name="arrow_upward" sizing="small" aria-label="Back to top icon" className={styles.icon} />}
                onClick={handleClick}>
                Back to top
            </Button>
        </div>
    );
};
