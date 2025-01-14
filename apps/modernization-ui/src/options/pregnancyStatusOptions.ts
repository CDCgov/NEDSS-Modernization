import { asSelectable, Selectable } from './selectable';

const pregnancyStatusOptions: Selectable[] = [
    asSelectable('YES', 'Yes'),
    asSelectable('NO', 'No'),
    asSelectable('UNKNOWN', 'Unknown')
];

export { pregnancyStatusOptions };
