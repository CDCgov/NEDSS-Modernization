import { Tag } from 'design-system/tag';
import { maybeMap } from 'utils/mapping';

const displayStatus = maybeMap((value: string) =>
    value === 'Open' ? (
        <Tag size="small" weight="bold" variant="success">
            {value}
        </Tag>
    ) : (
        value
    )
);

export { displayStatus };
