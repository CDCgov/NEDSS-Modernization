import { FeatureGuard } from 'feature';
import { Navigate } from 'react-router-dom';
import { WelcomeLayout } from './Layout/WelcomeLayout';
import { About } from 'apps/landing/About/About';
import { Learn } from 'apps/landing/Learn/Learn';

const routing = {
    path: '/welcome',
    element: <WelcomeLayout />,
    children: [
        {
            index: true,
            element: (
                <FeatureGuard guard={(features) => features.welcome.enabled}>
                    <Navigate to="about" />
                </FeatureGuard>
            )
        },
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
