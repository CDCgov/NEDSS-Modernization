import { ReactNode } from 'react';
import { createRoot } from 'react-dom/client';

/**
 * Open the provided content in a new tab.
 *
 * The styles are copied over and the title is set.
 */
export const useNewTab = () => {
    const openNewTab = (content: ReactNode) => {
        let newWindow = window.open('', '_blank', '');

        const div = document.createElement('div');
        const root = createRoot(div);
        root.render(content);
        if (newWindow) {
            newWindow.document.body.appendChild(div);
            newWindow.document.title = 'NBS Report';
            copyStyles(document, newWindow.document);
        }
    };

    return {
        openNewTab,
    };
};

const copyStyles = (sourceDoc: Document, targetDoc: Document) => {
    for (const stylesheet of sourceDoc.styleSheets) {
        // For <link> elements (external stylesheets)
        if (stylesheet.href) {
            const el = targetDoc.createElement('link');
            el.rel = 'stylesheet';
            el.href = stylesheet.href;
            targetDoc.head.appendChild(el);
        }

        // For <style> elements (inline or generated styles)
        else if (stylesheet.ownerNode && stylesheet.ownerNode.textContent) {
            const el = targetDoc.createElement('style');
            el.textContent = stylesheet.ownerNode.textContent;
            targetDoc.head.appendChild(el);
        }
    }
};
