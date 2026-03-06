import { useLocation } from 'react-router';
import { ReactElement } from 'react';
import classNames from 'classnames';
import style from './tabNavigation.module.scss';
import { NavLinkButton } from 'design-system/button';
import { Sizing } from 'design-system/field';

type NavigationProps = {
    path: string;
    children: string;
};

const TabNavigationEntry = ({ children, path }: NavigationProps) => {
    const { pathname } = useLocation();
    console.log('TabNavigationEntry', path, pathname);

    return (
        <div className={classNames(style.tab, { [style.active]: isActive(path, pathname) })}>
            <NavLinkButton
                secondary
                tertiary
                to={path}
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
    sizing?: Sizing;
};

const TabNavigation = ({ sizing = 'medium', children = [], className }: TabNavigationProps) => {
    return (
        <div
            className={classNames(
                style['tab-navigation'],
                sizing === 'large' && style['large'],
                sizing === 'medium' && style['medium'],
                className
            )}>
            {ensureArray(children).map((child, index) => (
                <div key={index}>{child}</div>
            ))}
        </div>
    );
};

const ensureArray = (children: Children | Children[]) => (Array.isArray(children) ? children : [children]);
const isActive = (activePath: string, currentPath: string) => currentPath.includes(activePath);

export { TabNavigationEntry, TabNavigation };
