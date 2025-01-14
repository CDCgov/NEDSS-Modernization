import classNames from 'classnames';

import styles from './inline-message.module.scss';

type InlineMessageProps = {
    id: string;
    children: string;
} & Omit<JSX.IntrinsicElements['span'], 'id' | 'children'>;

const InlineMessage = ({ id, className, children }: InlineMessageProps) => (
    <span className={classNames(styles.message, className)} id={id} role="alert">
        {children}
    </span>
);

export { InlineMessage };
export type { InlineMessageProps };
