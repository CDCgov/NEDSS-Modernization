import classNames from 'classnames';
import { ReactElement } from 'react';
import { Link, useLocation } from 'react-router-dom';
import './SideNavigation.scss';

type EntryProps = {
    children: string;
};

type LinkEntryProps = EntryProps & {
    href: string;
};

const LinkEntry = ({ children, href }: LinkEntryProps) => <a href={href}>{children}</a>;

type NavigationEntryProps = EntryProps & {
    path: string;
};
const NavigationEntry = ({ children, path }: NavigationEntryProps) => {
    return <Link to={path}>{children}</Link>;
};

type Children = ReactElement<LinkEntryProps> | ReactElement<NavigationEntryProps>;

type SideNavigationProps = {
    title: string;
    className?: string;
    children?: Children | Children[];
};
const SideNavigation = ({ title, className, children = [] }: SideNavigationProps) => {
    const { pathname } = useLocation();

    return (
        <div className={classNames('navigation', className)}>
            <h2>{title}</h2>
            <nav>
                <ol>
                    {ensureArray(children).map((child, index) => (
                        <li key={index} className={classNames('item', { active: isActive(pathname, child) })}>
                            {child}
                        </li>
                    ))}
                </ol>
            </nav>
        </div>
    );
};

const ensureArray = (children: Children | Children[]) => (Array.isArray(children) ? children : [children]);

const isActive = (active: string, entry: Children) => 'path' in entry.props && entry.props.path === active;

export { LinkEntry, NavigationEntry, SideNavigation };
