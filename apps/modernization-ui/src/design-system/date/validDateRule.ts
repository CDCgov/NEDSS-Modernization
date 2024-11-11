import { validateIfPresent } from 'validation';
import { validateDate } from './validateDate';

const validDateRule = (name: string) => ({
    validate: validateIfPresent(validateDate(name))
});

export { validDateRule };
