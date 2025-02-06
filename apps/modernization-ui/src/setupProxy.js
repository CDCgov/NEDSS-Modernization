const { createProxyMiddleware } = require('http-proxy-middleware');

const PORT = process.env.PROXY_PORT ?? '8080';
// Port for the deduplication APIs
const dedupPort = '8083';

const target = `http://localhost:${PORT}/`;
// Deduplication API target (for matching and save config)
const dedupTarget = `http://localhost:${dedupPort}`;

const NBS_API = '/nbs/api';
const GRAPHQL = '/graphql';
const ENCRYPTION = '/encryption';
const PAGEBUILDER_API = '/nbs/page-builder/api';

// Deduplication-specific proxy routes (handled by localhost:8083)
const MATCHING_CONFIG_API = '/api/deduplication/matching-configuration';
const SAVE_CONFIG_API = '/api/deduplication/configure-matching';

// only forward POST methods so the login page can load
const LOGIN = function (pathname, req) {
    return pathname.match('^/login') && req.method === 'POST';
};

module.exports = function (app) {
    app.use(createProxyMiddleware([NBS_API, GRAPHQL, ENCRYPTION, PAGEBUILDER_API], { target }));
    // Proxy for deduplication routes (handled by dedupTarget at port 8083)
    app.use(createProxyMiddleware(MATCHING_CONFIG_API, { target: dedupTarget }));
    app.use(createProxyMiddleware(SAVE_CONFIG_API, { target: dedupTarget }));

    app.use(createProxyMiddleware(LOGIN, { target }));
};
