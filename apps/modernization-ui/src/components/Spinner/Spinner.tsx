import './Spinner.scss';

export const Spinner = () => {
    return (
        <div className="spinner">
            <span className="ds-c-spinner" role="status">
                <span className="ds-u-visibility--screen-reader">Loading</span>
            </span>
        </div>
    );
};
