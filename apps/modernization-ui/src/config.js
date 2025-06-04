const prod = {
    modernizationUrl: `${window.location.protocol}//${window.location.host}`,
    pageBuilderUrl: `${window.location.protocol}//${window.location.host}/nbs/page-builder`,
    deduplicationUrl: `${window.location.protocol}//${window.location.host}/nbs/api/deduplication`,
    enableLogin: false
};

const dev = {
    ...prod,
    enableLogin: true,
    features: {
        address: {
            autocomplete: true,
            verification: true
        }
    }
};

// eslint-disable-next-line no-undef
export const Config = import.meta.env.MODE === 'development' ? dev : prod;
