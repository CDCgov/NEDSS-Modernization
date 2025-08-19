import { get, maybeJson } from 'libs/api';

const selectableResolver = (url: string) =>
    fetch(get(url))
        .then(maybeJson)
        .then((response) => response ?? []);

export { selectableResolver };
