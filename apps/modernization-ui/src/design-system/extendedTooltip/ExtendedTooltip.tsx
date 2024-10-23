import { Tooltip } from '@trussworks/react-uswds';
import { ReactNode } from 'react';
import styles from './extended-tooltip.module.scss';

type Props = { label?: string; children: ReactNode };

const ExtendedTooltip = ({ children }: Props) => {
    return (
        <div className={styles.tooltipContainer}>
            <Tooltip
                position="bottom"
                label={
                    <div className={styles.label}>
                        <p>
                            <b>We are modernizing search</b>
                            <br />
                            To perform an event search or save a new custom queue, continue using classic search.
                        </p>
                    </div>
                }>
                <span>{children}</span>
            </Tooltip>
        </div>
    );
};

export default ExtendedTooltip;
