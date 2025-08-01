import { ReactNode, useEffect, useRef } from 'react';

type VisibleWrapperProps = {
    children: ReactNode;
    onVisibilityChange: (visible: boolean) => void;
};

const VisibleWrapper = ({ children, onVisibilityChange }: VisibleWrapperProps) => {
    const ref = useRef<HTMLDivElement | null>(null);

    useEffect(() => {
        const visibleNow = ref.current ? ref.current.offsetParent !== null : false;
        onVisibilityChange(visibleNow);
    }, [children, onVisibilityChange]);

    return <div ref={ref}>{children}</div>;
};

export default VisibleWrapper;
