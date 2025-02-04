import { Suspense } from 'react';
import { Await, Navigate, useLoaderData, useNavigate } from 'react-router-dom';

import { User, UserContextProvider } from 'providers/UserContext';

import { Configuration, ConfigurationProvider, useConfiguration } from 'configuration';
import { AnalyticsProvider } from 'analytics';
import { Spinner } from 'components/Spinner';
import { Layout } from '../layout/Layout';
import { InitializationLoaderResult } from './initializationLoader';
import IdleTimer from './IdleTimer';

const ProtectedLayout = () => {
    const data = useLoaderData() as InitializationLoaderResult;
    const navigate = useNavigate();
    const {
        settings: { session }
    } = useConfiguration();

    const handleIdle = () => navigate('/expired');

    const withUser = (user: User) => {
        // Renamed to withUser (camelCase)
        return (
            <UserContextProvider initial={user}>
                <Suspense fallback={<Spinner />}>
                    {/* Added fallback for Suspense */}
                    <Await resolve={data?.configuration}>{withConfiguration}</Await>
                </Suspense>
            </UserContextProvider>
        );
    };

    const withConfiguration = (configuration: Configuration) => {
        // Renamed to withConfiguration (camelCase)
        return (
            <ConfigurationProvider initial={configuration}>
                <AnalyticsProvider>
                    <Layout />
                </AnalyticsProvider>
            </ConfigurationProvider>
        );
    };

    // Skip login redirection if in development
    if (process.env.NODE_ENV === 'development') {
        return withUser(data?.user); // Directly return the user data
    }

    return (
        <Suspense fallback={<Spinner />}>
            {/* Suspense with fallback */}
            <IdleTimer
                onIdle={handleIdle}
                timeout={session.warning}
                warningTimeout={session.expiration - session.warning}
            />
            <Await resolve={data?.user} errorElement={<Navigate to={'/login'} />}>
                {withUser}
            </Await>
        </Suspense>
    );
};

export { ProtectedLayout };
