import { Supplier } from 'libs/supplying';
import { EffectiveDated } from 'utils';

type AdministrativeInformation = EffectiveDated & {
    comment?: string | null;
};

type HasAdministrativeInformation = { administrative?: Partial<AdministrativeInformation> };

export type { AdministrativeInformation, HasAdministrativeInformation };

const initial = (asOf: Supplier<string>): AdministrativeInformation => ({ asOf: asOf(), comment: null });

export { initial };

const labels = {
    asOf: 'As of',
    comment: 'Comments'
};

export { labels };
