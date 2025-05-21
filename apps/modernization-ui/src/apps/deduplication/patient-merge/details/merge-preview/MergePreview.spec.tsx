import { render, screen, fireEvent } from '@testing-library/react';

// Mock dummyData with alias-only data for most tests
jest.mock('./dummyData', () => {
    const originalModule = jest.requireActual('./dummyData.ts');
    return {
        __esModule: true,
        ...originalModule,
        dummyPatientData: {
            ...originalModule.dummyPatientData,
            name: [
                {
                    asOf: '01/01/2024',
                    type: 'Alias',  // Only Alias type, no Legal
                    prefix: '',
                    last: 'Smith',
                    first: 'Johnny',
                    middle: '',
                    suffix: '',
                    degree: '',
                    selected: false,
                },
            ],
        },
    };
});

import { MergePreview } from './MergePreview';

describe('MergePreview', () => {
    it('renders the "Merge preview" heading', () => {
        render(<MergePreview onBack={jest.fn()} />);
        const heading = screen.getByRole('heading', { name: /merge preview/i });
        expect(heading).toBeInTheDocument();
    });

    // This test uses real dummyData, unmock + dynamic import inside it
    it('renders the preview header with patient summary', async () => {
        jest.unmock('./dummyData');
        jest.resetModules();

        // Import fresh MergePreview with real dummyData
        const { MergePreview } = await import('./MergePreview');

        render(<MergePreview onBack={jest.fn()} />);
        expect(screen.getByText(/Super/i)).toBeInTheDocument();
        expect(screen.getByText(/Mario/i)).toBeInTheDocument();
        expect(screen.getByText(/Male/i)).toBeInTheDocument();
        expect(screen.getByText(/12\/04\/1975/i)).toBeInTheDocument();
        expect(screen.getByText(/49/i)).toBeInTheDocument();
        expect(screen.getByText(/98765/i)).toBeInTheDocument();
    });

    it('calls onBack when Back button is clicked', () => {
        const onBackMock = jest.fn();
        render(<MergePreview onBack={onBackMock} />);
        const backButton = screen.getByRole('button', { name: /back/i });
        fireEvent.click(backButton);
        expect(onBackMock).toHaveBeenCalledTimes(1);
    });

    it('falls back to "---" for first and last name when no Legal name found', () => {
        render(<MergePreview onBack={jest.fn()} />);

        // Expect the fallback --- instead of real first/last names
        expect(screen.getByText(/---/)).toBeInTheDocument();

        // check that the alias names don't appear
        expect(screen.queryByText(/Super/i)).not.toBeInTheDocument();
        expect(screen.queryByText(/Mario/i)).not.toBeInTheDocument();
    });

    it('renders the "Merge record" button', () => {
        render(<MergePreview onBack={jest.fn()} />);
        const mergeButton = screen.getByRole('button', {
            name: /confirm and merge patient records/i,
        });
        expect(mergeButton).toBeInTheDocument();
    });
});
