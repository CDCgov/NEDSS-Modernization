import classNames from 'classnames';
import { StandardButtonProps } from '../Button';
import { buttonClassnames } from '../buttonClassNames';

import styles from './link-button.module.scss';

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
    children,
    ...defaultProps
}: LinkButtonProps) => (
    <a
        className={classNames(
            styles.link,
            buttonClassnames({
                className,
                sizing,
                icon,
                labelPosition,
                active,
                tertiary,
                secondary,
                destructive,
                children
            })
        )}
        href={href}
        target={target}
        rel={rel}
        {...defaultProps}>
        {icon}
        {children}
    </a>
);

export { LinkButton };
export type { LinkButtonProps };
