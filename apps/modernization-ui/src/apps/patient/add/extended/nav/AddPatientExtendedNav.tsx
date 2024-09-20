import React from 'react';
import { useEffect, useState, useCallback } from 'react';
import styles from './extended-patient-nav.module.scss';
import { Section } from '../sections';

export const AddPatientExtendedNav = ({ sections }: { sections: Section[] }) => {
    const [activeSection, setActiveSection] = useState<string>('administrative');

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
    }, [handleIntersection]);

    const handleClick = (sectionId: string) => (e: React.MouseEvent) => {
        e.preventDefault();
        const element = document.getElementById(sectionId);
        if (element) {
            element.scrollIntoView({ behavior: 'smooth', block: 'start' });
        }
    };

    return (
        <aside>
            <nav>
                <div className={styles.navTitle}>On this page</div>
                <div className={styles.navOptions}>
                    {sections.map((section) => (
                        <a
                            key={section.id}
                            href={`#${section.id}`}
                            className={activeSection === section.id ? styles.visible : ''}
                            onClick={handleClick(section.id)}>
                            {section.label}
                        </a>
                    ))}
                </div>
            </nav>
        </aside>
    );
};
