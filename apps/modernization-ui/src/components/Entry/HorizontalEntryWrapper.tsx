import { ReactNode } from 'react';
import classNames from 'classnames';
import { ErrorMessage, Label } from '@trussworks/react-uswds';
import styles from './horizontal-wrapper.module.scss';

type Props = {
    className?: string;
    htmlFor: string;
    label: string;
    error?: string;
    required?: boolean;
    children: ReactNode;
};

const HorizontalEntryWrapper = ({ className, htmlFor, label, required, error, children }: Props) => (
    <div className={classNames(styles.horizontalInput, { [styles.error]: error }, className)}>
        {label && (
            <Label className={classNames({ required })} htmlFor={htmlFor}>
                {label}
            </Label>
        )}
        <div className={styles.verticalWrapper}>
            {error && <ErrorMessage id={`${htmlFor}-error`}>{error}</ErrorMessage>}
            {children}
        </div>
    </div>
);

export { HorizontalEntryWrapper };
