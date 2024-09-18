import { useEffect, useState } from 'react';
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
    const [activeSection, setActiveSection] = useState<string>('');

    useEffect(() => {
        const sectionElements = sections.map((section) => document.getElementById(section.id));

        const observer = new IntersectionObserver((entries) => {
            entries.forEach((entry) => {
                if (entry.isIntersecting) {
                    setActiveSection(entry.target.id);
                }
            });
        });

        sectionElements.forEach((section) => {
            if (section) observer.observe(section);
        });

        return () => {
            sectionElements.forEach((section) => {
                if (section) observer.unobserve(section);
            });
        };
    }, []);

    const handleScrollToSection = (id: string) => {
        const element = document.getElementById(id);
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
                            onClick={() => handleScrollToSection(section.id)}>
                            {section.label}
                        </a>
                    ))}
                </div>
            </nav>
        </aside>
    );
};
