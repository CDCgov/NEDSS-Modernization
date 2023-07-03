const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
    // Modernization API
    app.use(createProxyMiddleware('/nbs/api', { target: 'http://localhost:8080/' }));
    app.use(createProxyMiddleware('/login', { target: 'http://localhost:8080/' }));
    app.use(createProxyMiddleware('/graphql', { target: 'http://localhost:8080/' }));
    app.use(createProxyMiddleware('/encryption', { target: 'http://localhost:8080/' }));

    // Page Builder API
    app.use(createProxyMiddleware('/page-builder/api', { target: 'http://localhost:8095/' }));
};
