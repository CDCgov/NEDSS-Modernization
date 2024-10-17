import { DisplayableName, NameFormat } from './types';

/**
 * Matches a name to the specified legal name. Default format is 'short' (first and last name only).
 * @param {string} name The name to check
 * @param {string} legalName The legal name checked against
 * @param {NameFormat} format The format to display names in, either 'full', 'short' or 'fullLastFirst'. Default = 'short'.
 * @return {boolean} True if matches.
 */
export const matchesLegalName = (
    name: DisplayableName,
    legalName?: DisplayableName | null | undefined,
    format?: NameFormat
): boolean => {
    if (!name || !legalName) {
        return false;
    }
    if (format === 'fullLastFirst' || format === 'full') {
        return (
            name?.first === legalName?.first &&
            name?.middle === legalName?.middle &&
            name?.last === legalName?.last &&
            name?.suffix === legalName?.suffix
        );
    } else {
        return name?.first === legalName?.first && name?.last === legalName?.last;
    }
};
