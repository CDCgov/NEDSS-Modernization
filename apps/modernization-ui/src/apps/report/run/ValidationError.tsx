import { HeadingLevel } from 'components/heading';
import { AlertMessage } from 'design-system/message';
import { ReactNode } from 'react';

const ValidationErrorBanner = ({ level, children }: { level: HeadingLevel; children: ReactNode }) => (
    <AlertMessage type="error" title="Fix the following errors:" level={level}>
        {children}
    </AlertMessage>
);

const ValidationErrorSection = ({ id, title, children }: { id: string; title: string; children: ReactNode }) => (
    <div className="usa-prose">
        <p>
            For <a href={`#${id}`}>{title}</a>,
        </p>
        <ul>{children}</ul>
    </div>
);

export { ValidationErrorBanner, ValidationErrorSection };
