import { ApolloClient, ApolloProvider, InMemoryCache, createHttpLink } from '@apollo/client';
import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
// import { TopBanner } from './components/TopBanner/TopBanner';
import reportWebVitals from './reportWebVitals';
import { AppRoutes } from './routes/AppRoutes';
import UserService from './services/UserService';
import './settings.scss';
import NavBar from './shared/header/NavBar';
import { Config } from './config';
import { setContext } from '@apollo/client/link/context';

const httpLink = createHttpLink({
    uri: `http://localhost:${Config.port}/graphql`
});

const authMiddleware = setContext(async (_, { headers }) => {
    let header = {};
    // We are already logged in, use current user
    if (UserService.getUser()) {
        header = {
            ...headers,
            authorization: `Bearer ${UserService.getUser()?.token}`
        };
    } else {
        // use the userId cookie set from the proxied request
        const userId = UserService.getUserIdFromCookie();
        if (userId) {
            await UserService.login(userId, '').then(async (response) => {
                // grab the token from the userService
                const token = response?.token;
                // Use the setContext method to set the HTTP headers.
                header = {
                    ...headers,
                    authorization: token ? `Bearer ${token}` : ''
                };
            });
        } else {
            throw new Error('User not logged in');
        }
    }
    return {
        headers: header
    };
});
const client = new ApolloClient({
    link: authMiddleware.concat(httpLink),
    cache: new InMemoryCache()
});

ReactDOM.render(
    <React.StrictMode>
        <ApolloProvider client={client}>
            <BrowserRouter>
                {/* <TopBanner /> */}
                <div style={{ padding: '5px' }}>
                    <NavBar />
                    <div className="route-content">
                        <AppRoutes />
                    </div>
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
