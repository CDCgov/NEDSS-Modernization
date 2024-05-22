import { ReactNode, useEffect, useRef } from 'react';

export const OutOfTabOrder = ({
    children,
    submitted,
    className
}: {
    children: ReactNode;
    submitted: boolean;
    className?: string;
}) => {
    const element = useRef<HTMLElement>(null);

    useEffect(() => {
        if (element.current && className) {
            const selector = document.querySelector(`.${className}`);
            if (selector) {
                const querySelectors = element.current.querySelectorAll('button');
                if (querySelectors) {
                    querySelectors.forEach((querySelector) => ((querySelector as HTMLElement).tabIndex = -1));
                }
            }
        }
    }, [element.current, submitted]);

    return (
        <span className={className} ref={element}>
            {children}
        </span>
    );
};
