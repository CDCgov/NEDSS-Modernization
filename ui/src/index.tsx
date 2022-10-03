import React from 'react';
import ReactDOM from 'react-dom';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from 'react-router-dom';
import './settings.scss';
import NavBar from './shared/header/NavBar';
import { AppRoutes } from './routes/AppRoutes';
import { ApolloClient, ApolloProvider, HttpLink, InMemoryCache } from '@apollo/client';
import { TopBanner } from './components/TopBanner/TopBanner';

const client = new ApolloClient({
    link: new HttpLink({
        uri: 'http://localhost:3000/graphql'
        // fetchOptions: {
        //   mode: 'no-cors'
        // }
    }),
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
