import { StrictMode } from 'react';
import { render } from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import { UserContextProvider } from './providers/UserContext';
import reportWebVitals from './reportWebVitals';
import { AppRoutes } from 'routes/AppRoutes';
import 'styles/global.scss';
import { AnalyticsProvider } from 'analytics';

render(
    <StrictMode>
        <BrowserRouter>
            <AnalyticsProvider>
                <UserContextProvider>
                    <AppRoutes />
                </UserContextProvider>
            </AnalyticsProvider>
        </BrowserRouter>
    </StrictMode>,
    document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
