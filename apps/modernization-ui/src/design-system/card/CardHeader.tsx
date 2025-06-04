import { ReactNode } from 'react';
import { Heading, HeadingLevel } from 'components/heading';

import styles from './card-header.module.scss';

type CardHeaderProps = {
    id: string;
    title: ReactNode;
    level?: HeadingLevel;
    extra?: ReactNode;
    actions?: ReactNode;
    info?: ReactNode;
    subtext?: string;
};

const CardHeader = ({ id, title, level = 2, extra, actions, info, subtext }: CardHeaderProps) => {
    return (
        <>
            <div className={styles.titles}>
                <span className={styles.title}>
                    <Heading id={id} level={level}>
                        {title}
                    </Heading>
                    {extra}
                </span>
                {subtext && <div className={styles.subtext}>{subtext}</div>}
            </div>
            {actions && <div className={styles.actions}>{actions}</div>}
            {info && <div className={styles.info}>{info}</div>}
        </>
    );
};

export { CardHeader };
export type { CardHeaderProps };
