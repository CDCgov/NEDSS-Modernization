import React, { useEffect, useRef } from 'react';
import { useLocation } from 'react-router';

import styles from './main-content-container.module.scss';

const MainContentContainer = ({ children }: { children: React.ReactNode }) => {
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
            aria-label="NBS, Main content"
            className={styles.mainContent}>
            {children}
        </div>
    );
};

export { MainContentContainer };
