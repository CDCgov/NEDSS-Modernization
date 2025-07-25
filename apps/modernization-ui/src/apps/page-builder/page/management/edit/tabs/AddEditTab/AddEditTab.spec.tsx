import { vi } from 'vitest';
import { render } from '@testing-library/react';
import { AlertProvider } from 'alert';
import { BrowserRouter } from 'react-router';
import { AddEditTab } from './AddEditTab';
import Router from 'react-router';
import { PagesTab, Tab } from 'apps/page-builder/generated';
import { ManageTabs } from '../ManageTabs/ManageTabs';
import { FormProvider, useForm } from 'react-hook-form';

vi.mock('react-router', async () => {
    const actual = await vi.importActual<typeof import('react-router')>('react-router');
    return {
        ...actual,
        default: actual,
        useParams: vi.fn(() => ({ pageId: '1' })) // Mock useParams to return a default value
    };
});

afterEach(() => {
    vi.resetAllMocks();
});

describe('<AddEditTab />', () => {
    it('should render a grid with 3 inputs labels which are Tab Name, Tab Description, Visible', () => {
        const FormWrapper = () => {
            const formMethods = useForm<Tab>(); // Call useForm hook inside a functional component
            return (
                <BrowserRouter>
                    <AlertProvider>
                        <FormProvider {...formMethods}>
                            <AddEditTab />
                        </FormProvider>
                    </AlertProvider>
                </BrowserRouter>
            );
        };
        const { getByText, container } = render(<FormWrapper />);
        const input = container.getElementsByTagName('input');
        expect(input).toHaveLength(2);
        expect(getByText('Tab name')).toBeInTheDocument();
        expect(getByText('Visible')).toBeInTheDocument();
    });
});
