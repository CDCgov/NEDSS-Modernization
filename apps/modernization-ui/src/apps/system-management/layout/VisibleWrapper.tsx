import React, { useEffect, useRef } from 'react';

type Props = {
    children: React.ReactNode;
    onVisibilityChange: (visible: boolean) => void;
};

const VisibleWrapper = ({ children, onVisibilityChange }: Props) => {
    const ref = useRef<HTMLDivElement>(null);

    useEffect(() => {
        const checkVisibility = () => {
            const el = ref.current;
            const isVisible = !!(el && el.offsetParent !== null && el.offsetHeight > 0);
            onVisibilityChange(isVisible);
        };

        checkVisibility();

        const observer = new MutationObserver(checkVisibility);
        if (ref.current) {
            observer.observe(ref.current, { childList: true, subtree: true });
        }

        return () => observer.disconnect();
    }, [children, onVisibilityChange]);

    return <div ref={ref}>{children}</div>;
};

export default VisibleWrapper;
