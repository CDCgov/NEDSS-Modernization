import './MoreOptions.scss';
import useComponentVisible from '../../helpers/useComponentVisible';

export const MoreOptions = ({ children, header }: any) => {
    const { ref, isComponentVisible, setIsComponentVisible } = useComponentVisible(false);

    return (
        <div className="more-options">
            <div
                className={`more-options__header ${isComponentVisible ? 'active' : ''}`}
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
