import { ItemGroup } from 'design-system/item';
import { DisplayableIdentification } from 'generated';

export const displayIdentification = (identifications?: Array<DisplayableIdentification>) => {
    return (
        <>
            {identifications?.map((id, key) => (
                <ItemGroup key={key} label={id.type}>
                    {id?.value}
                </ItemGroup>
            ))}
        </>
    );
};
