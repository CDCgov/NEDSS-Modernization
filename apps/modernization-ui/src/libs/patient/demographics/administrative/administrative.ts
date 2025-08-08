import { today } from 'date';

type AdministrativeInformation = {
    asOf?: string;
    comment?: string;
};

type HasAdministrativeInformation = { administrative?: AdministrativeInformation };

export type { AdministrativeInformation, HasAdministrativeInformation };

const initial = (asOf: string = today()): AdministrativeInformation => ({ asOf });

export { initial };

const labels = {
    asOf: 'As of',
    comment: 'Comments'
};

export { labels };
