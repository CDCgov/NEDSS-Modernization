import { renderSelectables } from './renderSelectables';
import { Selectable } from './selectable';

describe('when rendering selectables', () => {
    it('should display names correctly', () => {
        const values: Selectable[] = [
            { name: 'test', value: 'test value' },
            { name: 'test2', value: 'test2 value' }
        ];

        expect(renderSelectables(values)).toBe('test, test2');
    });
});
