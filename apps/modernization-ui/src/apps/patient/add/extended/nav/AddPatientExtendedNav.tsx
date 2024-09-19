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
    const [activeSection, setActiveSection] = useState<string>('section-Administrative');

    useEffect(() => {
        const sections = Array.from(document.querySelectorAll('section[id]'));
        const scrollHandler: any = (entries: any) => {
            entries.forEach((entry: any) => {
                const section: any = entry.target;
                const sectionTop = section.getBoundingClientRect().top + window.scrollY;
                if (scrollY >= sectionTop - window.innerHeight / 2 && scrollY < sectionTop + section.offsetHeight) {
                    setActiveSection(section.id);
                }
            });
        };

        const observer = new IntersectionObserver(scrollHandler, {
            threshold: 0.5 // Trigger when at least half of the section is visible
        });

        sections.forEach((section) => observer.observe(section));

        // Smooth scrolling function
        const smoothScroll = (element: any) => {
            setActiveSection(element.id);
            element.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        };

        // Add click event listeners to each sectionLink
        sections.forEach((section) => {
            const sectionId = section.id;
            const sectionLink = document.querySelector(`a[href="#${sectionId}"]`);
            if (sectionLink) {
                sectionLink.addEventListener('click', (event) => {
                    event.preventDefault();
                    smoothScroll(section);
                });
            }
        });

        return () => observer.disconnect();
    }, []);

    return (
        <aside>
            <nav>
                <div className={styles.navTitle}>On this page</div>
                <div className={styles.navOptions}>
                    {sections.map((section) => (
                        <a
                            key={section.id}
                            href={`#${section.id}`}
                            className={activeSection === section.id ? styles.visible : ''}>
                            {section.label}
                        </a>
                    ))}
                </div>
            </nav>
        </aside>
    );
};
