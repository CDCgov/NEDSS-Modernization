const prod = {
    modernizationUrl: `${window.location.protocol}//${window.location.host}`,
    pageBuilderUrl: `${window.location.protocol}//${window.location.host}/nbs/page-builder`,
    enableLogin: false,
    matchingConfigApiUrl: `${window.location.protocol}//${window.location.host}/api/deduplication/matching-configuration`,
    saveConfigApiUrl: `${window.location.protocol}//${window.location.host}/api/deduplication/configure-matching`
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
    matchingConfigApiUrl: `${window.location.protocol}//${window.location.host}/api/deduplication/matching-configuration`,
    saveConfigApiUrl: `${window.location.protocol}//${window.location.host}/api/deduplication/configure-matching`
};

// eslint-disable-next-line no-undef
export const Config = process.env.NODE_ENV === 'development' ? dev : prod;
