import { ReactNode, useRef } from 'react';
import classNames from 'classnames';
import styles from './activity-search-header.module.scss';
import { Link } from '@trussworks/react-uswds';
import { Icon } from 'design-system/icon';
import RichTooltip from 'design-system/richTooltip/RichTooltip';

type ActionsRenderer = () => ReactNode;

type Props = {
    className?: string;
    actions?: ActionsRenderer;
};

const ActivityLogSearchHeader = ({ className, actions }: Props) => {
    const infoIconRef = useRef<HTMLDivElement>(null);

    return (
        <nav className={classNames(styles.navigation, className)}>
            <h1>Activity Log 7 Beta</h1>
            <div className={styles.links}>
                <div className={styles.linkContainer}>
                    <Link href="nbs/LoadDSMActivityLog.do?method=searchActivityLog&param1=11648804">
                        Go to classic activity log search
                    </Link>
                    <RichTooltip marginTop={42} anchorRef={infoIconRef}>
                        <span>
                            <b>We are modernizing search</b>
                            <br />
                            To perform an event search or save a new custom queue, continue using classic search.
                        </span>
                    </RichTooltip>
                    <div className={styles.infoIconContainer} ref={infoIconRef}>
                        <Icon name="info_outline" color="#265e9d" />
                    </div>
                </div>
            </div>

            {actions && actions()}
        </nav>
    );
};

export { ActivityLogSearchHeader };
