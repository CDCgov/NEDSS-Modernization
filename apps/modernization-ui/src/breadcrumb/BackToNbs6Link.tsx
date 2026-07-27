import classNames from 'classnames';
import { Icon } from '@trussworks/react-uswds';
import { BreadcrumbProps } from './Breadcrumb';

import styles from './breadcrumb.module.scss';

// Same as above but for navigating "out" of mod and back to NBS 6
const BackToNbs6Link = ({ start, children }: Pick<BreadcrumbProps, 'start' | 'children'>) => {
    return (
        <span className={classNames(styles.breadcrumb, styles.item)}>
            <a className={styles.link} href={start}>
                <Icon.ArrowBack size={3} aria-label="Back arrow" />
                {children}
            </a>
        </span>
    );
};

export { BackToNbs6Link };
