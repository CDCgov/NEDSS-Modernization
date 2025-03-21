import { Link, useLocation } from 'react-router';
import { ReactElement } from 'react';
import classNames from 'classnames';
import style from './tabNavigation.module.scss';

type NavigationProps = {
    path: string;
    children: string;
};

const TabNavigationEntry = ({ children, path }: NavigationProps) => {
    const { pathname } = useLocation();

    return (
        <Link to={path} className={classNames(style.tab, { [style.active]: isActive(path, pathname) })}>
            {children}
        </Link>
    );
};

type Children = ReactElement<NavigationProps>;

type TabNavigationProps = {
    className?: string;
    children?: Children | Children[];
};

const TabNavigation = ({ children = [], className }: TabNavigationProps) => {
    return (
        <div className={classNames(style['tab-navigation'], className)}>
            {ensureArray(children).map((child, index) => (
                <div key={index}>{child}</div>
            ))}
        </div>
    );
};

const ensureArray = (children: Children | Children[]) => (Array.isArray(children) ? children : [children]);
const isActive = (activePath: string, currentPath: string) => currentPath.includes(activePath);

export { TabNavigationEntry, TabNavigation };
