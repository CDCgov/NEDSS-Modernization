import { RefObject, useEffect, useState } from 'react';

type CollapseObserverProps<T extends HTMLElement> = {
    contentRef: RefObject<T>;
    collapsible: boolean;
    collapsed: boolean;
};

/**
 * Observes the height of a collapsible element and returns the appropriate height value.
 * @param {CollapseObserverProps} props - The properties for the observer.
 * @returns {string} The current HTML height value of the ref object being observed i.e. 'auto', '0', '100px'
 */
const useCollapseObserver = <T extends HTMLElement>({
    contentRef,
    collapsible,
    collapsed
}: CollapseObserverProps<T>): string => {
    const [height, setHeight] = useState('auto');

    useEffect(() => {
        const updateHeight = () => {
            if (contentRef.current) {
                const currentHeight = contentRef.current.scrollHeight;
                setHeight(!collapsible || !currentHeight ? 'auto' : collapsed ? '0' : `${currentHeight}px`);
            }
        };

        // Initial height update
        updateHeight();

        if (!collapsible) {
            return;
        }
        // Create observers to handle resize and mutation events
        // These observers will detect changes in size of the content
        const resizeObserver = new ResizeObserver(updateHeight);
        const mutationObserver = new MutationObserver(updateHeight);

        if (contentRef.current) {
            resizeObserver.observe(contentRef.current);
            mutationObserver.observe(contentRef.current, { childList: true, subtree: true, characterData: true });
        }

        return () => {
            if (contentRef.current) {
                resizeObserver.unobserve(contentRef.current);
                mutationObserver.disconnect();
            }
        };
    }, [collapsible, collapsed]);

    // return final height to the component
    return height;
};

export { useCollapseObserver };
