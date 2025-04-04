import React from 'react';
import styles from './InPageNavigation.module.scss';
import classNames from 'classnames';
import useInPageNavigation from './useInPageNavigation';

export interface NavSection {
    id: string;
    label: string;
}

export interface InPageNavigationProps {
    sections: NavSection[];
    title?: string;
}

export const InPageNavigation: React.FC<InPageNavigationProps> = ({ sections, title = 'On this page' }) => {
    useInPageNavigation(0);

    return (
        <nav>
            <div className={styles.navTitle}>{title}</div>
            <div className={styles.navOptions}>
                {sections.map((section) => (
                    <a
                        key={section.id}
                        id={`inPageNav-${section.id}`}
                        href={`#${section.id}`}
                        className={classNames(styles.navOption)}>
                        {section.label}
                    </a>
                ))}
            </div>
        </nav>
    );
};
