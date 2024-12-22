import classNames from 'classnames';
import { InlineMessage, InlineMessageProps } from './InlineMessage';

import styles from './inline-error-message.module.scss';

const InlineErrorMessage = ({ className, ...remaining }: InlineMessageProps) => (
    <InlineMessage className={classNames(styles.error, className)} {...remaining} />
);

export { InlineErrorMessage };
