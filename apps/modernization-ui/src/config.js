const prod = {
    modernizationUrl: `${window.location.protocol}//${window.location.host}`,
    pageBuilderUrl: `${window.location.protocol}//${window.location.host}/nbs/page-builder`,
    deduplicationUrl: `${window.location.protocol}//${window.location.host}/nbs/api/deduplication`,
    enableLogin: false
};

const dev = {
    modernizationUrl: `${window.location.protocol}//${window.location.host}`,
    pageBuilderUrl: `${window.location.protocol}//${window.location.host}/nbs/page-builder`,
    deduplicationUrl: `${window.location.protocol}//${window.location.host}/nbs/api/deduplication`,
    enableLogin: true,
    features: {
        address: {
            autocomplete: true,
            verification: true
        }
    }
};

// eslint-disable-next-line no-undef
export const Config = process.env.NODE_ENV === 'development' ? dev : prod;
