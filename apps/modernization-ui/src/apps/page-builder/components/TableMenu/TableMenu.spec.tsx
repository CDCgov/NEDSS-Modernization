import { fireEvent, render, screen } from '@testing-library/react';
import { TableMenu } from './TableMenu';
import { BrowserRouter } from 'react-router-dom';

const props = {
    tableType: '',
    searchQuery: '',
    setSearchQuery: jest.fn(),
    onDownloadIconClick: jest.fn(),
    onPrintIconClick: jest.fn()
};

describe('TableMenu', () => {
    it('should render', () => {
        render(
            <BrowserRouter>
                <TableMenu {...props} />
            </BrowserRouter>
        );
        expect(screen.getByText('Create new')).toBeInTheDocument();
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

    it('has a page porting button', () => {
        const { getByText } = render(
            <BrowserRouter>
                <TableMenu {...props} />
            </BrowserRouter>
        );

        const button = getByText('Page porting');
        expect(button).toBeInTheDocument();
    });

    it('has a print button', () => {
        const { getByTestId } = render(
            <BrowserRouter>
                <TableMenu {...props} />
            </BrowserRouter>
        );

        const button = getByTestId('print-icon');
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

    describe('when the print icon is clicked', () => {
        it('should call onDownloadIconClick', () => {
            const { getByTestId } = render(
                <BrowserRouter>
                    <TableMenu {...props} />
                </BrowserRouter>
            );

            const button = getByTestId('print-icon');
            fireEvent.click(button);

            expect(props.onPrintIconClick).toHaveBeenCalled();
        });
    });
});
