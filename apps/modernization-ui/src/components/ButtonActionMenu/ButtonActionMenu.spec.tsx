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

    it('should display label on the left by default', () => {
        const { getByText } = render(
            <ButtonActionMenu label="Add new">
                <></>
            </ButtonActionMenu>
        );

        expect(getByText('Add new')).toBeInTheDocument();
        expect(getByText('Add new').previousElementSibling).toBeNull();
    });

    it('should display label on the right when specified', () => {
        const { getByText } = render(
            <ButtonActionMenu label="Add new" labelPosition="right">
                <></>
            </ButtonActionMenu>
        );

        expect(getByText('Add new')).toBeInTheDocument();
        expect(getByText('Add new').nextElementSibling).toBeNull();
    });
});
