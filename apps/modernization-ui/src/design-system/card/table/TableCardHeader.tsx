import { Heading, HeadingLevel } from 'components/heading';
import { Button, ButtonProps } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { OverlayPanel } from 'overlay';
import { ColumnPreferencesPanel } from 'design-system/table/preferences';
import { Shown } from 'conditional-render';
import { Tag } from 'design-system/tag';
import { Sizing } from 'design-system/field';
import styles from './table-card-header.module.scss';

export type TableCardAction = ButtonProps;

export type TableCardHeaderProps = {
    title: string;
    subtext?: string;
    headingLevel?: HeadingLevel;
    tagText?: string;
    showSettings?: boolean;
    resultCount?: number;
    actions?: TableCardAction[];
    sizing?: Sizing;
};

export const TableCardHeader = ({
    title,
    headingLevel = 2,
    subtext,
    showSettings = true,
    actions,
    resultCount,
    sizing
}: TableCardHeaderProps) => {
    return (
        <>
            <div className={styles.title}>
                <div className={styles.titleContent}>
                    <Heading level={headingLevel}>{title}</Heading>
                    <Tag size={sizing}>{resultCount}</Tag>
                </div>
                {subtext && <div className={styles.subtext}>{subtext}</div>}
            </div>
            <div className={styles.actions}>
                {actions?.map((action, index) => <Button key={index} {...action} />)}
                <Shown when={showSettings}>
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
                </Shown>
            </div>
        </>
    );
};
