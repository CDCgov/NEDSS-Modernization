import { compressToUTF16, decompressFromUTF16 } from 'lz-string';

/**
 * Open a new tab with the provided content available.
 *
 * The path should point to nbs, content will be placed in local storage at the provided key
 */
export const openNewTab = (path: string, content?: unknown, storageKey?: string) => {
    if (content && storageKey) {
        const json = JSON.stringify(content);
        const blob = compressToUTF16(json);
        localStorage.setItem(storageKey, blob);
    }
    window.open(path, '_blank', 'noopener,noreferrer');
};

export const fetchStoredData = <T>(storageKey: string): T | null => {
    const rawData = localStorage.getItem(storageKey);
    // clean up after the data to make sure it doesn't linger in cache
    localStorage.removeItem(storageKey);
    return rawData ? JSON.parse(decompressFromUTF16(rawData)) : null;
};
