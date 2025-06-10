import classNames from 'classnames';
import { Button } from 'design-system/button';

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
            <Button icon="close" aria-label={`Close ${name}`} onClick={handleClose} className={styles.closeIcon} />
        </div>
    );
};

export { Chip };
