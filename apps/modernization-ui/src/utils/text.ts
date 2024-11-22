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
