const prod = {
    port: 8080
};

const dev = {
    port: 3000
};

export const Config = process.env.NODE_ENV === 'development' ? dev : prod;
