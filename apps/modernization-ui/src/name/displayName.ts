import { exists } from 'utils';
import { NameFormat } from './format';
import { DisplayableName } from './displayableName';
import { mapOr } from 'utils/mapping';

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
 * The 'full' format would produce "Martin Seamus McFly, Jr.", the 'short' format
 * would produce "Martin McFly", and the 'fullLastFirst' format would produce "McFly, Martin Seamus, Jr."
 *
 * @param {string} format The format to display names in, either 'full', 'short' or 'fullLastFirst'
 * @return {string}
 */
const displayName = (format: NameFormat = 'full') => {
    switch (format) {
        case 'full':
            return displayFullName;

        case 'short':
            return displayShortName;

        case 'fullLastFirst':
            return displayFullNameLastFirst;
    }
};

const displayFullName = ({ first, middle, last, suffix }: DisplayableName) => {
    const name = [first, middle, last].filter(exists).join(' ');
    return [name, suffix].filter(exists).join(', ');
};

const displayFullNameLastFirst = ({ first, middle, last, suffix }: DisplayableName) => {
    const firstMiddle = [first || '--', middle].filter(exists).join(' ');
    const name = [last || '--', firstMiddle].filter(exists).join(', ');
    return [name, suffix].filter(exists).join(', ');
};

const displayShortName = ({ first, last }: DisplayableName) => {
    return [first, last].filter(exists).join(' ');
};

export { displayName };

const maybeDisplayName = mapOr(displayName('fullLastFirst'), '---');

export { maybeDisplayName };
