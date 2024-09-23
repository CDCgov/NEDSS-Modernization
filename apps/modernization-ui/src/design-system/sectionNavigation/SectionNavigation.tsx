import React, { useCallback, useEffect, useState } from 'react';
import styles from './sectionNavigation.module.scss';
import classNames from 'classnames';

export interface NavSection {
    id: string;
    label: string;
}

interface SectionNavigationProps {
    sections: NavSection[];
    title?: string;
}

export const SectionNavigation: React.FC<SectionNavigationProps> = ({ sections, title = 'On this page' }) => {
    const [activeSection, setActiveSection] = useState<string>(sections[0]?.id);

    const handleIntersection = useCallback((entries: IntersectionObserverEntry[]) => {
        entries.forEach((entry) => {
            if (entry.isIntersecting) {
                setActiveSection(entry.target.id);
            }
        });
    }, []);

    useEffect(() => {
        const observer = new IntersectionObserver(handleIntersection, {
            root: null,
            rootMargin: '0px',
            threshold: 0.5
        });

        sections.forEach((section) => {
            const element = document.getElementById(section.id);
            if (element) observer.observe(element);
        });

        return () => observer.disconnect();
    }, [handleIntersection, sections]);

    const handleClick = (sectionId: string) => (e: React.MouseEvent) => {
        e.preventDefault();
        const element = document.getElementById(sectionId);
        if (element) {
            element.scrollIntoView({ behavior: 'smooth', block: 'start' });
            setActiveSection(sectionId);
        }
    };

    return (
        <nav>
            <div className={styles.navTitle}>{title}</div>
            <div className={styles.navOptions}>
                {sections.map((section) => (
                    <a
                        key={section.id}
                        href={`#${section.id}`}
                        className={classNames(styles.navOption, { [styles.visible]: activeSection === section.id })}
                        onClick={handleClick(section.id)}>
                        {section.label}
                    </a>
                ))}
            </div>
        </nav>
    );
};
