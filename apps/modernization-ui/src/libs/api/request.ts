import { maybeMap } from 'utils/mapping';

const maybeAsJson = maybeMap((body: object | string | number) => JSON.stringify(body));

const put = (url: string, body?: object) => {
    return new Request(url, {
        method: 'PUT',
        body: maybeAsJson(body),
        headers: { 'Content-Type': 'application/json' },
        credentials: 'same-origin'
    });
};

export { put };
