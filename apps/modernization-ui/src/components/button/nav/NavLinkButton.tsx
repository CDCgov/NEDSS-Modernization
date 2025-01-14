import { RefAttributes } from 'react';
import { NavLink, NavLinkProps } from 'react-router-dom';
import classnames from 'classnames';

type Props = {
    to: string;
    label?: string;
    type?: 'outline';
    className?: string;
    dataTestId?: string;
    state?: any;
} & Omit<NavLinkProps, 'classname'> &
    RefAttributes<HTMLAnchorElement>;

const NavLinkButton = ({ type, to, label, className, dataTestId, children, state }: Props) => (
    <NavLink
        className={classnames(className, 'usa-button', { 'usa-button--outline': type === 'outline' })}
        to={to}
        data-testid={dataTestId}
        aria-label={label}
        state={state}>
        {children}
    </NavLink>
);

export { NavLinkButton };
