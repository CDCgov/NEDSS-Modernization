import classNames from 'classnames';

import styles from './chip.module.scss';
import { Icon } from 'design-system/icon';

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
            <Icon
                name={'close'}
                aria-label={`Close ${name}`}
                onClick={() => handleClose()}
                className={styles.closeIcon}
                tabIndex={0}
                onAccessibleKeyDown={() => handleClose()}
            />
        </div>
    );
};

export { Chip };
