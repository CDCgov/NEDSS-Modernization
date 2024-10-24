import { RefObject, useCallback, useMemo, useState } from 'react';

type UseTooltipProps = {
    ref: RefObject<HTMLElement>;
    richTooltipRef: RefObject<HTMLDivElement>;
    marginTop?: number;
    marginLeft?: number;
};

type Position = {
    top?: number;
    left?: number;
    width?: number;
};

export function useTooltip({ ref, richTooltipRef, marginTop, marginLeft }: UseTooltipProps) {
    const [isVisible, setIsVisible] = useState<boolean>(false);
    const [position, setPosition] = useState<Position>({});

    const calcBottomLeftPosition = (top: number, left: number, width: number) => {
        // The left is calculated from the center of the element which the tooltip parent element is attached to.
        // The offset is calculated then from subtracting half of that parent element's width to align with the start of the parent element.
        const leftOffset = left - width / 2;
        return { top: top, left: leftOffset, width: width };
    };

    useMemo(() => {
        if (!ref.current) {
            return;
        }

        if (isVisible) {
            const { top, left, width } = ref.current.getBoundingClientRect();
            setPosition({ ...calcBottomLeftPosition(marginTop || top, marginLeft || left, width) });
        }

        if (!isVisible) {
            setPosition({});
        }
    }, [isVisible, ref, richTooltipRef]);

    const onMouseEnter = useCallback(() => {
        setIsVisible(true);
    }, []);

    const onMouseLeave = useCallback(() => {
        setIsVisible(false);
    }, []);

    return {
        position: {
            top: position.top ?? 0,
            left: position.left ?? 0,
            width: position.width ?? 0
        },
        isVisible,
        onMouseEnter,
        onMouseLeave
    };
}
