import { render } from '@testing-library/react';
import { AlertProvider } from 'alert';
import { BrowserRouter } from 'react-router-dom';
import { AddEditTab } from './AddEditTab';
import Router from 'react-router';
import { PagesTab } from 'apps/page-builder/generated';

jest.mock('react-router', () => ({
    ...jest.requireActual('react-router'),
    useParams: jest.fn()
}));

const tabData: PagesTab = {
    id: 123,
    name: 'tab-name',
    order: 1,
    visible: true,
    sections: []
};

beforeEach(() => {
    jest.spyOn(Router, 'useParams').mockReturnValue({ pageId: '1' });
});

afterEach(() => {
    jest.resetAllMocks();
});

describe('<AddEditTab />', () => {
    it('should render a grid with 3 inputs labels which are Tab Name, Tab Description, Visible', () => {
        const { getByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <AddEditTab tabData={tabData} onChanged={jest.fn()} />
                </AlertProvider>
            </BrowserRouter>
        );
        expect(getByText('Tab Name')).toBeInTheDocument();
        expect(getByText('Visible')).toBeInTheDocument();
    });
});
