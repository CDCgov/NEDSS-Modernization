const prod = {
    modernizationUrl: `${window.location.protocol}//${window.location.host}`,
    pageBuilderUrl: `${window.location.protocol}//${window.location.host}/nbs/page-builder`,
    enableLogin: false,
    apiUrl: `${window.location.protocol}//${window.location.host}/api/deduplication/matching-configuration`
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
    apiUrl: `${window.location.protocol}//${window.location.host}/api/deduplication/matching-configuration`
};

// eslint-disable-next-line no-undef
export const Config = process.env.NODE_ENV === 'development' ? dev : prod;
