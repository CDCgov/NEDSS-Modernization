import { ReactNode, useEffect, useRef, useState } from 'react';
import classNames from 'classnames';

import styles from './collapsible.module.scss';

type CollapsibleProps = {
    id: string;
    className?: string;
    open?: boolean;
    children: ReactNode;
};

const Collapsible = ({ id, open = true, children }: CollapsibleProps) => {
    const [height, setHeight] = useState<number | undefined>(open ? undefined : 0);
    const contentRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (contentRef.current) {
            const resizeObserver = new ResizeObserver((element) => {
                const next = open ? element[0].contentRect.height : 0;
                setHeight(next);
            });

            resizeObserver.observe(contentRef.current);
            return () => resizeObserver.disconnect();
        }
    }, [height, open, contentRef.current]);

    return (
        <div
            id={id}
            style={{ height }}
            className={classNames(styles.collapsible, {
                [styles.collapsed]: !open
            })}>
            <div ref={contentRef}>{children}</div>
        </div>
    );
};

export { Collapsible };
