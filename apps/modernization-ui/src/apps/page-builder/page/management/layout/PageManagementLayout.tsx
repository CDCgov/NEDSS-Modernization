import { ReactNode } from 'react';
import classNames from 'classnames';
import { Breadcrumb } from 'breadcrumb';

import styles from './page-management-layout.module.scss';

type PageBuilderLayoutProps = {
    name: string;
    mode: string;
    children?: ReactNode;
};

const PageManagementLayout = ({ name, mode, children }: PageBuilderLayoutProps) => {
    return (
        <section className={styles.management}>
            <header>
                <h1 aria-label="Page builder">Page builder</h1>
            </header>
            <div className={styles.bar}>
                <Breadcrumb start="/page-builder/pages" currentPage={name}>
                    Page library
                </Breadcrumb>
                <span
                    className={classNames(styles.mode, {
                        [styles.draft]: mode === 'Draft',
                        [styles.edit]: mode === 'Edit',
                        [styles.published]: mode === 'Published'
                    })}>
                    {mode === 'Published' ? (
                        <>Previewing: Published</>
                    ) : mode === 'Draft' ? (
                        <>Previewing: Initial Draft</>
                    ) : (
                        <>Previewing: Published with Draft</>
                    )}
                </span>
            </div>
            <div className={styles.content}>{children}</div>
        </section>
    );
};

export { PageManagementLayout };
