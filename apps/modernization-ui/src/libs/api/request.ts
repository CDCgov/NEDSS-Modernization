import { maybeMap } from 'utils/mapping';

const maybeAsJson = maybeMap((body: object | string | number) => JSON.stringify(body));

/**
 * Creates a standard GET request to the API.
 *
 * @param {string} url The URL of the request.
 * @return {Request} The resulting Request.
 */
const get = (url: string) => {
    return new Request(url, {
        method: 'GET',
        headers: { Accept: 'application/json' },
        credentials: 'same-origin'
    });
};

/**
 * Creates a standard PUT request to the API with an optional request `body`.  If a body is provided it is converted to JSON.
 *
 * @param {string} url The URL of the request.
 * @param {object} body The body of the request.
 * @return {Request} The resulting Request
 */
const put = (url: string, body?: object) => {
    return new Request(url, {
        method: 'PUT',
        body: maybeAsJson(body),
        headers: { 'Content-Type': 'application/json' },
        credentials: 'same-origin'
    });
};

export { get, put };
