import { Icon } from '@trussworks/react-uswds';
import classNames from 'classnames';
import styles from './chip.module.scss';

type ChipProps = {
    name: string;
    value: string;
    handleClose: () => void;
};

const Chip = ({ name, value, handleClose }: ChipProps) => {
    return (
        <div className={classNames(styles['chip-container'])} data-testid={`${name}-chip`}>
            <span className={styles.name}>
                {name}: {value}
            </span>
            <Icon.Close data-testid="close-icon" onClick={() => handleClose()} className={styles.closeIcon} />
        </div>
    );
};

export default Chip;
