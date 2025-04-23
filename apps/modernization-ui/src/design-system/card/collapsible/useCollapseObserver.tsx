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
                // Temporarily disable overflow to measure the full height
                //const previousOverflow = contentRef.current.style.overflow;
                //contentRef.current.style.overflow = 'visible';
                const currentHeight = contentRef.current.scrollHeight;

                setHeight(!collapsible || !currentHeight ? 'auto' : collapsed ? '0' : `${currentHeight}px`);

                // Restore the previous overflow style
                //contentRef.current.style.overflow = previousOverflow;
            }
        };

        // Create observers to handle resize and mutation events
        // These observers will detect changes in size of the content
        const resizeObserver = new ResizeObserver(updateHeight);
        const mutationObserver = new MutationObserver(updateHeight);

        if (contentRef.current) {
            resizeObserver.observe(contentRef.current);
            mutationObserver.observe(contentRef.current, { childList: true, subtree: true, characterData: true });
        }

        // Initial height update
        updateHeight();

        return () => {
            if (contentRef.current) {
                resizeObserver.unobserve(contentRef.current);
                mutationObserver.disconnect();
            }
        };
    }, [collapsible, collapsed]);

    // if (contentRef?.current) {
    //     // Set the height of the contentRef element
    //     contentRef.current.style.maxHeight = height;
    // }
    // return final height to the component
    return height;
};

export { useCollapseObserver };
