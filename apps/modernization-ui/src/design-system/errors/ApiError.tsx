import { HeadingLevel } from 'components/heading';
import { AlertMessage } from 'design-system/message';
import { ApiError } from 'generated';
import { logErrorToUserConsole } from 'utils/logging';

const ApiErrorBanner = ({
    action,
    item,
    level = 2,
    error,
    className,
}: {
    action: string;
    item: string;
    level?: HeadingLevel;
    error: unknown;
    className?: string;
}) => {
    logErrorToUserConsole(error);
    return (
        <AlertMessage
            type="error"
            className={className}
            // eslint-disable-next-line max-len
            title={`There was an error ${action} this ${item}. If this error persists, contact your system administrator.`}
            level={level}>
            <p>{error instanceof Error && error.name} Details:</p>
            {error instanceof ApiError ? (
                <>
                    <pre>
                        {error.status} {error.statusText}
                    </pre>
                    {!!error.body.message && <pre>{error.body.message}</pre>}
                </>
            ) : error instanceof Error ? (
                <>
                    <pre>{error.message}</pre>
                    <p>The stack trace is:</p>
                    <pre>{error.stack}</pre>
                </>
            ) : typeof error === 'string' ? (
                <pre>{error}</pre>
            ) : (
                <pre>{JSON.stringify(error, null, 2)}</pre>
            )}
        </AlertMessage>
    );
};

export { ApiErrorBanner };
