import { DisplayableName } from './displayableName';

/**
 * Matches a name to the specified legal name. Default format is 'short' (first and last name only).
 * @param {string} name The name to check
 * @param {string} legalName The legal name checked against
 * @return {boolean} True if matches.
 */
const matchesLegalName = (name: DisplayableName, legalName?: DisplayableName | null | undefined): boolean => {
    if (!name || !legalName) {
        return false;
    }
    return name?.first === legalName?.first && name?.last === legalName?.last;
};

export { matchesLegalName };
