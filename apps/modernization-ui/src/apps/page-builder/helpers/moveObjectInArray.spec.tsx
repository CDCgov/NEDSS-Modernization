import { moveSubsectionInArray } from "./moveObjectInArray";

describe('Object manipulation in Array', () => {
    it('should place two after three', () => {
        const values = [
            { name: 'one', id: 0 },
            { name: 'two', id: 1 },
            { name: 'three', id: 2 },
            { name: 'four', id: 3 },
            { name: 'five', id: 4 },
            { name: 'six', id: 5 },
            { name: 'seven', id: 6 },
            { name: 'eight', id: 7 }
        ];

        const actual = moveSubsectionInArray(values, 7, 3);

        expect(actual).toEqual([
            { name: 'one', id: 0 },
            { name: 'two', id: 1 },
            { name: 'three', id: 2 },
            { name: 'eight', id: 7 },
            { name: 'four', id: 3 },
            { name: 'five', id: 4 },
            { name: 'six', id: 5 },
            { name: 'seven', id: 6 }
        ]);
    });
    it('should swap one and two', () => {
        const values = [
            { name: 'one', id: 0 },
            { name: 'two', id: 1 },
        ];
            const actual = moveSubsectionInArray(values, 0, 1);

            expect(actual).toEqual(
                expect.arrayContaining([
                    { name: 'two', id: 1 },
                    { name: 'one', id: 0 }
                ])
            );
    });
});
