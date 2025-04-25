import { Heading, HeadingLevel } from 'components/heading';
import { Button, ButtonProps } from 'design-system/button';
import { Icon } from 'design-system/icon';
import styles from './table-card-header.module.scss';
import { OverlayPanel } from 'overlay';
import { ColumnPreferencesPanel } from 'design-system/table/preferences';
import { Shown } from 'conditional-render';

export type TableCardAction = ButtonProps;

export type TableCardHeaderProps = {
    title: string;
    subtext?: string;
    headingLevel?: HeadingLevel;
    tagText?: string;
    showSettings?: boolean;
    actions?: TableCardAction[];
};

export const TableCardHeader = ({
    title,
    headingLevel = 2,
    subtext,
    showSettings = true,
    actions
}: TableCardHeaderProps) => {
    return (
        <>
            <div className={styles.title}>
                <Heading level={headingLevel}>{title}</Heading>
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
                        render={(close) => <ColumnPreferencesPanel close={close} />}></OverlayPanel>
                </Shown>
            </div>
        </>
    );
};
