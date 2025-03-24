import { RefAttributes } from 'react';
import { NavLink, NavLinkProps } from 'react-router';
import classnames from 'classnames';
import { Sizing } from 'design-system/field';
import styles from './navlink-button.module.scss';

type Props = {
    to: string;
    label?: string;
    type?: 'outline';
    className?: string;
    dataTestId?: string;
    state?: any;
    sizing?: Sizing;
} & Omit<NavLinkProps, 'classname'> &
    RefAttributes<HTMLAnchorElement>;

const NavLinkButton = ({ type, to, label, className, dataTestId, children, state, sizing }: Props) => (
    <NavLink
        className={classnames(className, 'usa-button', sizing && styles[sizing], {
            'usa-button--outline': type === 'outline'
        })}
        to={to}
        data-testid={dataTestId}
        aria-label={label}
        state={state}>
        {children}
    </NavLink>
);

export { NavLinkButton };
