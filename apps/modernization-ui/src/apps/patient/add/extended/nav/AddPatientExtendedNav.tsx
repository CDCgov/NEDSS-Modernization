import React from 'react';
import { useEffect, useState, useCallback } from 'react';
import styles from './extended-patient-nav.module.scss';

export const AddPatientExtendedNav = () => {
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
