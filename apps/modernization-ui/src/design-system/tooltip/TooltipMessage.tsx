import classNames from 'classnames';
import styles from './tooltip-message.module.scss';

type TooltipMessageProps = {
    children: string;
    position?: 'top' | 'right' | 'bottom' | 'left';
    offset?: 'left' | 'right' | 'center';
    visible?: boolean;
} & JSX.IntrinsicElements['span'];

const TooltipMessage = ({ children, position = 'top', offset, visible = true, ...remaining }: TooltipMessageProps) => (
    <span
        className={classNames(styles.tooltip, { [styles.visible]: visible })}
        role="tooltip"
        title={children}
        data-tooltip-position={position}
        data-tooltip-offset={offset}
        data-tooltip-message={children}
        {...remaining}
    />
);

export { TooltipMessage };
export type { TooltipMessageProps };
