export const COMMON_DELIMITERS = [',', ';', '\\s'];

/**
 * Split a string by common delimiters
 * @param {string} text The text to split
 * @return {string[]} An array of strings split by common delimiters, or an empty array if the text is empty or contains only delimiters
 */
export function splitStringByCommonDelimiters(text: string): string[] {
    return (
        text
            ?.split(/[,;\s]+/g)
            .map((s) => s.trim())
            .filter((s) => s.length > 0) ?? []
    );
}

/**
 * Trim common delimiters from the beginning and end of a string.
 * @param {string} text The text to trim
 * @return {string} the updated string
 */
export function trimCommonDelimiters(text: string): string {
    // remove any common delimiters at beginning or end of string
    return text?.replace(new RegExp(`^[${COMMON_DELIMITERS.join('')}]+|[${COMMON_DELIMITERS.join('')}]+$`, 'g'), '');
}

/**
 * Remove the specified value from a string and trim common delimiters.
 * @param {string} text The text to trim
 * @param {string} value The value to remove
 * @return {string} the updated string
 */
export function removeAndTrim(text: string, value: string) {
    if (!text) return '';
    return trimCommonDelimiters(text.replace(value, ''));
}
