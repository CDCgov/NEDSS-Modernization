import { StorageOptions, useStorage } from './useStorage';

const useLocalStorage = <V>(options: StorageOptions<V>) => useStorage({ storage: localStorage, ...options });

export { useLocalStorage };
