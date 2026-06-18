import { Heading } from 'components/heading';
import { isRouteErrorResponse, useRouteError } from 'react-router';
import { logErrorToUserConsole } from 'utils/logging';
import { NotFoundError } from './NotFoundError';

const ErrorPage = () => {
    let error = useRouteError();

    logErrorToUserConsole(error);

    return (
        <main className="margin-8">
            {isRouteErrorResponse(error) || error instanceof NotFoundError ? (
                <>
                    <Heading level={1}>
                        {error.status} {error.statusText}
                    </Heading>
                    <p>{error.data}</p>
                </>
            ) : error instanceof Error ? (
                <>
                    <Heading level={1}>Error</Heading>
                    <p>{error.message}</p>
                    <p>The stack trace is:</p>
                    <pre>{error.stack}</pre>
                </>
            ) : (
                <>
                    <Heading level={1}>Unknown Error</Heading>
                    <p>{JSON.stringify(error)}</p>
                </>
            )}
        </main>
    );
};

export { ErrorPage };
