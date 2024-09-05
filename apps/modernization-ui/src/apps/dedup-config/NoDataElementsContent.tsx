import { Button } from '@trussworks/react-uswds';
import styles from './NoDataElements.module.scss';

type Props = {
    onConfigClick: () => void;
};

const NoDataElements = ({ onConfigClick }: Props) => {
    return (
        <div className={styles.content}>
            <h3 className={styles.heading}>Data elements not configured</h3>
            <p>
                To get started configuring the algorithm, the data elements the algorithm will use must first be
                configured.
            </p>
            <Button type={'button'} onClick={onConfigClick} className={styles.configButton}>
                Configure data elements
            </Button>
        </div>
    );
};

export default NoDataElements;
