import { ReactNode, useCallback, useId, useState } from 'react';
import { createPortal } from 'react-dom';
import { TooltipMessage, TooltipMessageProps } from './TooltipMessage';

import styles from './tooltip.module.scss';
import classNames from 'classnames';

type Children = (id: string) => ReactNode | ReactNode[];

type Placement = {
    top: number;
    left: number;
};

type TooltipProps = {
    /** The message that is displayed on hover */
    message: string;
    spanClass?: string;
    /** The target element that will display the tooltip when hovered. */
    children: Children;
} & Omit<TooltipMessageProps, 'children'>;

/**
 A brief helpful messages displayed when a user hovers over the `children`.  The `id` of the 
 tooltip is provided to the render function so that the targeted element can be labeled or described by the `Tooltip`.

 @example
<Tooltip message="Tooltip message">
{ (id) => <Icon name="announcement" aria-describedby={id} />}
</Tooltip>
 
 @example
<Tooltip message="View">
    {(id) => (
        <Button                    
            aria-labelledby={id}
            tertiary
            onClick={onView}
            icon={<Icon name="visibility" />}
        />
    )}
</Tooltip>
 
 @return {Tooltip}
 */
const Tooltip = ({ children, message, spanClass, ...remaining }: TooltipProps) => {
    const id = useId();
    const [visible, setVisible] = useState<boolean>(false);
    const [placement, setPlacement] = useState<Placement | undefined>();

    const targeted = useCallback(
        (element: HTMLElement | null) => {
            if (visible && element != null) {
                const { top, left, width } = element.getBoundingClientRect();

                setPlacement({
                    top,
                    left: left + width / 2
                });
            }
        },
        [visible, setPlacement]
    );

    return (
        <span
            className={classNames(styles.container, spanClass)}
            ref={targeted}
            onMouseEnter={() => setVisible(true)}
            onMouseLeave={() => setVisible(false)}>
            {children(id)}
            {createPortal(
                <TooltipMessage id={id} style={{ position: 'fixed', ...placement }} visible={visible} {...remaining}>
                    {message}
                </TooltipMessage>,
                document.body
            )}
        </span>
    );
};

export { Tooltip };
export type { TooltipProps };
