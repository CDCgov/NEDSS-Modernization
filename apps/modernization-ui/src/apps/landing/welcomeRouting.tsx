import { Navigate } from 'react-router';

const routing = {
    path: '/welcome',
    lazy: {
        Component: async () => (await import('./Layout/WelcomeLayout')).WelcomeLayout
    },
    children: [
        { index: true, element: <Navigate to="about" /> },
        {
            path: 'about',
            lazy: {
                Component: async () => (await import('apps/landing/About/About')).About
            }
        },
        {
            path: 'learn',
            lazy: {
                Component: async () => (await import('apps/landing/Learn/Learn')).Learn
            }
        }
    ]
};

export { routing };
