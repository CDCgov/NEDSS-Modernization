import { useForm } from 'react-hook-form';
import { act, renderHook } from '@testing-library/react-hooks';
import { removeTerm } from './removeTerm';
import { asSelectable, Selectable } from 'options';

describe('when removing search terms', () => {
    it('should remove a term with a single value', () => {
        const { result } = renderHook(() => useForm<{ value?: string }>());

        result.current.setValue('value', 'current-value');

        const after = jest.fn();

        const resetField = jest.spyOn(result.current, 'resetField');

        const remove = removeTerm(result.current, after);

        remove({ source: 'value', title: 'title-value', name: 'name-value', value: 'current-value' });

        expect(after).toBeCalled();

        expect(resetField).toBeCalledWith('value');
    });
    it('should remove a term with multi values', () => {
        const { result } = renderHook(() => useForm<{ values?: string[] }>());

        result.current.setValue('values', ['one', 'two', 'current-value']);

        const after = jest.fn();

        const remove = removeTerm(result.current, after);

        act(() => {
            remove({ source: 'values', title: 'title-value', name: 'name-value', value: 'current-value' });
        });

        expect(after).toBeCalled();

        const actual = result.current.getValues();

        expect(actual).toEqual(expect.objectContaining({ values: ['one', 'two'] }));
    });

    it('should remove a term with multi selectable values', () => {
        const { result } = renderHook(() => useForm<{ values?: Selectable[] }>());
        result.current.setValue('values', [asSelectable('one'), asSelectable('two'), asSelectable('current-value')]);

        const after = jest.fn();

        const remove = removeTerm(result.current, after);

        act(() => {
            remove({ source: 'values', title: 'title-value', name: 'name-value', value: 'current-value' });
        });

        expect(after).toBeCalled();

        const actual = result.current.getValues();

        expect(actual).toEqual(expect.objectContaining({ values: [asSelectable('one'), asSelectable('two')] }));
    });
});
