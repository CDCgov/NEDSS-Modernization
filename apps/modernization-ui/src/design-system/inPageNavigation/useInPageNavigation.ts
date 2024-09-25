import { useEffect } from 'react';
import styles from './InPageNavigation.module.scss';

const useInPageNavigation = () => {
    useEffect(() => {
        const sections = Array.from(document.querySelectorAll('section[id]'));

        const scrollHandler = (entries: IntersectionObserverEntry[]) => {
            entries.forEach((entry) => {
                const section = entry.target as HTMLElement;
                const sectionId = section.id;
                const sectionLink = document.querySelector(`a[href="#${sectionId}"]`);
                if (entry.intersectionRatio > 0) {
                    section?.classList.add(styles.visible);
                    sectionLink?.classList.add(styles.visible);
                } else {
                    section?.classList?.remove(styles.visible);
                    sectionLink?.classList?.remove(styles.visible);
                }
            });
        };

        const observer = new IntersectionObserver(scrollHandler);

        sections.forEach((section) => observer.observe(section));

        const smoothScroll = (element: HTMLElement) => {
            element.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        };

        sections.forEach((section) => {
            const sectionId = section.id;
            const sectionLink = document.querySelector(`a[href="#${sectionId}"]`);
            if (sectionLink) {
                sectionLink.addEventListener('click', (event) => {
                    event.preventDefault();
                    smoothScroll(section as HTMLElement);
                });
            }
        });

        return () => observer.disconnect();
    }, []);
};

export default useInPageNavigation;
