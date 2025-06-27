import React, { useEffect, useRef } from 'react';
import { useLocation } from 'react-router';

import styles from './scroll-to-top.module.scss';

const ScrollToTop = ({ children }: { children: React.ReactNode }) => {
    const { pathname } = useLocation();
    const initialFocusRef = useRef<HTMLDivElement>(null);
    const pageTitle = pathname.split('/').join(' ');

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
            aria-label={`NBS, ${pageTitle || 'Main content'}`}
            className={styles.mainContent}>
            {children}
        </div>
    );
};

export { ScrollToTop };
