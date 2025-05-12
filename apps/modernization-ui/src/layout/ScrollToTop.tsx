import { ReactNode, useEffect, useState } from 'react';
import { useLocation } from 'react-router';

const ScrollToTop = ({ children }: { children: ReactNode }) => {
    const location = useLocation();
    const [tabPressed, setTabPressed] = useState(false);
    const [prevPathname, setPrevPathname] = useState(location.pathname);

    useEffect(() => {
        window.scrollTo(0, 0);

        if (location.pathname !== prevPathname) {
            setTabPressed(false);
            setPrevPathname(location.pathname);
        }

        const handleFirstTab = (event: KeyboardEvent) => {
            const activeElement = document.activeElement;
            const isBodyOrNonFocusable = activeElement === document.body || activeElement === null;

            if (event.code === 'Tab' && !tabPressed && isBodyOrNonFocusable) {
                setTabPressed(true);
                const skipLinkElement: HTMLElement | null = document.querySelector('.usa-skipnav');
                if (skipLinkElement) {
                    requestAnimationFrame(() => {
                        skipLinkElement.focus();
                    });
                }
                window.removeEventListener('keydown', handleFirstTab);
            }
        };

        window.addEventListener('keydown', handleFirstTab);

        return () => {
            window.removeEventListener('keydown', handleFirstTab);
        };
    }, [location.pathname, tabPressed, prevPathname]);

    return <>{children}</>;
};

export { ScrollToTop };