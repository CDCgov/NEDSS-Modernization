import { DisplayableAddress } from 'address/display';
import { exists } from 'utils';

/** Returns the type and use as combined text. Example: "Dormitory/Home"
 * @param {DisplayableAddress} address - The address object with type and use fields.
 * @return {string} The result string.
 */
export const asAddressTypeUse = ({ type, use }: DisplayableAddress): string => {
    return [type, use].filter(exists).join('/');
};
