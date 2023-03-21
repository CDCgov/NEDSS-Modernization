import { render } from '@testing-library/react';
import FormCard from './FormCard';

describe('General information component tests', () => {
    it('Should renders General information component', async () => {
        const { getByTestId } = render(
            <FormCard title="test title">
                <div>Test</div>
            </FormCard>
        );
        expect(getByTestId('title').innerHTML).toBe('test title');
    });
});
