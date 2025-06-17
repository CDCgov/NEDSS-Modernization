import { render, screen } from '@testing-library/react';
import { ValueField } from './ValueField';

describe('ValueField', () => {
    it('should display title and value', () => {
        render(<ValueField title="title goes here">Value goes here</ValueField>);

        expect(screen.getByText('title goes here')).toBeInTheDocument();
        expect(screen.getByText('Value goes here')).toBeInTheDocument();
    });

    it('should display no data placeholder when children is empty', () => {
        render(<ValueField title="title goes here" />);

        expect(screen.getByText('---')).toBeInTheDocument();
    });
});
