import classNames from 'classnames';
import { InlineMessage, InlineMessageProps } from './InlineMessage';

import styles from './inline-warning-message.module.scss';

const InlineWarningMessage = ({ className, ...remaining }: InlineMessageProps) => (
    <InlineMessage className={classNames(styles.warning, className)} {...remaining} />
);

export { InlineWarningMessage };
