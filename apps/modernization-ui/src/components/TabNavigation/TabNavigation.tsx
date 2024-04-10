import TabButton from 'components/TabButton/TabButton';

export type TabProps = {
    title: string;
    type: string;
};

type TabNavigationProps = {
    tabsList: TabProps[];
    handleTabNavigation: (type: string) => void;
    isActive: (type: string) => boolean;
};

export const TabNavigation = ({ tabsList, handleTabNavigation, isActive }: TabNavigationProps) => {
    return (
        <>
            {tabsList.map((tab, index) => (
                <TabButton
                    key={tab.type}
                    className={index === 0 ? 'margin-left-0' : ''}
                    title={tab.title}
                    onClick={() => handleTabNavigation(tab.type)}
                    active={isActive(tab.type)}
                />
            ))}
        </>
    );
};
