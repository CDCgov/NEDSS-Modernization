import { ReactNode } from 'react';
import { HorizontalEntryWrapper } from './HorizontalEntryWrapper';
import { VerticalEntryWrapper } from './VerticalEntryWrapper';

type Props = {
    orientation: 'horizontal' | 'vertical';
    htmlFor: string;
    label: string;
    error?: string;
    required?: boolean;
    children: ReactNode;
};

const EntryWrapper = ({ orientation = 'vertical', htmlFor, label, required, error, children }: Props) => {
    if (orientation === 'horizontal') {
        return (
            <HorizontalEntryWrapper htmlFor={htmlFor} label={label} required={required} error={error}>
                {children}
            </HorizontalEntryWrapper>
        );
    }

    return (
        <VerticalEntryWrapper htmlFor={htmlFor} label={label} required={required} error={error}>
            {children}
        </VerticalEntryWrapper>
    );
};

export { EntryWrapper };
