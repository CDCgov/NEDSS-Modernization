import { useEffect, useRef } from 'react';
import { useLocation } from 'react-router';

type ScrollToTopProps = { title: string };

const ScrollToTop = ({ title }: ScrollToTopProps) => {
    const { pathname } = useLocation();
    const initialFocusRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        window.scrollTo(0, 0);

        if (initialFocusRef.current) {
            initialFocusRef.current.focus();
        }
    }, [pathname]);

    return <span role="region" ref={initialFocusRef} tabIndex={-1} aria-label={title} />;
};

export { ScrollToTop };
