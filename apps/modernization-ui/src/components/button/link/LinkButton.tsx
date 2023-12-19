import { ReactNode } from 'react';
import classnames from 'classnames';

type Props = {
    href: string;
    label?: string;
    type?: 'outline' | 'solid';
    children: ReactNode;
} & JSX.IntrinsicElements['a'];

const LinkButton = ({
    type = 'outline',
    href,
    label,
    className,
    children,
    target = '_blank',
    rel = 'noreferrer'
}: Props) => (
    <a
        className={classnames('usa-button', className, { 'usa-button--outline': type === 'outline' })}
        href={href}
        target={target}
        rel={rel}
        aria-label={label}>
        {children}
    </a>
);

export { LinkButton };
