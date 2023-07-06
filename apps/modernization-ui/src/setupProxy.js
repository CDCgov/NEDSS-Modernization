const { createProxyMiddleware } = require('http-proxy-middleware');

const NBS_API = '/nbs/api';
const GRAPHQL = '/graphql';
const ENCRYPTION = '/encryption';
const PAGEBUILDER_API = '/page-builder/api';

// only forward POST methods so the login page can load
const LOGIN = function (pathname, req) {
    return pathname.match('^/login') && req.method === 'POST';
};

module.exports = function (app) {
    // Modernization API
    app.use(createProxyMiddleware([NBS_API, GRAPHQL, ENCRYPTION], { target: 'http://localhost:8080/' }));
    app.use(createProxyMiddleware(LOGIN, { target: 'http://localhost:8080/' }));

    // Page Builder API
    app.use(createProxyMiddleware(PAGEBUILDER_API, { target: 'http://localhost:8095/' }));
};
