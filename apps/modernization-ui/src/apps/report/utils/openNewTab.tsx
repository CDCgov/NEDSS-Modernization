/**
 * Open a new tab with the provided content available.
 *
 * The path should point to nbs, content will be placed in local storage at the provided key
 */
export const openNewTab = (path: string, content?: unknown, storageKey?: string) => {
    if (content && storageKey) localStorage.setItem(storageKey, JSON.stringify(content));
    window.open(path, '_blank', 'noopener,noreferrer');
};
