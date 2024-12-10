import { ReactNode } from 'react';
import classNames from 'classnames';
import { HelperText } from './HelperText';
import { InlineErrorMessage } from './InlineErrorMessage';

import styles from './vertical-field.module.scss';

type Props = {
    className?: string;
    htmlFor: string;
    label: string;
    helperText?: string;
    error?: string;
    required?: boolean;
    children: ReactNode;
};

const VerticalField = ({ className, htmlFor, label, helperText, required, error, children }: Props) => (
    <span className={classNames(styles.entry, className, { [styles.error]: error })}>
        {label && (
            <label className={classNames({ required })} htmlFor={htmlFor}>
                {label}
            </label>
        )}
        {helperText && <HelperText id={`${htmlFor}-hint`}>{helperText}</HelperText>}
        {error && <InlineErrorMessage id={`${error}-error`}>{error}</InlineErrorMessage>}
        {children}
    </span>
);

export { VerticalField };
