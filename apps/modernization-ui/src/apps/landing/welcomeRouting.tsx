import { Navigate } from 'react-router';
import { WelcomeLayout } from './Layout/WelcomeLayout';
import { About } from 'apps/landing/About/About';
import { Learn } from 'apps/landing/Learn/Learn';

const routing = {
    path: '/welcome',
    element: <WelcomeLayout />,
    children: [
        { index: true, element: <Navigate to="about" /> },
        {
            path: 'about',
            element: <About />
        },
        {
            path: 'learn',
            element: <Learn />
        }
    ]
};

export { routing };
