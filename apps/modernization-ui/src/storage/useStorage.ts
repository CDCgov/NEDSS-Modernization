import { useCallback, useState } from 'react';

type StorageInteraction<V> = {
    value?: V;
    save: (value: V) => void;
    remove: () => void;
};

type StorageOptions<V> = {
    key: string;
    initial?: V;
};

const useStorage = <V>({ storage, key, initial }: StorageOptions<V> & { storage: Storage }): StorageInteraction<V> => {
    const read = useCallback((id: string) => readFrom(storage)(id, initial), [storage, key, initial]);

    const [value, setValue] = useState(() => read(key));

    const save = (value: V) => {
        storage.setItem(key, serializer(value));
        setValue(value);
    };
    const remove = () => {
        storage.removeItem(key);
        setValue(undefined);
    };

    return {
        value,
        save,
        remove
    };
};

const serializer = <I>(value: I) => JSON.stringify(value);

const readFrom =
    (storage: Storage) =>
    <O>(key: string, fallback?: O) => {
        const stored = storage.getItem(key);

        return (stored && deserializer<O>(stored, fallback)) || fallback;
    };

const deserializer = <O>(value: string, fallback?: O) => {
    try {
        const deserialized = JSON.parse(value);
        return deserialized as O;
    } catch (error) {
        return fallback;
    }
};

export { useStorage };
export type { StorageInteraction, StorageOptions };
