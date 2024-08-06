import { ReactNode } from 'react';
import classNames from 'classnames';
import { ErrorMessage, Grid, Label } from '@trussworks/react-uswds';

type Props = {
    className?: string;
    htmlFor: string;
    label: string;
    error?: string;
    required?: boolean;
    children: ReactNode;
};

const HorizontalEntryWrapper = ({ className, htmlFor, label, required, error, children }: Props) => (
    <Grid row className={className}>
        <Grid col={6}>
            {label && (
                <Label className={classNames({ required })} htmlFor={htmlFor}>
                    {label}
                </Label>
            )}
            {error && <ErrorMessage id={`${htmlFor}-error`}>{error}</ErrorMessage>}
        </Grid>
        <Grid col={6}>{children}</Grid>
    </Grid>
);

export { HorizontalEntryWrapper };
