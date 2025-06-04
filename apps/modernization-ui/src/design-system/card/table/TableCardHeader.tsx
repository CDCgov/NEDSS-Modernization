import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { ColumnPreferencesPanel } from 'design-system/table/preferences';
import { Tag } from 'design-system/tag';
import { OverlayPanel } from 'overlay';
import { CardHeader, CardHeaderProps } from '../CardHeader';

import styles from './table-card-header.module.scss';

type TableCardHeaderProps = {
    resultCount?: number;
} & CardHeaderProps;

const TableCardHeader = ({ title, level, subtext, actions, resultCount }: TableCardHeaderProps) => {
    return (
        <CardHeader
            title={title}
            level={level}
            extra={<Tag size="small">{resultCount}</Tag>}
            subtext={subtext}
            actions={
                <>
                    {actions}
                    <OverlayPanel
                        className={styles.overlay}
                        position="right"
                        toggle={({ toggle }) => (
                            <Button
                                aria-label="Settings"
                                data-tooltip-position="top"
                                data-tooltip-offset="center"
                                secondary
                                icon={<Icon name="settings" />}
                                onClick={toggle}
                                sizing="small"
                            />
                        )}
                        render={(close) => <ColumnPreferencesPanel close={close} />}
                    />
                </>
            }
        />
    );
};

export { TableCardHeader };
export type { TableCardHeaderProps };
