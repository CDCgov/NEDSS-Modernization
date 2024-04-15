import { Navigate } from 'react-router-dom';
import { WelcomeLayout } from './WelcomeLayout';

const routing = {
    path: '/login',
    element: <WelcomeLayout />,
    children: [
        { path: '', element: <Navigate to="about" /> },
        {
            path: 'about',
            // to be added with actual elements/component
            element: <h1>About</h1>
        },
        {
            path: 'our-vision',
            // to be added with actual elements/component
            element: <h1>Our Vision</h1>
        },
        {
            path: 'get-involved',
            // to be added with actual elements/component
            element: <h1>get-involved</h1>
        }
    ]
};

export { routing };
