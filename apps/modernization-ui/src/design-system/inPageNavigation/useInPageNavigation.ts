import { useEffect } from 'react';
import styles from './InPageNavigation.module.scss';
import { focusedTarget } from 'utils';

const useInPageNavigation = (threshold: number = 0) => {
    useEffect(() => {
        const sections = Array.from(document.querySelectorAll('section[id]'));

        const scrollHandler = (entries: IntersectionObserverEntry[]) => {
            entries.forEach((entry) => {
                const section = entry.target as HTMLElement;
                const sectionId = section.id;
                const sectionLink = document.querySelector(`a[id="inPageNav-${sectionId}"]`);
                if (entry.intersectionRatio > threshold) {
                    section?.classList.add(styles.active);
                    sectionLink?.classList.add(styles.active);
                } else {
                    section?.classList?.remove(styles.active);
                    sectionLink?.classList?.remove(styles.active);
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

        const sectionListeners = sections.map((section) => {
            const sectionId = section.id;
            const sectionLink = document.querySelector(`a[id="inPageNav-${sectionId}"]`);
            if (sectionLink) {
                const clickEventListener = (event: Event) => {
                    event.preventDefault();
                    smoothScroll(section as HTMLElement);
                };

                const enterOrSpaceEventListener = (event: Event) => {
                    const keyboardEvent = event as KeyboardEvent;
                    if (keyboardEvent.key == 'Enter' || keyboardEvent.key === ' ') {
                        event.preventDefault();
                        smoothScroll(section as HTMLElement);
                        focusedTarget(`${section.id}-title`);
                    }
                };

                sectionLink.addEventListener('click', clickEventListener);
                sectionLink.addEventListener('keydown', enterOrSpaceEventListener);

                return { sectionLink, clickEventListener, enterOrSpaceEventListener };
            }
        });

        return () => {
            observer.disconnect();
            sectionListeners.forEach((section) => {
                if (section) {
                    const { sectionLink, clickEventListener, enterOrSpaceEventListener } = section;
                    sectionLink.removeEventListener('click', clickEventListener);
                    sectionLink.removeEventListener('keydown', enterOrSpaceEventListener);
                }
            });
        };
    }, [threshold]);
};

export default useInPageNavigation;
