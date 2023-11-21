import { ReactNode } from 'react';
import classnames from 'classnames';
import './LinkButton.scss';

type Props = {
    href: string;
    children: ReactNode;
} & JSX.IntrinsicElements['a'];

const LinkButton = ({ href, className, children, target, rel }: Props) => (
    <a className={classnames('link-button', className)} href={href} target={target} rel={rel}>
        {children}
    </a>
);

export { LinkButton };
