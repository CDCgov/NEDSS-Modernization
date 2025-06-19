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

        const smoothScrollAndFocus = (section: HTMLElement) => {
            // First scroll to the section.
            section.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        };

        sections.forEach((section) => {
            const sectionId = section.id;
            const sectionLink = document.querySelector(`a[id="inPageNav-${sectionId}"]`);
            if (sectionLink) {
                sectionLink.addEventListener('click', (event) => {
                    event.preventDefault();
                    smoothScrollAndFocus(section as HTMLElement);
                });

                sectionLink.addEventListener('keydown', (event) => {
                    const keyboardEvent = event as KeyboardEvent;
                    if (keyboardEvent.key == 'Enter' || keyboardEvent.key === ' ') {
                        event.preventDefault();
                        smoothScrollAndFocus(section as HTMLElement);
                        focusedTarget(`${section.id}-title`);
                    }
                });
            }
        });

        return () => observer.disconnect();
    }, [threshold]);
};

export default useInPageNavigation;
