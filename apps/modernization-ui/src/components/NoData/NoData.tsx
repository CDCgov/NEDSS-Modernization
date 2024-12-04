import './NoData.scss';

type NoDataProps = {
    className?: string;
    showText?: boolean;
};

const NoData = ({ className = '', showText = true }: NoDataProps) => {
    return <span className={`no-data ${className}`}>{showText ? 'No Data' : <>&nbsp;</>}</span>;
};

export { NoData };
