import { now } from 'design-system/date/clock';
import { internalizeDate } from './InternalizeDate';

const today = () => internalizeDate(now());

export { today };
