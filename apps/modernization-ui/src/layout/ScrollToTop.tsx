import { ReactNode, useEffect, useState, useRef } from 'react';
import { useLocation } from 'react-router';

const ScrollToTop = ({ children }: { children: ReactNode }) => {
    const location = useLocation();
    const [initialTabPress, setInitialTabPress] = useState(false);
    const [prevPathname, setPrevPathname] = useState(location.pathname);
    const focusRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        window.scrollTo(0, 0);

        if (location.pathname !== prevPathname) {
            setInitialTabPress(false);
            setPrevPathname(location.pathname);

            if (focusRef.current) {
                focusRef.current.focus();
            }
        }

        const handleFirstTab = (event: KeyboardEvent) => {
            const activeElement = document.activeElement;
            const isBodyOrNonFocusable = activeElement === document.body || activeElement === null;

            if (event.code === 'Tab') {
                if (!initialTabPress && isBodyOrNonFocusable) {
                    setInitialTabPress(true);
                    const skipLinkElement: HTMLElement | null = document.querySelector('.usa-skipnav');
                    if (skipLinkElement) {
                        requestAnimationFrame(() => {
                            skipLinkElement.focus();
                        });
                    }
                }
                window.removeEventListener('keydown', handleFirstTab);
            }
        };

        window.addEventListener('keydown', handleFirstTab);

        return () => {
            window.removeEventListener('keydown', handleFirstTab);
        };
    }, [location.pathname, initialTabPress, prevPathname]);

    return (
        <>
            <div ref={focusRef} tabIndex={-1} style={{ outline: 'none' }} aria-hidden="true" />
            {children}
        </>
    );
};

export { ScrollToTop };
