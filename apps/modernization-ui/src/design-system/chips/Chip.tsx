import { Icon } from '@trussworks/react-uswds';
import classNames from 'classnames';

import styles from './chip.module.scss';

type ChipProps = {
    name: string;
    value: string;
    operator?: string;
    handleClose: () => void;
};

const Chip = ({ name, value, operator, handleClose }: ChipProps) => {
    return (
        <div className={classNames(styles['chip-container'])}>
            <span>{operator ? `${name} ${operator} '${value}'` : `${name}: ${value}`}</span>
            <Icon.Close aria-label="Close chip" onClick={() => handleClose()} className={styles.closeIcon} />
        </div>
    );
};

export { Chip };
