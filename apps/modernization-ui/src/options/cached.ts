import { parseISO } from 'date-fns';
import { Supplier } from 'libs/supplying';
import { exists } from 'utils/exists';
import { mapOr } from 'utils/mapping';

const isUseable = mapOr((value: string) => parseISO(value) > new Date(), false);

type CacheEntry<V> = {
    expires: string;
    value: V;
};

type CacheSettings = {
    /** The key used to identify the value in storage. */
    id: string;
    storage: Storage;
    expiration: Supplier<Date>;
};

const cache =
    <I>(settings: CacheSettings) =>
    (promise: Supplier<Promise<I>>): Promise<I> => {
        return retrieve<I>(settings).then((found) => {
            if (found) {
                console.log('using cached values for ', settings.id);
                return found;
            }

            // no value is currently cached, call the promise
            console.log('retrieving values for ', settings.id);
            return promise().then((resolved) => {
                if (exists(resolved)) {
                    // a values has been produced, store it
                    store(settings)(resolved);
                }

                return resolved;
            });
        });
    };

const retrieve = <V>(settings: CacheSettings) =>
    new Promise<V | undefined>((resolve) => {
        const found = settings.storage.getItem(settings.id);

        if (found) {
            const entry = JSON.parse(found) as CacheEntry<V>;

            const useable = isUseable(entry.expires);

            if (useable) {
                resolve(entry.value);
            }
        }
        resolve(undefined);
    });

const store =
    <V>(settings: CacheSettings) =>
    (value: V) => {
        const expires = settings.expiration();
        const entry = JSON.stringify({ value, expires });
        settings.storage.setItem(settings.id, entry);
    };

export { cache, type CacheSettings };
