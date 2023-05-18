import { add } from 'date-fns';
const offest = { minutes: new Date().getTimezoneOffset() };

const asLocalDate = (value: string) => add(new Date(value), offest);

export { asLocalDate };
