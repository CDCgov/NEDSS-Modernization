const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
    app.use(createProxyMiddleware('/modernization-api', { target: 'http://localhost:8080/' }));
    app.use(createProxyMiddleware('/pageBuilder', { target: 'http://localhost:8095/' }));
};
