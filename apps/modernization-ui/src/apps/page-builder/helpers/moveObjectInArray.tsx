import { PagesQuestion, PagesSubSection, PagesTab } from '../generated';

export const moveSubsectionInArray = (array: PagesSubSection[], source: number, destination: number) => {
    const result = array.slice();
    const item = result.splice(source, 1)[0];

    const start = result.slice(0, destination);
    const end = result.slice(destination, array.length);
    return [...start, item, ...end];
};

export const moveQuestionInArray = (array: PagesQuestion[], source: number, destination: number) => {
    const result = array.slice();
    const item = result.splice(source, 1)[0];

    const start = result.slice(0, destination);
    const end = result.slice(destination, array.length);
    return [...start, item, ...end];
};

export const moveTabInArray = (array: PagesTab[], source: number, destination: number) => {
    const result = array.slice();
    const item = result.splice(source, 1)[0];

    const start = result.slice(0, destination);
    const end = result.slice(destination, array.length);
    return [...start, item, ...end];
};
