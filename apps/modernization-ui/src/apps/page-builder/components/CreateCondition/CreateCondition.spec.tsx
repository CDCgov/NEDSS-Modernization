import { render } from '@testing-library/react';
import { CreateCondition } from './CreateCondition';

describe('General information component tests', () => {
    it('should display create condition form', () => {
        const { getByTestId } = render(<CreateCondition />);
        expect(getByTestId('header-title').innerHTML).toBe('Create a new Condition');
    });
});
