import { Suspense } from 'react';
import { Await, Navigate, useLoaderData, useNavigate } from 'react-router';
import { User, UserContextProvider } from 'providers/UserContext';
import { currentUser } from 'user';
import { Configuration, ConfigurationProvider } from 'configuration';
import { AnalyticsProvider } from 'analytics';
import { Layout } from 'layout';
import { Spinner } from 'components/Spinner';
import { InitializationLoaderResult } from './initializationLoader';
import IdleTimer from './IdleTimer';

const ProtectedLayout = () => {
    const data = useLoaderData() as InitializationLoaderResult;
    const navigate = useNavigate();

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
                <IdleTimer
                    onIdle={handleIdle}
                    onContinue={handleIdleContinue}
                    timeout={configuration.settings.session.warning}
                    warningTimeout={configuration.settings.session.expiration - configuration.settings.session.warning}
                />
                <AnalyticsProvider>
                    <Layout />
                </AnalyticsProvider>
            </ConfigurationProvider>
        );
    };

    return (
        <Suspense fallback={<Spinner />}>
            <Await resolve={data?.user} errorElement={<Navigate to={'/login'} />}>
                {WithUser}
            </Await>
        </Suspense>
    );
};

export { ProtectedLayout };
