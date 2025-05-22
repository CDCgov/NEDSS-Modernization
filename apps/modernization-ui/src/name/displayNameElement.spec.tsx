import { render } from '@testing-library/react';
import { displayNameElement } from './displayNameElement';

describe('displayNameElement', () => {
    it('should render short name format by default with label', () => {
        const name = { first: 'John', last: 'Doe', type: 'Home' };
        const { getByText } = render(displayNameElement(name));
        expect(getByText('Home')).toBeInTheDocument();
        expect(getByText('Doe, John')).toBeInTheDocument();
    });

    it('should render full name format by default with label', () => {
        const name = { first: 'John', last: 'Doe', middle: 'Sawyer', type: 'Home' };
        const { getByText } = render(displayNameElement(name, 'full'));
        expect(getByText('Home')).toBeInTheDocument();
        expect(getByText('John Sawyer Doe')).toBeInTheDocument();
    });
});
