import { fireEvent, render, screen } from '@testing-library/react';
import { TableMenu } from './TableMenu';
import { BrowserRouter } from 'react-router-dom';
import { on } from 'events';

const props = {
    tableType: '',
    searchQuery: '',
    setSearchQuery: jest.fn(),
    onDownloadIconClick: jest.fn()
};

describe('TableMenu', () => {
    it('should render', () => {
        render(
            <BrowserRouter>
                <TableMenu {...props} />
            </BrowserRouter>
        );
        expect(screen.getByText('Add new')).toBeInTheDocument();
    });

    it('has a download button', () => {
        const { getByTestId } = render(
            <BrowserRouter>
                <TableMenu {...props} />
            </BrowserRouter>
        );

        const button = getByTestId('file-download');
        expect(button).toBeInTheDocument();
    });

    describe('when download icon is clicked', () => {
        it('should call onDownloadIconClick', () => {
            const { getByTestId } = render(
                <BrowserRouter>
                    <TableMenu {...props} />
                </BrowserRouter>
            );

            const button = getByTestId('file-download');
            fireEvent.click(button);

            expect(props.onDownloadIconClick).toHaveBeenCalled();
        });
    });
});
