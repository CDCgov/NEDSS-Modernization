import { useForm } from 'react-hook-form';
import { act, renderHook } from '@testing-library/react-hooks';
import { removeTerm } from './removeTerm';
import { asSelectable, Selectable } from 'options';

const DEFAULT_TERM = {
    source: 'source',
    title: 'title-value',
    name: 'name-value',
    value: 'current-value',
    partial: false
};

describe('when removing search terms', () => {
    it('should remove a term with a single value', () => {
        const { result } = renderHook(() => useForm<{ value?: string }>());

        result.current.setValue('value', 'current-value');

        const after = jest.fn();

        const setValue = jest.spyOn(result.current, 'setValue');
        const resetField = jest.spyOn(result.current, 'resetField');

        const remove = removeTerm(result.current, after);

        remove({ ...DEFAULT_TERM, source: 'value' });

        expect(after).toBeCalled();
        expect(setValue).toBeCalledWith('value', null);
        expect(resetField).toBeCalledWith('value');
    });

    it('should remove a term with multi values', () => {
        const { result } = renderHook(() => useForm<{ values?: string[] }>());

        result.current.setValue('values', ['one', 'two', 'current-value']);

        const after = jest.fn();

        const remove = removeTerm(result.current, after);

        act(() => {
            remove({ ...DEFAULT_TERM, source: 'values' });
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
            remove({ ...DEFAULT_TERM, source: 'values' });
        });

        expect(after).toBeCalled();

        const actual = result.current.getValues();

        expect(actual).toEqual(expect.objectContaining({ values: [asSelectable('one'), asSelectable('two')] }));
    });

    it('should remove a term from a string when partial is true', () => {
        const { result } = renderHook(() => useForm<{ value?: string }>());

        result.current.setValue('value', 'one, two, three');

        const after = jest.fn();

        const remove = removeTerm(result.current, after);

        act(() => {
            remove({ ...DEFAULT_TERM, source: 'value', value: 'two', partial: true });
        });

        expect(after).toBeCalled();

        const actual = result.current.getValues();

        expect(actual).toEqual(expect.objectContaining({ value: 'one, , three' }));
    });

    // it should handle a nested object path
    it('it should handle a nested object path', () => {
        const { result } = renderHook(() => useForm<{ nested: { value?: string } }>());

        result.current.setValue('nested.value', 'current-value');

        const after = jest.fn();
        const resetField = jest.spyOn(result.current, 'resetField');

        const remove = removeTerm(result.current, after);

        act(() => {
            remove({ ...DEFAULT_TERM, source: 'nested.value' });
        });

        expect(after).toBeCalled();
        expect(resetField).toBeCalledWith('nested.value');
    });
});
