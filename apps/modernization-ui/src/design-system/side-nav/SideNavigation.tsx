import { ReactElement, ReactNode } from 'react';
import classNames from 'classnames';
import { Heading, HeadingLevel } from 'components/heading';
import { NavEntryProps } from './NavEntry';

import styles from './side-nav.module.scss';

type Props = {
    title: string;
    children: ReactElement<NavEntryProps> | ReactElement<NavEntryProps>[];
    headingLevel?: HeadingLevel;
} & Omit<JSX.IntrinsicElements['div'], 'title'>;
/**
 * Accepts 1 or more children of type {@link NavEntry}. If a child is marked `active`
 * only the name property will be displayed in the list and no `onClick` action will be performed.
 *
 * @param {NavEntry} 1 or more NavEntry
 * @return {ReactNode} rendered element with supplied children in an unordered list
 */
export const SideNavigation = ({ title, className, headingLevel = 2, children, ...remaining }: Props): ReactNode => {
    return (
        <div {...remaining} className={classNames(styles.sideNav, className)}>
            <Heading className={styles.title} level={headingLevel}>
                {title}
            </Heading>
            <nav aria-label={title} className={styles.navEntries}>
                <ul>{children}</ul>
            </nav>
        </div>
    );
};
