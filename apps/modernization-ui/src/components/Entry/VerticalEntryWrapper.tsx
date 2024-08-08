import { ReactNode } from 'react';
import classNames from 'classnames';
import { ErrorMessage, Label } from '@trussworks/react-uswds';

type Props = {
    className?: string;
    htmlFor: string;
    label: string;
    error?: string;
    required?: boolean;
    children: ReactNode;
};

const VerticalEntryWrapper = ({ className, htmlFor, label, required, error, children }: Props) => (
    <span className={className}>
        {label && (
            <Label className={classNames({ required })} htmlFor={htmlFor}>
                {label}
            </Label>
        )}
        {error && <ErrorMessage id={`${error}-message`}>{error}</ErrorMessage>}
        {children}
    </span>
);

export { VerticalEntryWrapper };
