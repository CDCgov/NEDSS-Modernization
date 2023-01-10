const prod = {
    nbsUrl: process.env.REACT_APP_NBS_URL
        ? process.env.REACT_APP_NBS_URL
        : `${window.location.protocol}//${window.location.host}/nbs`,
    modernizationUrl: `${window.location.protocol}//${window.location.host}`
};

const dev = {
    nbsUrl: `${window.location.protocol}//${window.location.host}/nbs`,
    modernizationUrl: `${window.location.protocol}//${window.location.host}`
};

// eslint-disable-next-line no-undef
export const Config = process.env.NODE_ENV === 'development' ? dev : prod;
