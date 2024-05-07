import { Suspense } from 'react';
import { Await, Navigate, redirect, useLoaderData } from 'react-router-dom';
import { User, UserContextProvider } from 'providers/UserContext';

import { Configuration, ConfigurationProvider } from 'configuration';
import { AnalyticsProvider } from 'analytics';
import { Spinner } from 'components/Spinner';
import { Layout } from '../layout/Layout';
import { InitializationLoaderResult } from './initializationLoader';
import IdleTimer from './IdleTimer';

const ProtectedLayout = () => {
    const data = useLoaderData() as InitializationLoaderResult;

    const handleIdle = () => {
        redirect('/nbs/logout/');
    };

    // const timeout = 1000 * 60 * 15; // 15 minutes
    const timeout = 3000;

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
            <IdleTimer onIdle={handleIdle} timeout={timeout} />
            <Await resolve={data?.user} errorElement={<Navigate to={'/login'} />}>
                {WithUser}
            </Await>
        </Suspense>
    );
};

// const WithUser = (user: User) => {
//     const data = useLoaderData() as InitializationLoaderResult;
//     return (
//         <UserContextProvider initial={user}>
//             <Await resolve={data?.configuration}>{WithConfiguration}</Await>
//         </UserContextProvider>
//     );
// };

// const WithConfiguration = (configuration: Configuration) => {
//     return (
//         <ConfigurationProvider initial={configuration}>
//             <AnalyticsProvider>
//                 <Layout />
//             </AnalyticsProvider>
//         </ConfigurationProvider>
//     );
// };

export { ProtectedLayout };
