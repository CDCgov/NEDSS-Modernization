import { createElement, act } from 'react';
import { renderHook } from '@testing-library/react';
import { useConditionalRender } from './useConditionalRender';

describe('when conditionally rendering', () => {
    it('should not render by default', () => {
        const { result } = renderHook(() => useConditionalRender());

        const actual = result.current.render(createElement('h1', null, 'Heading'));

        expect(actual).toBeNull();
    });

    it('should render when shown', () => {
        const { result } = renderHook(() => useConditionalRender());

        act(() => {
            result.current.show();
        });

        const actual = result.current.render(createElement('h1', null, 'Heading'));

        expect(actual).not.toBeNull();
    });

    it('should not render when hidden', () => {
        const { result } = renderHook(() => useConditionalRender());

        act(() => {
            result.current.hide();
        });

        const actual = result.current.render(createElement('h1', null, 'Heading'));

        expect(actual).toBeNull();
    });

    it('should not render when hidden after being shown', () => {
        const { result } = renderHook(() => useConditionalRender());

        act(() => {
            result.current.show();
        });

        act(() => {
            result.current.hide();
        });

        const actual = result.current.render(createElement('h1', null, 'Heading'));

        expect(actual).toBeNull();
    });

    it('should render when toggled from default', () => {
        const { result } = renderHook(() => useConditionalRender());

        act(() => {
            result.current.toggle();
        });

        const actual = result.current.render(createElement('h1', null, 'Heading'));

        expect(actual).not.toBeNull();
    });

    it('should render when toggled from hidden', () => {
        const { result } = renderHook(() => useConditionalRender());

        act(() => {
            result.current.hide();
        });

        act(() => {
            result.current.toggle();
        });

        const actual = result.current.render(createElement('h1', null, 'Heading'));

        expect(actual).not.toBeNull();
    });

    it('should not render when toggled after being shown', () => {
        const { result } = renderHook(() => useConditionalRender());

        act(() => {
            result.current.show();
        });

        act(() => {
            result.current.toggle();
        });

        const actual = result.current.render(createElement('h1', null, 'Heading'));

        expect(actual).toBeNull();
    });
});
