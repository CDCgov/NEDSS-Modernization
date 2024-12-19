import { BasicIdentificationEntry } from '../entry';

const initial = (): Partial<BasicIdentificationEntry> => ({
    type: undefined,
    issuer: undefined,
    id: ''
});

export { initial };
