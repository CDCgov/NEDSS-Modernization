import classNames from 'classnames';
import './NoData.scss';

type NoDataProps = {
    className?: string;
    display?: 'text' | 'dashes' | 'whitespace';
};

const NoData = ({ className = '', display = 'text' }: NoDataProps) => {
    const noDataText = display === 'dashes' ? '---' : display === 'whitespace' ? <>&nbsp;</> : 'No Data';
    return <span className={classNames('no-data', className)}>{noDataText}</span>;
};

export { NoData };
