import { ReactNode } from 'react';
import classNames from 'classnames';
import { ErrorMessage, Label } from '@trussworks/react-uswds';
import styles from './horizontal-wrapper.module.scss';

type Props = {
    className?: string;
    htmlFor: string;
    label: string;
    helperText?: string;
    error?: string;
    required?: boolean;
    children: ReactNode;
};

const HorizontalEntryWrapper = ({ className, htmlFor, label, helperText, required, error, children }: Props) => (
    <div className={classNames(styles.horizontalInput, className)}>
        <div className={styles.left}>
            {label && (
                <Label className={classNames({ required })} htmlFor={htmlFor}>
                    {label}
                </Label>
            )}
            {helperText && (
                <span id={`${htmlFor}-hint`} className={styles.helptext}>
                    {helperText}
                </span>
            )}
        </div>
        <div className={styles.right}>
            {error && <ErrorMessage id={`${htmlFor}-error`}>{error}</ErrorMessage>}
            {children}
        </div>
    </div>
);

export { HorizontalEntryWrapper };
