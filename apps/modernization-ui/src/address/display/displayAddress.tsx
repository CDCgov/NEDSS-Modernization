import { ItemGroup } from 'design-system/item';
import { exists } from 'utils';

export type DisplayableAddress = {
    type?: string | null;
    use?: string | null;
    address?: string | null;
    address2?: string | null;
    city?: string | null;
    state?: string | null;
    zipcode?: string | null;
};

/** Returns the multiline text portion of an address. Example: "123 Main St\nSpringfield, IL 62701"
 * @param {DisplayableAddress} address - The address object to display.
 * @return {string} The formatted address text.
 */
const displayAddressText = ({ address, address2, city, state, zipcode }: DisplayableAddress): string => {
    const street = [address, address2].filter(exists).join(', ');
    let location = [city, state].filter(exists).join(', ');
    location = [location, zipcode].filter(exists).join(' ');
    return [street, location].filter(exists).join('\n');
};

/** Returns the full text + label address element. Example: "Home 123 Main St Springfield, IL 62701"
 * @param {DisplayableAddress} address - The address object to display.
 * @return {JSX.Element} The address block as JSX.
 */
const displayAddress = (address: DisplayableAddress): JSX.Element => {
    const addressText = displayAddressText(address);
    const addressHeader = address.use ?? undefined;
    return (
        <ItemGroup type="address" label={addressHeader}>
            {addressText}
        </ItemGroup>
    );
};

export { displayAddress, displayAddressText };
