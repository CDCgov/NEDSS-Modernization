import { Heading, HeadingLevel } from 'components/heading';
import { AlertMessage } from 'design-system/message';
import { ReactNode } from 'react';

const ValidationErrorBanner = ({ level, children }: { level: HeadingLevel; children: ReactNode }) => (
    <AlertMessage type="error">
        <Heading level={level}>Fix the following errors:</Heading>
        {children}
    </AlertMessage>
);

const ValidationErrorChunk = ({ id, title, children }: { id: string; title: string; children: ReactNode }) => (
    <div key={id} className="usa-prose">
        <p>
            For <a href={`#${id}`}>{title}</a>,
        </p>
        <ul>{children}</ul>
    </div>
);

export { ValidationErrorBanner, ValidationErrorChunk };
