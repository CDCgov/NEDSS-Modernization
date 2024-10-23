import { Tooltip } from '@trussworks/react-uswds';
import { ReactNode } from 'react';
import styles from './rich-tooltip.module.scss';

type Props = {
    labelTitle: string;
    labelText: string;
    position?: 'bottom' | 'top' | 'left' | 'right' | undefined;
    children: ReactNode;
};

const RichTooltip = ({ labelTitle, labelText, position, children }: Props) => {
    return (
        <div className={styles.tooltipContainer}>
            <Tooltip
                position={position}
                label={
                    <div className={styles.label}>
                        <p>
                            <b>{labelTitle}</b>
                            <br />
                            {labelText}
                        </p>
                    </div>
                }>
                {children}
            </Tooltip>
        </div>
    );
};

export default RichTooltip;
