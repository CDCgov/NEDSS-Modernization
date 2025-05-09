import { DisplayablePhone } from 'generated';

import { ItemGroup } from 'design-system/item';

export const displayPhone = (phone?: DisplayablePhone) => {
    const phoneHeader = phone?.use;
    return (
        <ItemGroup type="phone" label={phoneHeader}>
            {phone?.number}
        </ItemGroup>
    );
};
