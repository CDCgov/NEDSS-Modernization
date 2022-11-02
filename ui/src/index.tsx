import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import ApolloWrapper from './providers/ApolloContext';
// import { TopBanner } from './components/TopBanner/TopBanner';
import { UserContextProvider } from './providers/UserContext';
import reportWebVitals from './reportWebVitals';
import { AppRoutes } from './routes/AppRoutes';
import './settings.scss';
import NavBar from './shared/header/NavBar';

ReactDOM.render(
    <React.StrictMode>
        <UserContextProvider>
            <ApolloWrapper>
                <BrowserRouter>
                    {/* <TopBanner /> */}
                    <div style={{ padding: '5px' }}>
                        <NavBar />
                        <div className="route-content">
                            <AppRoutes />
                        </div>
                    </div>
                </BrowserRouter>
            </ApolloWrapper>
        </UserContextProvider>
    </React.StrictMode>,
    document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
