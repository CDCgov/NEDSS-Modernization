import { ReactNode, useEffect, useRef } from 'react';

export const OutOfTabOrder = ({
    children,
    focusable,
    selector
}: {
    children: ReactNode;
    focusable: boolean;
    selector: string;
}) => {
    const element = useRef<HTMLElement>(null);

    useEffect(() => {
        if (element.current && !focusable) {
            const elements = element.current.querySelectorAll(selector);
            if (elements) {
                elements.forEach((element) => ((element as HTMLElement).tabIndex = -1));
            }
        }
    }, [element.current, focusable]);

    return <span ref={element}>{children}</span>;
};
