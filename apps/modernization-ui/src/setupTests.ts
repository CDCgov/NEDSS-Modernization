import '@testing-library/jest-dom/vitest';
import { vi } from 'vitest';
import * as matchers from 'vitest-axe/matchers';

// All tests will create dates in the EST Timezone. UTC-5 or UTC-4 during DST
// eslint-disable-next-line no-undef
process.env.TZ = 'America/New_York';

// Provide a minimal jest mock for compatibility with most common usages
// Only assign the functions that are actually used in your codebase
globalThis.jest = {
    fn: vi.fn,
    mock: vi.mock,
    clearAllMocks: vi.clearAllMocks,
    restoreAllMocks: vi.restoreAllMocks,
    resetAllMocks: vi.resetAllMocks,
    spyOn: vi.spyOn,
    clearAllTimers: vi.clearAllTimers,
    useFakeTimers: vi.useFakeTimers,
    advanceTimersByTime: vi.advanceTimersByTime
    // Add more mappings if needed
} as any;

expect.extend(matchers);

// Global fetch mock to prevent live network calls in all tests
globalThis.fetch = vi.fn(() =>
    Promise.resolve({
        ok: true,
        status: 200,
        json: async () => ({}),
        text: async () => ''
        // Add more methods if needed
    } as unknown as Response)
);
