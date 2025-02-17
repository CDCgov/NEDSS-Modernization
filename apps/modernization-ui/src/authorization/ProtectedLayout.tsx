import { Suspense } from 'react';
import { Await, Navigate, useLoaderData, useNavigate } from 'react-router-dom';

import { User, UserContextProvider } from 'providers/UserContext';

import { Configuration, ConfigurationProvider, useConfiguration } from 'configuration';
import { AnalyticsProvider } from 'analytics';
import { Spinner } from 'components/Spinner';
import { Layout } from '../layout/Layout';
import { InitializationLoaderResult } from './initializationLoader';
import IdleTimer from './IdleTimer';
import { currentUser } from 'user';

const ProtectedLayout = () => {
    const data = useLoaderData() as InitializationLoaderResult;
    const navigate = useNavigate();
    const {
        settings: { session }
    } = useConfiguration();

    const handleIdle = () => navigate('/expired');
    const handleIdleContinue = async () => {
        await currentUser();
    };

    const WithUser = (user: User) => {
        const data = useLoaderData() as InitializationLoaderResult;

        return (
            <UserContextProvider initial={user}>
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

    return (
        <Suspense fallback={<Spinner />}>
            <IdleTimer
                onIdle={handleIdle}
                onContinue={handleIdleContinue}
                timeout={session.warning}
                warningTimeout={session.expiration - session.warning}
            />
            <Await resolve={data?.user} errorElement={<Navigate to={'/login'} />}>
                {WithUser}
            </Await>
        </Suspense>
    );
};

export { ProtectedLayout };
