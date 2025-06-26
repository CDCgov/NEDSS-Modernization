import { Type } from './Message';

const resolveIcon = (type: Type) => {
    if (type == 'information') {
        return 'info';
    } else if (type == 'success') {
        return 'check_circle';
    } else if (type == 'warning') {
        return 'warning';
    } else if (type == 'error') {
        return 'error';
    }
};

export { resolveIcon };
