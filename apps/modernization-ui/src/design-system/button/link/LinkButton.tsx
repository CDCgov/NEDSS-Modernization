import { Button, StandardButtonProps } from '../Button';

type InternalLinkProps = { href: string } & Omit<JSX.IntrinsicElements['a'], 'href'>;

type LinkButtonProps = InternalLinkProps & StandardButtonProps;

const LinkButton = ({
    href,
    target = '_self',
    rel = 'noreferrer',
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
}: LinkButtonProps) => {
    const labelPosition = 'labelPosition' in remaining ? remaining.labelPosition : undefined;
    return (
        <a href={disabled ? undefined : href} target={target} rel={rel} {...remaining} tabIndex={-1}>
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
        </a>
    );
};

export { LinkButton };
export type { LinkButtonProps };
