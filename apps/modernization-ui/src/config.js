const dedupBaseUrl = `http://localhost:8083`;

const prod = {
    modernizationUrl: `${window.location.protocol}//${window.location.host}`,
    pageBuilderUrl: `${window.location.protocol}//${window.location.host}/nbs/page-builder`,
    enableLogin: false,
    matchingConfigApiUrl: `${dedupBaseUrl}/api/deduplication/matching-configuration`,
    saveConfigApiUrl: `${dedupBaseUrl}/api/deduplication/configure-matching`
};

const dev = {
    modernizationUrl: `${window.location.protocol}//${window.location.host}`,
    pageBuilderUrl: `${window.location.protocol}//${window.location.host}/nbs/page-builder`,
    enableLogin: true,
    features: {
        address: {
            autocomplete: true,
            verification: true
        }
    },
    matchingConfigApiUrl: `${dedupBaseUrl}/api/deduplication/matching-configuration`,
    saveConfigApiUrl: `${dedupBaseUrl}/api/deduplication/configure-matching`
};

// eslint-disable-next-line no-undef
export const Config = process.env.NODE_ENV === 'development' ? dev : prod;
