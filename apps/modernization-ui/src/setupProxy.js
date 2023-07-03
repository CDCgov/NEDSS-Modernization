const { createProxyMiddleware } = require('http-proxy-middleware');

const NBS_API = '/nbs/api';
const LOGIN = '/login';
const GRAPHQL = '/graphql';
const ENCRYPTION = '/encryption';
const PAGEBUILDER_API = '/page-builder/api';

module.exports = function (app) {
    // Modernization API
    app.use(createProxyMiddleware(NBS_API, { target: 'http://localhost:8080/' }));
    app.use(createProxyMiddleware(LOGIN, { target: 'http://localhost:8080/' }));
    app.use(createProxyMiddleware(GRAPHQL, { target: 'http://localhost:8080/' }));
    app.use(createProxyMiddleware(ENCRYPTION, { target: 'http://localhost:8080/' }));

    // Page Builder API
    app.use(createProxyMiddleware(PAGEBUILDER_API, { target: 'http://localhost:8095/' }));
};
