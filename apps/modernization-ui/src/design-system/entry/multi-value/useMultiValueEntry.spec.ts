import { act, renderHook } from '@testing-library/react';
import { MultiValueEntrySettings, useMultiValueEntry } from './useMultiValueEntry';
import { entryIdentifierGenerator } from './entryIdentifierGenerator';

describe('useMultiValueEntry', () => {
    type Example = { id: number; name: string; description?: string; order?: number };

    const setup = (settings?: Partial<MultiValueEntrySettings<Example>>) => {
        const identifierGenerator = settings?.identifierGenerator
            ? settings.identifierGenerator
            : entryIdentifierGenerator;
        const values = settings?.values ?? [];

        return renderHook(() => useMultiValueEntry({ identifierGenerator, values }));
    };

    it('should support adding a new value', () => {
        const { result } = setup({ values: [{ id: 47, name: 'name-one' }] });

        act(() => {
            result.current.add({ id: 61, name: 'name-other' });
        });

        expect(result.current.entries).toEqual(
            expect.arrayContaining([
                expect.objectContaining({ value: expect.objectContaining({ id: 47, name: 'name-one' }) }),
                expect.objectContaining({ value: expect.objectContaining({ id: 61, name: 'name-other' }) })
            ])
        );
    });

    it('should default to no item selected', () => {
        const { result } = setup({
            values: [
                { id: 47, name: 'name-one' },
                { id: 61, name: 'name-other' }
            ]
        });

        expect(result.current.selected).toBeUndefined();
    });

    it('should select an item for viewing', () => {
        const identifierGenerator = jest
            .fn()
            .mockImplementationOnce(() => '557')
            .mockImplementationOnce(() => '601');

        const { result } = setup({
            identifierGenerator,
            values: [
                { id: 47, name: 'name-one' },
                { id: 61, name: 'name-other' }
            ]
        });

        act(() => {
            result.current.view('601');
        });

        expect(result.current).toEqual(
            expect.objectContaining({
                status: 'viewing',
                selected: expect.objectContaining({
                    id: '601',
                    value: expect.objectContaining({ id: 61, name: 'name-other' })
                })
            })
        );
    });

    it('should not select unknown entry for viewing', () => {
        const { result } = setup();

        act(() => {
            result.current.view('1013');
        });

        expect(result.current).toEqual(
            expect.objectContaining({
                status: 'adding'
            })
        );
    });

    it('should not select unknown entry for editing', () => {
        const { result } = setup();

        act(() => {
            result.current.edit('1013');
        });

        expect(result.current).toEqual(
            expect.objectContaining({
                status: 'adding'
            })
        );
    });

    it('should select an item for editing', () => {
        const identifierGenerator = jest
            .fn()
            .mockImplementationOnce(() => '557')
            .mockImplementationOnce(() => '601');

        const { result } = setup({
            identifierGenerator,
            values: [
                { id: 47, name: 'name-one' },
                { id: 61, name: 'name-other' }
            ]
        });

        act(() => {
            result.current.edit('601');
        });

        expect(result.current).toEqual(
            expect.objectContaining({
                status: 'editing',
                selected: expect.objectContaining({
                    id: '601',
                    value: expect.objectContaining({ id: 61, name: 'name-other' })
                })
            })
        );
    });

    it('should update the selected value', () => {
        const identifierGenerator = jest
            .fn()
            .mockImplementationOnce(() => '557')
            .mockImplementationOnce(() => '601');

        const { result } = setup({
            identifierGenerator,
            values: [
                { id: 47, name: 'name-one' },
                { id: 61, name: 'name-other' }
            ]
        });

        act(() => {
            result.current.edit('601');
            result.current.update({ id: 61, name: 'name-changed' });
        });

        expect(result.current.entries).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    id: '601',
                    value: expect.objectContaining({ id: 61, name: 'name-changed' })
                })
            ])
        );
    });

    it('should remove an existing value', () => {
        const identifierGenerator = jest
            .fn()
            .mockImplementationOnce(() => '557')
            .mockImplementationOnce(() => '601');

        const { result } = setup({
            identifierGenerator,
            values: [
                { id: 47, name: 'name-one' },
                { id: 61, name: 'name-other' }
            ]
        });

        act(() => {
            result.current.remove('601');
        });

        expect(result.current.entries).toEqual(
            expect.arrayContaining([expect.objectContaining({ id: '557' }), expect.not.objectContaining({ id: '601' })])
        );
    });

    it('should reset when removing the selected value', () => {
        const identifierGenerator = jest
            .fn()
            .mockImplementationOnce(() => '557')
            .mockImplementationOnce(() => '601');

        const { result } = setup({
            identifierGenerator,
            values: [
                { id: 47, name: 'name-one' },
                { id: 61, name: 'name-other' }
            ]
        });

        act(() => {
            result.current.view('557');
            result.current.remove('557');
        });

        expect(result.current.status).toEqual('adding');
    });
});
