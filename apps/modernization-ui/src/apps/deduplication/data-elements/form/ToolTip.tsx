import React, { RefObject, useEffect, useRef, useState } from 'react';
import styles from './ToolTip.module.scss';

type TooltipProps = {
    anchorRef: RefObject<HTMLElement>;
    children: React.ReactNode;
    offsetX?: number;
    offsetY?: number;
};

const Tooltip = ({ children, anchorRef, offsetX = 10, offsetY = 10 }: TooltipProps) => {
    const tooltipRef = useRef<HTMLDivElement>(null);
    const [position, setPosition] = useState({ top: 0, left: 0 });
    const [isVisible, setIsVisible] = useState(false);

    useEffect(() => {
        const anchor = anchorRef.current;
        if (!anchor) return;

        const updatePosition = (event: MouseEvent) => {
            setPosition({
                top: event.clientY + offsetY,
                left: event.clientX + offsetX
            });
        };

        const showTooltip = (event: MouseEvent) => {
            updatePosition(event);
            setIsVisible(true);
            document.addEventListener('mousemove', updatePosition);
        };

        const hideTooltip = () => {
            setIsVisible(false);
            document.removeEventListener('mousemove', updatePosition);
        };

        anchor.addEventListener('mouseenter', showTooltip);
        anchor.addEventListener('mouseleave', hideTooltip);

        return () => {
            anchor.removeEventListener('mouseenter', showTooltip);
            anchor.removeEventListener('mouseleave', hideTooltip);
            document.removeEventListener('mousemove', updatePosition);
        };
    }, [anchorRef, offsetX, offsetY]);

    if (!isVisible) return null;

    return (
        <div
            ref={tooltipRef}
            className={styles.tooltipContainer}
            style={{ position: 'fixed', top: position.top, left: position.left }}>
            {children}
        </div>
    );
};

export default Tooltip;
