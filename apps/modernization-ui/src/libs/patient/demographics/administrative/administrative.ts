import { Supplier } from 'libs/supplying';
import { EffectiveDated } from 'utils';
import { Nullable } from 'utils/object';

type AdministrativeInformation = EffectiveDated &
    Nullable<{
        comment: string;
    }>;

type HasAdministrativeInformation = { administrative?: AdministrativeInformation };

export type { AdministrativeInformation, HasAdministrativeInformation };

const initial = (asOf: Supplier<string>): AdministrativeInformation => ({ asOf: asOf(), comment: null });

export { initial };

const labels = {
    asOf: 'As of',
    comment: 'Comments'
};

export { labels };
