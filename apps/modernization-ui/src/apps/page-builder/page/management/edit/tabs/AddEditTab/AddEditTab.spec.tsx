import { render } from '@testing-library/react';
import { AlertProvider } from 'alert';
import { BrowserRouter } from 'react-router';
import { AddEditTab } from './AddEditTab';
import Router from 'react-router';
import { PagesTab, Tab } from 'apps/page-builder/generated';
import { ManageTabs } from '../ManageTabs/ManageTabs';
import { FormProvider, useForm } from 'react-hook-form';

jest.mock('react-router', () => ({
    ...jest.requireActual('react-router'),
    useParams: jest.fn()
}));

beforeEach(() => {
    jest.spyOn(Router, 'useParams').mockReturnValue({ pageId: '1' });
});

afterEach(() => {
    jest.resetAllMocks();
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
