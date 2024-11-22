import { ReactNode } from 'react';
import classNames from 'classnames';
import { ErrorMessage, Label } from '@trussworks/react-uswds';
import styles from './entry-wrapper.module.scss';

type Props = {
    className?: string;
    htmlFor: string;
    label: string;
    helperText?: string;
    error?: string;
    required?: boolean;
    children: ReactNode;
};

const VerticalEntryWrapper = ({ className, htmlFor, label, helperText, required, error, children }: Props) => (
    <span className={className}>
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
        {error && <ErrorMessage id={`${error}-message`}>{error}</ErrorMessage>}
        {children}
    </span>
);

export { VerticalEntryWrapper };
