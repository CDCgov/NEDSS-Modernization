import { ReactNode } from 'react';
import classnames from 'classnames';
import { Sizing } from 'design-system/field';
import styles from './link-button.module.scss';

type Props = {
    href: string;
    label?: string;
    type?: 'outline' | 'solid';
    sizing?: Sizing;
    children: ReactNode;
} & JSX.IntrinsicElements['a'];

const LinkButton = ({
    type = 'outline',
    href,
    label,
    className,
    children,
    target = '_blank',
    rel = 'noreferrer',
    sizing,
    ...defaultProps
}: Props) => (
    <a
        className={classnames('usa-button', className, sizing && styles[sizing], {
            'usa-button--outline': type === 'outline'
        })}
        href={href}
        target={target}
        rel={rel}
        aria-label={label}
        {...defaultProps}>
        {children}
    </a>
);

export { LinkButton };
