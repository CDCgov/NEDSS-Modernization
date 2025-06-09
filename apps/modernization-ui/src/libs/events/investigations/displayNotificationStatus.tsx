import { Tag } from 'design-system/tag';
import { maybeMap } from 'utils/mapping';
import { equalsIgnoreCase } from 'utils/predicate';

const isRequiredStatus = equalsIgnoreCase('Rejected');

const displayNotificationStatus = maybeMap((value: string) =>
    isRequiredStatus(value) ? (
        <Tag size="small" weight="bold" variant="error">
            {value}
        </Tag>
    ) : (
        value
    )
);

export { displayNotificationStatus };
