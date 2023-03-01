import { render } from '@testing-library/react';
import { HorizontalTable } from './HorizontalTable';

describe('Table component', () => {
    it('Should renders table component', async () => {
        const { container } = render(
            <HorizontalTable tableHeader="Test Table Header" tableData={[{ title: 'test', text: '' }]} />
        );

        const tableHeader = container.getElementsByClassName('table-header');
        const noData = container.getElementsByClassName('no-data');
        expect(tableHeader[0].innerHTML).toBe('Test Table Header');
        noData[0] && expect(noData[0].innerHTML).toBe('No data');
    });
});
