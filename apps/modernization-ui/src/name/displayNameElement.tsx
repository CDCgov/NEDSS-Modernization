import { ItemGroup } from 'design-system/item';
import { displayName } from './displayName';
import { DisplayableName, NameFormat } from './types';

/** Returns the full text + label name element. Example: "Alias Name\nMarty McFly"
 * @param {DisplayableName} name - The name object to display.
 * @param {NameFormat} format - The format to display names in, either 'full', 'short' or 'fullLastFirst'. Default = 'full'.
 * @return {JSX.Element} The address block as JSX.
 */
export const displayNameElement = (name: DisplayableName, format: NameFormat = 'fullLastFirst'): JSX.Element => {
    const nameText = displayName(format)(name);
    const nameHeader = name.type ?? undefined;
    return (
        <ItemGroup type="name" label={nameHeader}>
            {nameText}
        </ItemGroup>
    );
};
