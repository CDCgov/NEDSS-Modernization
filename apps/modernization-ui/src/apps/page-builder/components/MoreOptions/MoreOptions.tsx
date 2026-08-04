import './MoreOptions.scss';
import { ReactNode, useEffect } from 'react';

import useComponentVisible from './useComponentVisible';

export const MoreOptions = ({
    children,
    header,
    close,
    className,
}: {
    children: ReactNode;
    header: ReactNode;
    close: boolean;
    className?: string;
}) => {
    const { ref, isComponentVisible, setIsComponentVisible } = useComponentVisible(false);
    useEffect(() => {
        if (close === true) {
            setIsComponentVisible(false);
        }
    }, [close]);
    return (
        <div className="more-options">
            <div
                className={`more-options__header ${className} ${isComponentVisible ? 'active' : ''}`}
                onClick={(e) => {
                    if (isComponentVisible) {
                        setIsComponentVisible(false);
                        e.stopPropagation();
                    } else {
                        setIsComponentVisible(true);
                    }
                }}
            >
                {header}
            </div>
            {isComponentVisible ? (
                <div ref={ref} className="more-options__menu">
                    {children}
                </div>
            ) : null}
        </div>
    );
};
