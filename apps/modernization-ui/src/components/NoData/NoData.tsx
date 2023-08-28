import './NoData.scss';

const NoData = ({ className = '' }: { className?: string }) => {
    return <span className={`no-data ${className}`}>No Data</span>;
};

export { NoData };
