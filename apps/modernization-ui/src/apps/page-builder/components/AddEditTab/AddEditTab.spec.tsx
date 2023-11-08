import { fireEvent, render, waitFor } from '@testing-library/react';
import { AlertProvider } from 'alert';
import { BrowserRouter } from 'react-router-dom';
import { AddEditTab } from './AddEditTab';
import Router from 'react-router';
import { UserContext } from 'user';
import { PagesTab } from 'apps/page-builder/generated';

jest.mock('react-router', () => ({
    ...jest.requireActual('react-router'),
    useParams: jest.fn()
}));

const tabData: PagesTab = {
    id: 123
};

beforeEach(() => {
    jest.spyOn(Router, 'useParams').mockReturnValue({ pageId: '1' });
});

afterEach(() => {
    jest.resetAllMocks();
});

describe('<AddTab />', () => {
    it('should render a grid with 3 inputs labels which are Tab Name, Tab Description, Visible', () => {
        const { getByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <AddEditTab tabData={tabData} setTabDetails={jest.fn()} />
                </AlertProvider>
            </BrowserRouter>
        );
        expect(getByText('Tab Name')).toBeInTheDocument();
        expect(getByText('Visible')).toBeInTheDocument();
    });
});
