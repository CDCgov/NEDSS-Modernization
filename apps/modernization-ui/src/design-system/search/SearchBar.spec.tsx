import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { SearchBar } from './SearchBar';

describe('SearchBar', () => {
    it('applies medium size class by default', () => {
        const { container } = render(<SearchBar />);
        const wrapper = container.querySelector(`.${'size-medium'}`);
        expect(wrapper).toBeInTheDocument();
    });

    it('applies small size class when size="small"', () => {
        const { container } = render(<SearchBar size="small" />);
        const wrapper = container.querySelector(`.${'size-small'}`);
        expect(wrapper).toBeInTheDocument();
    });

    it('applies large size class when size="large"', () => {
        const { container } = render(<SearchBar size="large" />);
        const wrapper = container.querySelector(`.${'size-large'}`);
        expect(wrapper).toBeInTheDocument();
    });

    it('does not apply tall class by default', () => {
        const { container } = render(<SearchBar />);
        const tallElement = container.querySelector(`.${'tall'}`);
        expect(tallElement).not.toBeInTheDocument();
    });

    it('applies tall class when tall is true', () => {
        const { container } = render(<SearchBar tall />);
        const tallElement = container.querySelector(`.${'tall'}`);
        expect(tallElement).toBeInTheDocument();
    });

    it('renders with placeholder', () => {
        render(<SearchBar placeholder="Search here..." />);
        expect(screen.getByPlaceholderText('Search here...')).toBeInTheDocument();
    });

    it('updates value internally when uncontrolled', () => {
        render(<SearchBar placeholder="Type something..." />);
        const input = screen.getByPlaceholderText('Type something...') as HTMLInputElement;

        fireEvent.change(input, { target: { value: 'apple' } });
        expect(input.value).toBe('apple');
    });

    it('calls onChange when controlled', () => {
        const handleChange = jest.fn();
        render(<SearchBar value="orange" onChange={handleChange} />);
        const input = screen.getByDisplayValue('orange');

        fireEvent.change(input, { target: { value: 'grape' } });
        expect(handleChange).toHaveBeenCalledWith('grape');
    });

    it('renders clear button when value is present (controlled)', () => {
        render(<SearchBar value="banana" onChange={jest.fn()} />);
        const clearButton = screen.getByRole('button', { name: /clear/i });
        expect(clearButton).toBeInTheDocument();
    });

    it('clears value when clear button is clicked (controlled)', () => {
        const handleChange = jest.fn();
        render(<SearchBar value="kiwi" onChange={handleChange} />);
        const clearButton = screen.getByRole('button', { name: /clear/i });

        fireEvent.click(clearButton);
        expect(handleChange).toHaveBeenCalledWith('');
    });

    it('clears value when clear button is clicked (uncontrolled)', () => {
        render(<SearchBar placeholder="Type..." />);
        const input = screen.getByPlaceholderText('Type...') as HTMLInputElement;

        fireEvent.change(input, { target: { value: 'text' } });
        expect(input.value).toBe('text');

        const clearButton = screen.getByRole('button', { name: /clear/i });
        fireEvent.click(clearButton);
        expect(input.value).toBe('');
    });

    it('shows focus class when input is focused', () => {
        const { container } = render(<SearchBar placeholder="Focus me" />);
        const input = screen.getByPlaceholderText('Focus me');

        fireEvent.focus(input);
        const focusedElement = container.querySelector(`.${'focused'}`);
        expect(focusedElement).toBeTruthy();
    });

    it('calls onSearch when the search button is clicked', () => {
        const handleSearch = jest.fn();
        const { getByLabelText } = render(
            <SearchBar value="Seashells" onSearch={handleSearch} />
        );

        const searchButton = getByLabelText('Search');
        fireEvent.click(searchButton);

        expect(handleSearch).toHaveBeenCalledWith('Seashells');
    });


    it('calls onSearch when Enter is pressed in the input', () => {
        const handleSearch = jest.fn();
        const { getByPlaceholderText } = render(
            <SearchBar value="Seashore" onSearch={handleSearch} placeholder="Search..." />
        );

        const input = getByPlaceholderText('Search...');
        fireEvent.keyDown(input, { key: 'Enter', code: 'Enter', charCode: 13 });

        expect(handleSearch).toHaveBeenCalledWith('Seashore');
    });
});
