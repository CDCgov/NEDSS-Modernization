import { ButtonActionMenu } from './ButtonActionMenu';
import { Button } from 'design-system/button';
import { render } from '@testing-library/react';

describe('When ButtonActionMenu renders', () => {
    it('should not display menu by default', () => {
        const { queryByText } = render(
            <ButtonActionMenu label="Add new">
                <>
                    <Button type="button" onClick={() => jest.fn()}>
                        Test this
                    </Button>
                    <Button type="button" onClick={() => jest.fn()}>
                        Test that
                    </Button>
                </>
            </ButtonActionMenu>
        );

        expect(queryByText('Add new')).toBeInTheDocument();
        expect(queryByText('Test this')).not.toBeInTheDocument();
        expect(queryByText('Test that')).not.toBeInTheDocument();
    });
});
