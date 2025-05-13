import { StandardButtonProps } from '../Button';
import { buttonClassnames } from '../buttonClassNames';

type Props = { href: string } & StandardButtonProps & Omit<JSX.IntrinsicElements['a'], 'href'>;

const LinkButton = ({
    href,
    target = '_blank',
    rel = 'noreferrer',
    className,
    sizing,
    icon,
    labelPosition = 'right',
    tertiary = false,
    secondary = false,
    destructive = false,
    children,
    ...defaultProps
}: Props) => (
    <a
        className={buttonClassnames({
            className,
            sizing,
            icon,
            labelPosition,
            tertiary: tertiary,
            secondary: secondary,
            destructive,
            children
        })}
        href={href}
        target={target}
        rel={rel}
        {...defaultProps}>
        {icon}
        {children}
    </a>
);

export { LinkButton };
