import { ApolloClient, ApolloLink, ApolloProvider, HttpLink, concat, InMemoryCache } from '@apollo/client';
import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import { TopBanner } from './components/TopBanner/TopBanner';
import reportWebVitals from './reportWebVitals';
import { AppRoutes } from './routes/AppRoutes';
import UserService from './services/UserService';
import './settings.scss';
import NavBar from './shared/header/NavBar';
import { Config } from './config';
// hard coded login for now
UserService.login('msa', '');

const authMiddleware = new ApolloLink((operation, forward) => {
    // grab the token from the userService
    const token = UserService.getUser()?.token;
    // Use the setContext method to set the HTTP headers.
    operation.setContext({
        headers: {
            Authorization: token ? `Bearer ${token}` : ''
        }
    });

    // Call the next link in the middleware chain.
    return forward(operation);
});

const client = new ApolloClient({
    link: concat(
        authMiddleware,
        new HttpLink({
            uri: `http://localhost:${Config.port}/graphql`
        })
    ),
    cache: new InMemoryCache()
});

ReactDOM.render(
    <React.StrictMode>
        <ApolloProvider client={client}>
            <BrowserRouter>
                <TopBanner />
                <NavBar />
                <div className="route-content">
                    <AppRoutes />
                </div>
            </BrowserRouter>
        </ApolloProvider>
    </React.StrictMode>,
    document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
