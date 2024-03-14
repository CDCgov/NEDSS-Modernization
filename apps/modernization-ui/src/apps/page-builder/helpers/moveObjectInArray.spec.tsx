import { moveSubsectionInArray } from './moveObjectInArray';

describe('Object manipulation in Array', () => {
    it('should place two after three', () => {
        const values = [
            {
                name: 'one',
                isGrouped: false,
                id: 0,
                order: 0,
                visible: true,
                isGroupable: true,
                questionIdentifier: 'identifier',
                questions: []
            },
            {
                name: 'two',
                isGrouped: false,
                id: 1,
                order: 1,
                visible: true,
                isGroupable: true,
                questionIdentifier: 'identifier',
                questions: []
            },
            {
                name: 'three',
                isGrouped: false,
                id: 2,
                order: 2,
                visible: true,
                isGroupable: true,
                questionIdentifier: 'identifier',
                questions: []
            },
            {
                name: 'four',
                isGrouped: false,
                id: 3,
                order: 3,
                visible: true,
                isGroupable: true,
                questionIdentifier: 'identifier',
                questions: []
            },
            {
                name: 'five',
                isGrouped: false,
                id: 4,
                order: 4,
                visible: true,
                isGroupable: true,
                questionIdentifier: 'identifier',
                questions: []
            },
            {
                name: 'six',
                isGrouped: false,
                id: 5,
                order: 5,
                visible: true,
                isGroupable: true,
                questionIdentifier: 'identifier',
                questions: []
            },
            {
                name: 'seven',
                isGrouped: false,
                id: 6,
                order: 6,
                visible: true,
                isGroupable: true,
                questionIdentifier: 'identifier',
                questions: []
            },
            {
                name: 'eight',
                isGrouped: false,
                id: 7,
                order: 7,
                visible: true,
                isGroupable: true,
                questionIdentifier: 'identifier',
                questions: []
            }
        ];

        const actual = moveSubsectionInArray(values, 7, 3);

        expect(actual).toEqual(
            expect.arrayContaining([
                expect.objectContaining({ name: 'one', id: 0 }),
                expect.objectContaining({ name: 'two', id: 1 }),
                expect.objectContaining({ name: 'three', id: 2 }),
                expect.objectContaining({ name: 'eight', id: 7 }),
                expect.objectContaining({ name: 'four', id: 3 }),
                expect.objectContaining({ name: 'five', id: 4 }),
                expect.objectContaining({ name: 'six', id: 5 }),
                expect.objectContaining({ name: 'seven', id: 6 })
            ])
        );
    });
    it('should swap one and two', () => {
        const values = [
            {
                name: 'one',
                isGrouped: false,
                isGroupable: true,
                id: 0,
                order: 0,
                visible: true,
                questionIdentifier: 'identifier',
                questions: []
            },
            {
                name: 'two',
                isGrouped: false,
                isGroupable: true,
                id: 1,
                order: 1,
                visible: true,
                questionIdentifier: 'identifier',
                questions: []
            }
        ];
        const actual = moveSubsectionInArray(values, 0, 1);

        expect(actual).toEqual(
            expect.arrayContaining([
                expect.objectContaining({ name: 'two', id: 1 }),
                expect.objectContaining({ name: 'one', id: 0 })
            ])
        );
    });
});
