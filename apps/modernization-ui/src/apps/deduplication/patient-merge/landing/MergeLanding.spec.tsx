/**
 * @jest-environment jsdom
 */
import { render, within } from '@testing-library/react';
import { MergeLanding } from './MergeLanding';
import { MemoryRouter } from 'react-router';
import {fireEvent} from '@testing-library/react';

const Fixture = () => {
    return (
        <MemoryRouter>
            <MergeLanding />
        </MemoryRouter>
    );
};
describe('MergeLanding', () => {
    it('should have the proper heading', () => {
        const { getByRole } = render(<Fixture />);

        expect(getByRole('heading')).toHaveTextContent('Matches requiring review');
    });

    it('should have two buttons in the header', () => {
        const { getByRole } = render(<Fixture />);
        const buttons = within(getByRole('heading').parentElement!).getAllByRole('button');
        expect(buttons).toHaveLength(2);

        expect(buttons[0].children[0].children[0]).toHaveAttribute('xlink:href', 'undefined#print');
        expect(buttons[1].children[0].children[0]).toHaveAttribute('xlink:href', 'undefined#file_download');
    });

    it('should trigger CSV download when the download button is clicked', async () => {
        const mockBlob = new Blob(['id,name\n1,Alice\n2,Bob'], { type: 'text/csv' });
        const mockUrl = 'blob:http://localhost/fake-url';
        const mockClick = jest.fn();

        const mockAnchor = {
            href: '',
            download: '',
            click: mockClick,
            setAttribute: jest.fn(),
        } as unknown as HTMLAnchorElement;
        mockAnchor.click = mockClick;

        jest.spyOn(document, 'createElement').mockImplementation((tagName: string) => {
            if (tagName === 'a') return mockAnchor;
            return document.createElement(tagName);
        });

        global.URL.createObjectURL = jest.fn(() => mockUrl);

        // Mock fetch returning the CSV blob
        global.fetch = jest.fn().mockResolvedValue({
            ok: true,
            blob: () => Promise.resolve(mockBlob),
        }) as any;

        const { getByRole } = render(<Fixture />);
        const buttons = within(getByRole('heading').parentElement!).getAllByRole('button');

        const downloadButton = buttons.find((btn) =>
            btn.querySelector('use')?.getAttribute('xlink:href')?.endsWith('#file_download')
        );

        expect(downloadButton).toBeDefined();

        fireEvent.click(downloadButton!);
        await new Promise((r) => setTimeout(r, 0));

        expect(global.URL.createObjectURL).toHaveBeenCalledWith(mockBlob);
        expect(mockClick).toHaveBeenCalled();
    });
});
