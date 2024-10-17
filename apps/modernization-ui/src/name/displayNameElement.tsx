import { ItemGroup } from 'design-system/item';
import { displayName } from './displayName';
import { DisplayableName, NameFormat } from './types';

/** Returns the full text + label address element. Example: "Home 123 Main St Springfield, IL 62701"
 * @param {DisplayableName} name - The address object to display.
 * @param {NameFormat} format - The format to display names in, either 'full', 'short' or 'fullLastFirst'. Default = 'full'.
 * @return {JSX.Element} The address block as JSX.
 */
export const displayNameElement = (name: DisplayableName, format?: NameFormat): JSX.Element => {
    const nameText = displayName(format)(name);
    const nameHeader = name.type ?? undefined;
    return (
        <ItemGroup type="name" label={nameHeader}>
            {nameText}
        </ItemGroup>
    );
};
