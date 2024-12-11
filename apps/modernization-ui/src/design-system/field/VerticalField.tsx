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
    warnings?: string;
    children: ReactNode;
};

const VerticalField = ({ className, htmlFor, label, helperText, required, error, warnings, children }: Props) => (
    <span className={classNames(styles.entry, className, { [styles.error]: error })}>
        <label className={classNames({ required })} htmlFor={htmlFor}>
            {label}
        </label>
        {helperText && <HelperText id={`${htmlFor}-hint`}>{helperText}</HelperText>}
        {warnings && <InlineWarningMessage id={`${htmlFor}-warning`}>{warnings}</InlineWarningMessage>}
        {error && <InlineErrorMessage id={`${htmlFor}-error`}>{error}</InlineErrorMessage>}
        {children}
    </span>
);

export { VerticalField };
