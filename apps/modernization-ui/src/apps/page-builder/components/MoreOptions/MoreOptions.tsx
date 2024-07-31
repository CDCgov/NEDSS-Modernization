import './MoreOptions.scss';
import useComponentVisible from './useComponentVisible';
import { useEffect } from 'react';

export const MoreOptions = ({ children, header, close, className }: any) => {
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
                }}>
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
