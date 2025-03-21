import { ReactNode } from 'react';
import { useLocation } from 'react-router';
import classNames from 'classnames';
import { Breadcrumb } from 'breadcrumb';

import styles from './page-management-layout.module.scss';

type PageBuilderLayoutProps = {
    name: string;
    mode: string;
    children?: ReactNode;
};

const PageManagementLayout = ({ name, mode, children }: PageBuilderLayoutProps) => {
    const { pathname } = useLocation();
    const pathNames = pathname.split('/');
    let isEditing = false;
    if (pathname && pathNames.length > 0) {
        isEditing = pathNames[pathNames.length - 1] === 'edit';
    }
    return (
        <section className={styles.management}>
            <header>
                <h1 aria-label="Page builder">Page builder</h1>
            </header>
            <div className={styles.bar}>
                <Breadcrumb start="/page-builder/pages" currentPage={name}>
                    Page library
                </Breadcrumb>
                <h2 className={classNames(isEditing ? styles.modeEditing : styles.mode)}>
                    {isEditing ? 'EDITING: ' : 'PREVIEWING: '}
                    {mode}
                </h2>
            </div>
            <div className={styles.content}>{children}</div>
        </section>
    );
};

export { PageManagementLayout };
