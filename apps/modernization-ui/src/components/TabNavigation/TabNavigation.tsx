import { useLocation } from 'react-router';
import { ReactElement } from 'react';
import classNames from 'classnames';
import style from './tabNavigation.module.scss';
import { NavLinkButton } from 'design-system/button';

type NavigationProps = {
    path: string;
    children: string;
};

const TabNavigationEntry = ({ children, path }: NavigationProps) => {
    const { pathname } = useLocation();

    return (
        <div className={classNames(style.tab, { [style.active]: isActive(path, pathname) })}>
            <NavLinkButton
                secondary
                tertiary
                active
                to={path}
                sizing="medium"
                className={classNames(style.tabContent, { [style.active]: isActive(path, pathname) })}>
                {children}
            </NavLinkButton>
        </div>
    );
};

type Children = ReactElement<NavigationProps>;

export type TabNavigationProps = {
    className?: string;
    children?: Children | Children[];
    newTab?: boolean;
};

const TabNavigation = ({ newTab = false, children = [], className }: TabNavigationProps) => {
    return (
        <div className={classNames(newTab ? style['new-tab-navigation'] : style['tab-navigation'], className)}>
            {ensureArray(children).map((child, index) => (
                <div key={index}>{child}</div>
            ))}
        </div>
    );
};

const ensureArray = (children: Children | Children[]) => (Array.isArray(children) ? children : [children]);
const isActive = (activePath: string, currentPath: string) => currentPath.includes(activePath);

export { TabNavigationEntry, TabNavigation };
