const prod = {
    port: 8080
};

const dev = {
    port: 3000
};

// eslint-disable-next-line no-undef
export const Config = process.env.NODE_ENV === 'development' ? dev : prod;
