import { ReactNode } from 'react';
import classNames from 'classnames';
import { HelperText } from './HelperText';
import { InlineErrorMessage } from './InlineErrorMessage';
import { InlineWarningMessage } from './InlineWarningMessage';
import { Sizing } from './Field';
import styles from './horizontal-field.module.scss';

type Props = {
    className?: string;
    sizing: Sizing;
    htmlFor: string;
    label: string;
    helperText?: string;
    error?: string;
    required?: boolean;
    warning?: string;
    children: ReactNode;
};

const HorizontalField = ({
    className,
    sizing,
    htmlFor,
    label,
    helperText,
    required,
    error,
    warning,
    children
}: Props) => (
    <div
        className={classNames(styles.horizontalInput, className, {
            [styles.small]: sizing === 'small',
            [styles.medium]: sizing === 'medium',
            [styles.large]: sizing === 'large'
        })}>
        <div className={styles.left}>
            <label className={classNames({ [styles.required]: required })} htmlFor={htmlFor}>
                {label}
            </label>

            {helperText && <HelperText id={`${htmlFor}-hint`}>{helperText}</HelperText>}
        </div>
        <div className={styles.right}>
            <div className={styles.children}>{children}</div>
            <div className={styles.message}>
                {warning && <InlineWarningMessage id={`${htmlFor}-warning`}>{warning}</InlineWarningMessage>}
                {error && <InlineErrorMessage id={`${htmlFor}-error`}>{error}</InlineErrorMessage>}
            </div>
        </div>
    </div>
);

export { HorizontalField };
