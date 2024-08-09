import { ReactNode } from 'react';
import classNames from 'classnames';
import { ErrorMessage, Grid, Label } from '@trussworks/react-uswds';
import styles from './entry-wrapper.module.scss';

type Props = {
    className?: string;
    htmlFor?: string;
    label?: string;
    error?: string;
    required?: boolean;
    children: ReactNode;
};

const HorizontalEntryWrapper = ({ className, htmlFor, label, required, error, children }: Props) => (
    <Grid row className={className}>
        <Grid col={6} className={styles.hLabel}>
            {label && htmlFor ? (
                <Label className={classNames({ required })} htmlFor={htmlFor}>
                    {label}
                </Label>
            ) : null}
            {error && <ErrorMessage id={`${htmlFor}-error`}>{error}</ErrorMessage>}
        </Grid>
        <Grid col={6} className={styles.hContent}>
            {children}
        </Grid>
    </Grid>
);

export { HorizontalEntryWrapper };
