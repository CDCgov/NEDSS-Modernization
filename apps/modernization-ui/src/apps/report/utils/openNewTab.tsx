/**
 * Open the provided content in a new tab.
 *
 * The path should point to nbs, content will be placed in local storage at the provided key
 */
export const openNewTab = (path: string, content?: string, storageKey?: string) => {
    if (content && storageKey) localStorage.setItem(storageKey, content);
    window.open(path, '_blank', 'noopener,noreferrer');
};
