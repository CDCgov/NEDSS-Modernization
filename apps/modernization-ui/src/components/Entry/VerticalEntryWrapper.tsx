import { ReactNode } from 'react';
import classNames from 'classnames';
import { ErrorMessage, Label } from '@trussworks/react-uswds';

type Props = {
    htmlFor: string;
    label: string;
    error?: string;
    required?: boolean;
    children: ReactNode;
};

const VerticalEntryWrapper = ({ htmlFor, label, required, error, children }: Props) => (
    <>
        <Label className={classNames({ required })} htmlFor={htmlFor}>
            {label}
        </Label>
        {error && <ErrorMessage id={`${error}-message`}>{error}</ErrorMessage>}
        {children}
    </>
);

export { VerticalEntryWrapper };
