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
    labelPosition = 'right',
    active,
    tertiary,
    secondary,
    destructive,
    disabled,
    children,
    ...remaining
}: LinkButtonProps) => (
    <a href={disabled ? undefined : href} target={target} rel={rel} {...remaining}>
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

export { LinkButton };
export type { LinkButtonProps };
