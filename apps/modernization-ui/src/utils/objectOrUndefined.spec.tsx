import { objectOrUndefined } from './objectOrUndefined';

describe('objectOrUndefined tests', () => {
    it('an object with only undefined fields returns undefined', () => {
        const actual = objectOrUndefined({ field1: undefined, field2: undefined });

        expect(actual).toEqual(undefined);
    });

    it('an object with one defined field returns the object', () => {
        const object = { field1: undefined, field2: 'not empty!' };
        const actual = objectOrUndefined(object);

        expect(actual).toEqual(object);
    });

    it('an object with sub objects defined returns the object', () => {
        const object = { field1: undefined, field2: { somethingElse: 'abc' } };
        const actual = objectOrUndefined(object);

        expect(actual).toEqual(object);
    });
});
