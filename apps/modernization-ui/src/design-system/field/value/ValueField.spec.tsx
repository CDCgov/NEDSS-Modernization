import { render, screen } from '@testing-library/react';
import { ValueField } from './ValueField';

describe('ValueField', () => {
    it('should display title and value', () => {
        render(<ValueField label="title goes here">Value goes here</ValueField>);

        const actual = screen.getByRole('definition', { name: 'title goes here' });

        expect(actual).toHaveTextContent('Value goes here');
    });

    it('should display no data placeholder when children is empty', () => {
        render(<ValueField label="title goes here" />);

        const actual = screen.getByRole('definition', { name: 'title goes here' });

        expect(actual).toHaveTextContent('---');
    });
});
