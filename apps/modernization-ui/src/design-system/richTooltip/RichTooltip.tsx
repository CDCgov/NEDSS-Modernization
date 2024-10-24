import React, { RefObject, useEffect, useRef } from 'react';
import { useTooltip } from './useRichTooltip';
import styles from './rich-tooltip.module.scss';

type RichTooltipProps = {
    elementRef: RefObject<HTMLElement>;
    children: React.ReactNode;
    marginTop?: number;
    marginLeft?: number;
};

const RichTooltip = ({ children, elementRef, marginTop, marginLeft }: RichTooltipProps) => {
    const richTooltipRef = useRef<HTMLDivElement>(null);

    const { position, isVisible, onMouseEnter, onMouseLeave } = useTooltip({
        ref: elementRef,
        richTooltipRef,
        marginTop,
        marginLeft
    });

    useEffect(() => {
        const element = elementRef?.current;
        if (element) {
            element.addEventListener('mouseenter', onMouseEnter);
            element.addEventListener('mouseleave', onMouseLeave);
        }
        return () => {
            if (element) {
                element.removeEventListener('mouseenter', onMouseEnter);
                element.removeEventListener('mouseleave', onMouseLeave);
            }
        };
    }, [elementRef, onMouseEnter, onMouseLeave]);

    if (!isVisible) {
        return null;
    }

    return (
        <div
            ref={richTooltipRef}
            className={styles.richtooltipcontainer}
            style={{
                top: position.top,
                left: position.left
            }}>
            {children}
        </div>
    );
};

export default RichTooltip;
