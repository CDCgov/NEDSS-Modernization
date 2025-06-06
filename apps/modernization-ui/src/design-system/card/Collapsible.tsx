import { ReactNode, useEffect, useRef, useState } from 'react';

import styles from './collapsible.module.scss';
import classNames from 'classnames';

type CollapsibleProps = {
    className?: string;
    open?: boolean;
    children: ReactNode;
};

const Collapsible = ({ open = true, children }: CollapsibleProps) => {
    const [height, setHeight] = useState<number | undefined>(open ? undefined : 0);
    const contentRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (!height || !open || !contentRef.current) return undefined;

        if (contentRef.current) {
            const resizeObserver = new ResizeObserver((element) => {
                setHeight(element[0].contentRect.height);
            });

            resizeObserver.observe(contentRef.current);
            return () => resizeObserver.disconnect();
        }
    }, [height, open, contentRef.current]);

    useEffect(() => {
        if (open) setHeight(contentRef.current?.getBoundingClientRect().height);
        else setHeight(0);
    }, [open]);

    return (
        <div
            style={{ height }}
            className={classNames(styles.collapsible, {
                [styles.collapsed]: !open
            })}>
            <div ref={contentRef}>
                <div>{children}</div>
            </div>
        </div>
    );
};

export { Collapsible };
