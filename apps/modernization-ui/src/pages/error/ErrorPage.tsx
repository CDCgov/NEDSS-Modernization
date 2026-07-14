import { Heading } from 'components/heading';
import { isRouteErrorResponse, useRouteError } from 'react-router';
import { logErrorToUserConsole } from 'utils/logging';
import { NotFoundError } from './NotFoundError';
import { FullPageBlock } from 'components/FullPageBlock';
import { ApiError } from 'generated';
import { LinkButton } from 'design-system/button';

const ErrorPage = () => {
    const error = useRouteError();

    logErrorToUserConsole(error);

    return (
        <main className="display-flex flex-column">
            <FullPageBlock>
                {isRouteErrorResponse(error) || error instanceof NotFoundError || error instanceof ApiError ? (
                    <>
                        <p className="text-base text-bold margin-y-4" style={{ fontSize: '8rem' }}>
                            {error.status}
                        </p>
                        <Heading level={1}>{error.statusText}</Heading>
                        {error.status === 404 ? (
                            <>
                                <p className="margin-top-4 margin-bottom-0">
                                    We couldn't find the page you were looking for.
                                </p>
                                <p className="margin-top-1 margin-bottom-1">
                                    Check the URL to make sure it's correct and try again.
                                </p>
                            </>
                        ) : (
                            <p>{'data' in error ? JSON.stringify(error.data) : error?.body?.message}</p>
                        )}
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
                <LinkButton className="margin-top-4" href="/nbs/HomePage.do?method=loadHomePage">
                    Return to home
                </LinkButton>
            </FullPageBlock>
        </main>
    );
};

export { ErrorPage };
