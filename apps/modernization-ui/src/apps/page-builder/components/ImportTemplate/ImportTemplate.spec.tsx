import { fireEvent, render, waitFor } from '@testing-library/react';
import { CancelablePromise, Template, TemplateControllerService } from 'apps/page-builder/generated';
import { BrowserRouter } from 'react-router-dom';
import { ImportTemplate } from './ImportTemplate';
const toggleModal = jest.fn();
const modal = {
    isShowing: false,
    current: {
        modalId: '',
        modalIsOpen: true,
        toggleModal
    }
};

describe('General information component tests', () => {
    it('should display Import template form', () => {
        const { getByText } = render(<ImportTemplate modal={modal} onTemplateCreated={() => {}} />);
        expect(getByText('Import a new template')).toBeInTheDocument();
    });
});

describe('When page loads', () => {
    it('Import button should be disabled', () => {
        const { container } = render(
            <BrowserRouter>
                <ImportTemplate modal={modal} onTemplateCreated={() => {}} />
            </BrowserRouter>
        );
        const btn = container.getElementsByClassName('usa-button')[0];
        expect(btn.hasAttribute('disabled')).toBeTruthy();
    });
});

describe('When a file is selected', () => {
    it('should have an error if not .xml', async () => {
        const { container } = render(
            <BrowserRouter>
                <ImportTemplate modal={modal} onTemplateCreated={() => {}} />
            </BrowserRouter>
        );
        const file = new File(['fileContent'], 'template.txt', { type: 'txt' });
        const input = container.getElementsByTagName('input')[0];
        const submit = container.getElementsByClassName('submit-btn')[0];
        await waitFor(() => {
            fireEvent.change(input, { target: { files: [file] } });
        });

        await waitFor(() => {
            fireEvent.click(submit);
        });

        await waitFor(() => {
            const errorBanner = container.getElementsByClassName('banner')[0];
            expect(errorBanner).toBeInTheDocument();
        });
    });

    it('Import button should not be disabled', async () => {
        const { container } = render(
            <BrowserRouter>
                <ImportTemplate modal={modal} onTemplateCreated={() => {}} />
            </BrowserRouter>
        );
        const file = new File(['fileContent'], 'template.xml', { type: 'text/xml' });
        const input = container.getElementsByTagName('input')[0];
        await waitFor(() => {
            fireEvent.change(input, { target: { files: [file] } });
        });

        const btn = container.getElementsByClassName('usa-button')[0];
        expect(btn.hasAttribute('disabled')).toBeFalsy();
    });
});

describe('when a file is dropped', () => {
    it('Import button should be enabled', async () => {
        const { container } = render(
            <BrowserRouter>
                <ImportTemplate modal={modal} onTemplateCreated={() => {}} />
            </BrowserRouter>
        );
        const file = new File(['fileContent'], 'template.xml', { type: 'text/xml' });
        const dropContainer = container.getElementsByClassName('drop-container')[0];
        await waitFor(() => {
            fireEvent.drop(dropContainer, { dataTransfer: { files: [file] } });
        });

        const btn = container.getElementsByClassName('usa-button')[0];
        expect(btn.hasAttribute('disabled')).toBeFalsy();
    });
});

describe('When a file is successfully imported', () => {
    it('should excecute the callback', async () => {
        const mockImport = jest.spyOn(TemplateControllerService, 'importTemplateUsingPost');
        mockImport.mockImplementation(() => Promise.resolve({ id: 1 } as Template) as CancelablePromise<Template>);
        const callback = jest.fn(() => {});

        const { container } = render(
            <BrowserRouter>
                <ImportTemplate modal={modal} onTemplateCreated={callback} />
            </BrowserRouter>
        );

        const file = new File(['fileContent'], 'template.xml', { type: 'text/xml' });
        const input = container.getElementsByTagName('input')[0];
        await waitFor(() => {
            fireEvent.change(input, { target: { files: [file] } });
        });

        const submit = container.getElementsByClassName('submit-btn')[0];
        await waitFor(() => {
            fireEvent.click(submit);
        });

        await waitFor(() => {
            expect(mockImport).toHaveBeenCalled();
            expect(callback).toHaveBeenCalled();
        });
    });

    it('should close the modal', async () => {
        const mockImport = jest.spyOn(TemplateControllerService, 'importTemplateUsingPost');
        mockImport.mockImplementation(() => Promise.resolve({ id: 1 } as Template) as CancelablePromise<Template>);

        const { container } = render(
            <BrowserRouter>
                <ImportTemplate modal={modal} onTemplateCreated={() => {}} />
            </BrowserRouter>
        );

        const file = new File(['fileContent'], 'template.xml', { type: 'text/xml' });
        const input = container.getElementsByTagName('input')[0];
        await waitFor(() => {
            fireEvent.change(input, { target: { files: [file] } });
        });

        const submit = container.getElementsByClassName('submit-btn')[0];
        await waitFor(() => {
            fireEvent.click(submit);
        });

        await waitFor(() => {
            expect(toggleModal).toBeCalled();
        });
    });
});

describe('Import Template component tests', () => {
    it('should render a grid with 1 inputs labels which is  Choose file ', () => {
        const { getByText } = render(<ImportTemplate modal={modal} onTemplateCreated={() => {}} />);
        expect(getByText('Choose file')).toBeInTheDocument();
    });
});
