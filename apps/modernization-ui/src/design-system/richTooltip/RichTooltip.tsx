import React, { RefObject, useEffect, useRef } from 'react';
import { useTooltip } from './useRichTooltip';
import styles from './rich-tooltip.module.scss';

type RichTooltipProps = {
    anchorRef: RefObject<HTMLElement>;
    children: React.ReactNode;
    marginTop?: number;
    marginLeft?: number;
};

/**
 * Rich Tooltip component that provides a full React Node outlet for the tooltip that you wish to display.
 * The children of the tooltip component is the content of the tooltip itself that is shown or hidden on mouseover.
 * The anchorRef is the ref to the anchor element that is permanently displayed to the user (i.e an icon or button).
 * You can override the default placement of the tooltip using the optional marginTop and marginLeft parameters.
 *
 * @param {React.ReactNode} param0.children
 * @param {RefObject<HTMLElement>} param0.anchorRef
 * @param {number} [param0.marginTop]
 * @param {number} [param0.marginLeft]
 * @return {JSX.Element}
 */
const RichTooltip = ({ children, anchorRef, marginTop, marginLeft }: RichTooltipProps) => {
    const richTooltipRef = useRef<HTMLDivElement>(null);

    const { position, isVisible, onMouseEnter, onMouseLeave } = useTooltip({
        anchorRef,
        richTooltipRef,
        marginTop,
        marginLeft
    });

    useEffect(() => {
        const element = anchorRef?.current;
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
    }, [anchorRef, onMouseEnter, onMouseLeave]);

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
