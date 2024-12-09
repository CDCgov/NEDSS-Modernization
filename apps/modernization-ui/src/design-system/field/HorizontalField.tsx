import { ReactNode } from 'react';
import classNames from 'classnames';
import { HelperText } from './HelperText';
import { InlineErrorMessage } from './InlineErrorMessage';

import styles from './horizontal-field.module.scss';

type Props = {
    className?: string;
    htmlFor: string;
    label: string;
    helperText?: string;
    error?: string;
    required?: boolean;
    children: ReactNode;
};

const HorizontalField = ({ className, htmlFor, label, helperText, required, error, children }: Props) => (
    <div className={classNames(styles.horizontalInput, className)}>
        <div className={styles.left}>
            {label && (
                <label className={classNames({ required })} htmlFor={htmlFor}>
                    {label}
                </label>
            )}
            {helperText && <HelperText id={`${htmlFor}-hint`}>{helperText}</HelperText>}
        </div>
        <div className={styles.right}>
            {error && <InlineErrorMessage id={`${htmlFor}-error`}>{error}</InlineErrorMessage>}
            {children}
        </div>
    </div>
);

export { HorizontalField };
