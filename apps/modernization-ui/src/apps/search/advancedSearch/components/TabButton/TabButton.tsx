import classNames from 'classnames';
import style from './TabButton.module.scss';

type TabButtonProps = {
    title: string;
    active: boolean;
    onClick: () => void;
};

const TabButton = ({ active, title, onClick }: TabButtonProps) => {
    return (
        <button role="tab" onClick={onClick} className={classNames(style.tab, { [style.active]: active })}>
            {title}
        </button>
    );
};

export default TabButton;
