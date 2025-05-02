import classNames from 'classnames';

import styles from './chip.module.scss';
import { ActionIcon } from 'design-system/icon';

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
            <ActionIcon
                name={'close'}
                aria-label={`Close ${name}`}
                tabIndex={0}
                onAction={handleClose}
                className={styles.closeIcon}
            />
        </div>
    );
};

export { Chip };
