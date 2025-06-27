import React, { useEffect, useRef } from 'react';
import { useLocation } from 'react-router';

import styles from './scroll-to-top.module.scss';

const ScrollToTop = ({ children, title }: { children: React.ReactNode; title: string }) => {
    const { pathname } = useLocation();
    const initialFocusRef = useRef<HTMLDivElement>(null);
    const lastPathnameRef = useRef<string>('');

    useEffect(() => {
        if (pathname !== lastPathnameRef.current) {
            window.scrollTo(0, 0);
            lastPathnameRef.current = pathname;

            if (initialFocusRef.current) {
                initialFocusRef.current.focus();
            }
        }
    }, [pathname]);

    return (
        <div role="main" ref={initialFocusRef} tabIndex={-1} aria-label={title} className={styles.mainContent}>
            {children}
        </div>
    );
};

export { ScrollToTop };
