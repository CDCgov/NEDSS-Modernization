import { Navigate } from 'react-router-dom';
import { WelcomeLayout } from './WelcomeLayout';
import { About } from 'apps/landing/About/About';
import { Vision } from 'apps/landing/Vision/Vision';
import { Involved } from 'apps/landing/Involved/Involved';

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
            path: 'our-vision',
            element: <Vision />
        },
        {
            path: 'get-involved',
            element: <Involved />
        }
    ]
};

export { routing };
