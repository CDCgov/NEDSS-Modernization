import { HeadingLevel } from 'components/heading';
import { AlertMessage } from 'design-system/message';
import { ReactNode } from 'react';

const ValidationErrorBanner = ({
    level,
    children,
    className,
}: {
    level: HeadingLevel;
    children: ReactNode;
    className?: string;
}) => (
    <AlertMessage type="error" title="Fix the following errors:" level={level} className={className}>
        {children}
    </AlertMessage>
);

const ValidationErrorSection = ({ id, title, children }: { id: string; title: string; children: ReactNode }) => (
    <>
        <p>
            For <a href={`#${id}`}>{title}</a>,
        </p>
        <ul>{children}</ul>
    </>
);

// Generates an error message that will contain a link to the section if an id is provided
const DirtySectionErrorMessage = ({ title, id }: { title: string; id?: string }) => {
    return (
        <>
            Data have been entered in the{' '}
            {id ? (
                <a id={`link-to-${id}`} href={`#${id}`}>
                    {title}
                </a>
            ) : (
                title
            )}{' '}
            section. Please press Add or clear the data and submit again.
        </>
    );
};

export { ValidationErrorBanner, ValidationErrorSection, DirtySectionErrorMessage };
