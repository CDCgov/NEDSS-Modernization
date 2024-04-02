import classNames from 'classnames';
import style from './TabButton.module.scss';

type TabButtonProps = {
    title: string;
    active: boolean;
    onClick: () => void;
    className?: string;
};

const TabButton = ({ active, title, onClick, className }: TabButtonProps) => {
    return (
        <button role="tab" onClick={onClick} className={classNames(style.tab, { [style.active]: active }, className)}>
            {title}
        </button>
    );
};

export default TabButton;
