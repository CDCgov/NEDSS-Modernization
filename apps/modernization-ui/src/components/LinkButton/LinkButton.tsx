import { ReactNode } from 'react';
import classnames from 'classnames';
import styles from './LinkButton.module.scss';

type Props = {
    href: string;
    label?: string;
    children: ReactNode;
} & JSX.IntrinsicElements['a'];

const LinkButton = ({ href, label, className, children, target = '_blank', rel = 'noreferrer' }: Props) => (
    <a className={classnames(styles.outline, className)} href={href} target={target} rel={rel} aria-label={label}>
        {children}
    </a>
);

export { LinkButton };
