import { Heading, HeadingLevel } from 'components/heading';
import { Button, ButtonProps } from 'design-system/button';
import { Icon } from 'design-system/icon';
import styles from './table-card-header.module.scss';

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
            {showSettings && (
                <div className={styles.actions}>
                    {actions?.map((action, index) => <Button key={index} {...action} />)}
                    <Button
                        aria-label="Settings"
                        data-tooltip-position="top"
                        data-tooltip-offset="center"
                        secondary
                        sizing="small"
                        icon={<Icon name="settings" />}
                    />
                </div>
            )}
        </>
    );
};
