import { ReactNode } from 'react';
import classNames from 'classnames';
import { Breadcrumb } from 'breadcrumb';

import styles from './page-management-layout.module.scss';

type Mode = 'draft' | 'published' | 'edit';

const resolveModeDisplay = (mode: Mode) => (mode === 'edit' ? 'edit mode' : mode);

type PageBuilderLayoutProps = {
    name: string;
    mode: Mode;
    children?: ReactNode;
};

const PageManagementLayout = ({ name, mode, children }: PageBuilderLayoutProps) => {
    return (
        <section className={styles.management}>
            <header>
                <h1>Page builder</h1>
            </header>
            <div className={styles.bar}>
                <Breadcrumb start="/page-builder/pages" currentPage={name}>
                    Page library
                </Breadcrumb>
                <span
                    className={classNames(styles.mode, {
                        [styles.draft]: mode === 'draft',
                        [styles.edit]: mode === 'edit',
                        [styles.published]: mode === 'published'
                    })}>
                    {resolveModeDisplay(mode)}
                </span>
            </div>
            <div className={styles.content}>{children}</div>
        </section>
    );
};

export { PageManagementLayout };
