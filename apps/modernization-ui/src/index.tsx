import { StrictMode } from 'react';
import { render } from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import reportWebVitals from './reportWebVitals';
import { UserContextProvider } from './providers/UserContext';
import { AppRoutes } from 'routes/AppRoutes';
import { AnalyticsProvider } from 'analytics';

import 'styles/global.scss';
import { ConfigurationProvider } from 'configuration';

render(
    <StrictMode>
        <BrowserRouter>
            <ConfigurationProvider>
                <AnalyticsProvider>
                    <UserContextProvider>
                        <AppRoutes />
                    </UserContextProvider>
                </AnalyticsProvider>
            </ConfigurationProvider>
        </BrowserRouter>
    </StrictMode>,
    document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
