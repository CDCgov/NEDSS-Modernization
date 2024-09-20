import { internalizeDate } from './InternalizeDate';

const today = () => internalizeDate(new Date());

export { today };
