import { ReactNode } from 'react';
import classNames from 'classnames';
import { ErrorMessage, Grid, Label } from '@trussworks/react-uswds';

type Props = {
    htmlFor: string;
    label: string;
    error?: string;
    required?: boolean;
    children: ReactNode;
};

const HorizontalEntryWrapper = ({ htmlFor, label, required, error, children }: Props) => (
    <Grid row>
        <Grid col={6}>
            {label && (
                <Label className={classNames({ required })} htmlFor={htmlFor}>
                    {label}
                </Label>
            )}
            {error && <ErrorMessage id={`${error}-message`}>{error}</ErrorMessage>}
        </Grid>
        <Grid col={6}>{children}</Grid>
    </Grid>
);

export { HorizontalEntryWrapper };
