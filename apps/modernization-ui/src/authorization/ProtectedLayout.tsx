import { Suspense } from 'react';
import { Await, Navigate, useLoaderData } from 'react-router-dom';
import { User, UserContextProvider } from 'providers/UserContext';

import { Configuration, ConfigurationProvider } from 'configuration';
import { AnalyticsProvider } from 'analytics';
import { Spinner } from 'components/Spinner';
import { Layout } from '../layout/Layout';
import { InitializationLoaderResult } from './initializationLoader';

const ProtectedLayout = () => {
    const data = useLoaderData() as InitializationLoaderResult;

    return (
        <Suspense fallback={<Spinner />}>
            <Await resolve={data?.user} errorElement={<Navigate to={'/login'} />}>
                {WithUser}
            </Await>
        </Suspense>
    );
};

const WithUser = (user: User) => {
    const data = useLoaderData() as InitializationLoaderResult;
    return (
        <UserContextProvider user={user}>
            <Await resolve={data?.configuration}>{WithConfiguration}</Await>
        </UserContextProvider>
    );
};

const WithConfiguration = (configuration: Configuration) => {
    return (
        <ConfigurationProvider initial={configuration}>
            <AnalyticsProvider>
                <Layout />
            </AnalyticsProvider>
        </ConfigurationProvider>
    );
};

export { ProtectedLayout };
