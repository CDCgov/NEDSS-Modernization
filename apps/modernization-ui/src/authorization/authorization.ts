type TokenProvider = () => string | undefined;

const TOKEN = 'nbs_token=';

const getToken: TokenProvider = () => {
    if (document.cookie.includes(TOKEN)) {
        const tokenStart = document.cookie.indexOf(TOKEN) + TOKEN.length;
        const tokenEnd = document.cookie.indexOf(';', tokenStart);
        return document.cookie.substring(tokenStart, tokenEnd > -1 ? tokenEnd : document.cookie.length);
    } else {
        return undefined;
    }
};

const authorization = () => `Bearer ${getToken()}`;

export { authorization };
