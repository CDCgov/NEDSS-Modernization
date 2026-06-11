import { vi } from 'vitest';
import '@testing-library/jest-dom/vitest';
import 'jest-axe/extend-expect';

// All tests will create dates in the EST Timezone. UTC-5 or UTC-4 during DST
// eslint-disable-next-line no-undef
process.env.TZ = 'America/New_York';

// Global fetch mock to prevent live network calls in all tests
globalThis.fetch = vi.fn(() =>
    Promise.resolve({
        ok: true,
        status: 200,
        json: async () => ({}),
        text: async () => '',
        // Add more methods if needed
    } as unknown as Response)
);

// Mock the global Request object for unit tests
type MockRequestInit = {
    method?: string;
    headers?: any;
    body?: any;
    credentials?: string;
};

class MockRequest {
    url: string;
    options: MockRequestInit;
    constructor(url: string, options?: MockRequestInit) {
        this.url = url;
        this.options = options || {};
    }
}

globalThis.Request = MockRequest as any;

globalThis.ResizeObserver = class {
    private callback: (...args: any[]) => void;
    constructor(callback: (...args: any[]) => void) {
        this.callback = callback;
    }
    observe() {}
    unobserve() {}
    disconnect() {}
};

// From focus-trap/focus-trap-react#1002 (comment)
async function mock(mockedUri: string, stub: unknown) {
    const { Module } = (await import('module')) as unknown as {
        Module: Record<string, (uri: string, parent: unknown) => void>;
    };
    Module._load_original = Module._load;
    Module._load = (uri, parent) => {
        if (uri === mockedUri) return stub;
        return Module._load_original(uri, parent);
    };
}

vi.hoisted(async () => {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const tabbable: { tabbable: any; focusable: any; isTabbable: any; isFocusable: any } =
        await vi.importActual('tabbable');
    return mock('tabbable', {
        ...tabbable,
        tabbable: (node: Element, options: object) => tabbable.tabbable(node, { ...options, displayCheck: 'none' }),
        focusable: (node: Element, options: object) => tabbable.focusable(node, { ...options, displayCheck: 'none' }),
        isTabbable: (node: Element, options: object) => tabbable.isTabbable(node, { ...options, displayCheck: 'none' }),
        isFocusable: (node: Element, options: object) =>
            tabbable.isFocusable(node, { ...options, displayCheck: 'none' }),
    });
});
