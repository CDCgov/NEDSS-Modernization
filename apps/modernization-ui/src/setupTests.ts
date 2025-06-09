import '@testing-library/jest-dom/vitest';
import { vi } from 'vitest';
import * as matchers from 'vitest-axe/matchers';

// eslint-disable-next-line no-undef
process.env.TZ = 'America/New_York';

// Provide a minimal jest mock for compatibility with most common usages
// Only assign the functions that are actually used in your codebase
globalThis.jest = {
    fn: vi.fn,
    clearAllMocks: vi.clearAllMocks,
    restoreAllMocks: vi.restoreAllMocks,
    spyOn: vi.spyOn
    // Add more mappings if needed
} as any;

expect.extend(matchers);
