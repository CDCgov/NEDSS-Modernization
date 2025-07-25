import { render } from '@testing-library/react';
import { ValueView } from './ValueView';

describe('ValueView', () => {
    it('should display title and value', () => {
        const { getByText } = render(<ValueView title="title goes here" value="Value goes here" />);

        expect(getByText('title goes here')).toBeInTheDocument();
        expect(getByText('Value goes here')).toBeInTheDocument();
    });
});
