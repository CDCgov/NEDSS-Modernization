import { ReactNode } from 'react';
import { Heading, HeadingLevel } from 'components/heading';
import { Sizing } from 'design-system/field';

import styles from './card-header.module.scss';

type CardHeaderProps = {
    id: string;
    title: ReactNode;
    level?: HeadingLevel;
    flair?: ReactNode;
    actions?: ReactNode;
    control?: ReactNode;
    info?: ReactNode;
    subtext?: string;
    sizing?: Sizing;
};

const CardHeader = ({ id, title, level = 2, flair, control, actions, info, subtext }: CardHeaderProps) => {
    return (
        <header className={styles.header}>
            <div className={styles.titles}>
                <span className={styles.title}>
                    <Heading id={id} level={level}>
                        {title}
                    </Heading>
                    {flair}
                </span>
                {subtext && <div className={styles.subtext}>{subtext}</div>}
            </div>
            <div className={styles.controls}>
                {actions && <div className={styles.actions}>{actions}</div>}
                {control && <div className={styles.control}>{control}</div>}
            </div>
            {info && <div className={styles.info}>{info}</div>}
        </header>
    );
};

export { CardHeader };
export type { CardHeaderProps };
