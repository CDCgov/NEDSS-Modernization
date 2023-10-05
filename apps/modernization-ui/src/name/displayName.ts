import { exists } from 'utils';

type DisplayableName = {
    first?: string | null;
    middle?: string | null;
    last?: string | null;
    suffix?: string | null;
};

type Format = 'full' | 'short';

/**
 * Displays a name in a 'full' or 'short' format.  Where the 'short' format will
 * produce a string with the first and last name and the 'full' format will produce
 * a string with the fisrt, middle, last, and suffix.
 *
 * Given the name
 *  {
 *      first: 'Martin',
 *      middle: 'Seamus',
 *      last: 'McFly',
 *      suffix: 'Jr.'
 * }
 *
 * The 'full' format would produce "Martin Seamus McFly, Jr." and the 'short' format
 * would produce "Martin McFly"
 *
 * @param {string} format The format to display names in, either 'full' or 'short'
 * @return {string}
 */
const displayName = (format: Format = 'full') => (format === 'full' ? displayFullName : displayShortName);

const displayFullName = ({ first, middle, last, suffix }: DisplayableName) => {
    const name = [first, middle, last].filter(exists).join(' ');
    return [name, suffix].filter(exists).join(', ');
};

const displayShortName = ({ first, last }: DisplayableName) => {
    return [first, last].filter(exists).join(' ');
};

export { displayName };
