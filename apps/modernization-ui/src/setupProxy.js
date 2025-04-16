const { createProxyMiddleware } = require('http-proxy-middleware');

const PORT = process.env.PROXY_PORT ?? '8080';

const target = `http://localhost:${PORT}/`;

const deduplication = 'http://localhost:8083';

const NBS_API = '/nbs/api';
const GRAPHQL = '/graphql';
const ENCRYPTION = '/encryption';
const PAGEBUILDER_API = '/nbs/page-builder/api';
const DEDUPLICATION_API = '/nbs/api/deduplication';

// only forward POST methods so the login page can load
const LOGIN = function (pathname, req) {
    return pathname.match('^/login') && req.method === 'POST';
};

module.exports = function (app) {
    app.use(createProxyMiddleware([DEDUPLICATION_API], { target: deduplication }));
    app.use(createProxyMiddleware([NBS_API, GRAPHQL, ENCRYPTION, PAGEBUILDER_API], { target }));
    app.use(createProxyMiddleware(LOGIN, { target }));
};
