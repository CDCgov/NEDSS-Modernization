import classNames from 'classnames';
import { StandardButtonProps } from '../buttons';
import { resolveClasses } from '../resolveClasses';

import styles from './link-button.module.scss';
import { resolveContent } from '../resolveContent';

type LinkButtonProps = { href: string } & Omit<JSX.IntrinsicElements['a'], 'href' | 'children'> & StandardButtonProps;

const LinkButton = ({ href, target = '_self', rel = 'noreferrer', ...remaining }: LinkButtonProps) => (
    <a
        className={classNames(styles.link, resolveClasses(remaining))}
        href={href}
        target={target}
        rel={rel}
        {...remaining}>
        {resolveContent(remaining)}
    </a>
);

export { LinkButton };
export type { LinkButtonProps };
