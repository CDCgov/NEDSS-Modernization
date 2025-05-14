import { useEffect, useRef } from 'react';
import { useLocation } from 'react-router';

import styles from './scroll-to-top.module.scss';

const ScrollToTop = () => {
    const { pathname } = useLocation();
    const initialFocusRef = useRef<HTMLElement>(null);

    useEffect(() => {
        window.scrollTo(0, 0);

        if (initialFocusRef.current) {
            initialFocusRef.current.focus();
        }
    }, [pathname]);

    return (
        <span id="initial-focus" ref={initialFocusRef} tabIndex={-1} aria-hidden="true" className={styles.initial} />
    );
};

export { ScrollToTop };
