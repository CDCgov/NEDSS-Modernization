import { render } from '@testing-library/react';
import { PagesResponse } from 'apps/page-builder/generated';
import { EditPageHeader } from './EditPageHeader';
import { AlertProvider } from 'alert';

describe('when EditPageHeader renders', () => {
    const page: PagesResponse = {
        id: 123456,
        name: 'Test Page',
        description: 'Test Page description',
        tabs: [
            {
                id: 1119232,
                name: 'Patient',
                sections: [],
                visible: true
            },
            {
                id: 1119225,
                name: 'Vaccination',
                sections: [],
                visible: true
            }
        ],
        rules: []
    };
    const mockFunction = jest.fn();

    it('should display Page name', () => {
        const { getByText } = render(
            <AlertProvider>
                <EditPageHeader page={page} handleSaveDraft={mockFunction} />
            </AlertProvider>
        );

        expect(getByText('Test Page')).toBeInTheDocument();
    });

    it('should display Page description', () => {
        const { getByText } = render(
            <AlertProvider>
                <EditPageHeader page={page} handleSaveDraft={mockFunction} />
            </AlertProvider>
        );
        expect(getByText('Test Page description')).toBeInTheDocument();
    });
});
