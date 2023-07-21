import { render } from '@testing-library/react';
import { AddValueset } from './AddValueset';

describe('General information component tests', () => {
    it('should display Add Valueset form', () => {
        const { getByTestId } = render(<AddValueset />);
        expect(getByTestId('button').innerHTML).toBe('Add value set');
    });
});
