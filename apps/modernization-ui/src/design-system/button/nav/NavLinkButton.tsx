import { NavLink, NavLinkProps } from 'react-router';
import { Button, StandardButtonProps } from '../Button';
import { ReactNode } from 'react';

type NavLinkButtonProps = Omit<NavLinkProps, 'children'> & StandardButtonProps & { children?: ReactNode };

const NavLinkButton = ({
    className,
    sizing,
    icon,
    active,
    tertiary,
    secondary,
    destructive,
    disabled,
    children,
    ...remaining
}: NavLinkButtonProps) => {
    const labelPosition = 'labelPosition' in remaining ? remaining.labelPosition : undefined;

    return (
        <NavLink {...remaining} tabIndex={-1}>
            <Button
                className={className}
                sizing={sizing}
                icon={icon}
                labelPosition={labelPosition}
                active={active}
                tertiary={tertiary}
                secondary={secondary}
                destructive={destructive}
                disabled={disabled}>
                {children}
            </Button>
        </NavLink>
    );
};

export { NavLinkButton };
export type { NavLinkButtonProps };
