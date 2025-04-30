import React from 'react';

/**
 * Handles keyboard events for accessible interactive elements.
 * Triggers the callback when Enter or Space is pressed.
 *
 * @param {React.KeyboardEvent} event - The keyboard event
 * @param {Function} [callback] - Optional callback function to execute
 * @return {void} Nothing
 */
export const triggerOnEnterOrSpace = (event: React.KeyboardEvent, callback?: () => void): void => {
    if (event.code === 'Enter' || event.code === 'Space') {
        event.stopPropagation();
        event.preventDefault();
        callback?.();
    }
};
