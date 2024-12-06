import { today } from 'date';
import { Selectable } from 'options';
import { EffectiveDated, HasComments } from 'utils';

type PhoneEmailEntry = EffectiveDated &
    HasComments & {
        type: Selectable | null;
        use: Selectable | null;
        countryCode?: string;
        phoneNumber?: string;
        extension?: string;
        email?: string;
        url?: string;
    };

export type { PhoneEmailEntry };

const initial = (asOf: string = today()): Partial<PhoneEmailEntry> => ({
    asOf,
    type: undefined,
    use: undefined,
    countryCode: '',
    phoneNumber: '',
    extension: '',
    email: '',
    url: '',
    comment: ''
});

export { initial };
