import { ReactNode } from 'react';
import classNames from 'classnames';
import { HelperText } from './HelperText';
import { InlineErrorMessage } from './InlineErrorMessage';
import { InlineWarningMessage } from './InlineWarningMessage';

import styles from './vertical-field.module.scss';

type Props = {
    className?: string;
    htmlFor: string;
    label: string;
    helperText?: string;
    error?: string;
    required?: boolean;
    warning?: string;
    children: ReactNode;
};

const VerticalField = ({ className, htmlFor, label, helperText, required, error, warning, children }: Props) => (
    <span className={classNames(styles.entry, className)}>
        <span className={styles.labels}>
            <label className={classNames({ [styles.required]: required })} htmlFor={htmlFor}>
                {label}
            </label>
            {helperText && <HelperText id={`${htmlFor}-hint`}>{helperText}</HelperText>}
        </span>
        {warning && <InlineWarningMessage id={`${htmlFor}-warning`}>{warning}</InlineWarningMessage>}
        {error && <InlineErrorMessage id={`${htmlFor}-error`}>{error}</InlineErrorMessage>}

        {children}
    </span>
);

export { VerticalField };
