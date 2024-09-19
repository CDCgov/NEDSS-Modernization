import React from 'react';
import { useEffect, useState, useCallback } from 'react';
import styles from './extended-patient-nav.module.scss';

const sections = [
    { id: 'section-Administrative', label: 'Administrative' },
    { id: 'section-Name', label: 'Name' },
    { id: 'section-Address', label: 'Address' },
    { id: 'section-PhoneAndEmail', label: 'Phone & email' },
    { id: 'section-Identification', label: 'Identification' },
    { id: 'section-Race', label: 'Race' },
    { id: 'section-Ethnicity', label: 'Ethnicity' },
    { id: 'section-SexAndBirth', label: 'Sex & birth' },
    { id: 'section-Mortality', label: 'Mortality' },
    { id: 'section-General', label: 'General patient information' }
];

export const AddPatientExtendedNav = () => {
    const [activeSection, setActiveSection] = useState<string>('section-Administrative');

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
