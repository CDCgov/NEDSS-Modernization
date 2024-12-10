import classNames from 'classnames';

import styles from './inline-error-message.module.scss';

type InlineErrorMessageProps = {
    id: string;
    children: string;
} & Omit<JSX.IntrinsicElements['span'], 'id' | 'children'>;

const InlineErrorMessage = ({ id, className, children }: InlineErrorMessageProps) => (
    <span className={classNames(styles.message, className)} id={id} role="alert">
        {children}
    </span>
);

export { InlineErrorMessage };
