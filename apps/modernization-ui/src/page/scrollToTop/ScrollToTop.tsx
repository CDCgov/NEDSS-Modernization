import React, { useEffect, useRef } from 'react';
import { useLocation } from 'react-router';

import styles from './scroll-to-top.module.scss';

const ScrollToTop = ({ children, title }: { children: React.ReactNode; title: string }) => {
    const { pathname } = useLocation();
    const initialFocusRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        window.scrollTo(0, 0);

        if (initialFocusRef.current) {
            initialFocusRef.current.focus();
        }
    }, [pathname]);

    return (
        <div
            role="main"
            ref={initialFocusRef}
            tabIndex={-1}
            aria-label={`NBS, ${title}`}
            className={styles.mainContent}>
            {children}
        </div>
    );
};

export { ScrollToTop };
