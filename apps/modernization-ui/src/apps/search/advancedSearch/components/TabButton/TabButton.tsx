import './TabButton.scss';

type TabButtonProps = {
    title: string;
    active: boolean;
    onClick: () => void;
};

const TabButton = ({ active, title, onClick }: TabButtonProps) => {
    return (
        <button
            role="tab"
            onClick={onClick}
            className={`${
                active && 'active'
            } text-normal font-sans-md padding-bottom-1 margin-x-2 cursor-pointer margin-top-2 margin-bottom-0 usa-button--unstyled tab-button`}>
            {title}
        </button>
    );
};

export default TabButton;
