const { createProxyMiddleware } = require('http-proxy-middleware');

const NBS_API = '/nbs/api';
const GRAPHQL = '/graphql';
const ENCRYPTION = '/encryption';
const PAGEBUILDER_API = '/nbs/page-builder/api';

// only forward POST methods so the login page can load
const LOGIN = function (pathname, req) {
    return pathname.match('^/login') && req.method === 'POST';
};

module.exports = function (app) {
    app.use(
        createProxyMiddleware([NBS_API, GRAPHQL, ENCRYPTION, PAGEBUILDER_API], { target: 'http://localhost:8080/' })
    );
    app.use(createProxyMiddleware(LOGIN, { target: 'http://localhost:8080/' }));
};
