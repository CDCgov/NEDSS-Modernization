import { Button } from '@trussworks/react-uswds';

export const Actions = ({ handleDetails }: any) => {
    return (
        <div className="actions-card">
            <Button
                onClick={handleDetails}
                unstyled
                type="button"
                className="text-base-dark display-block padding-1 border-bottom border-base-lighter text-no-underline width-full">
                Details
            </Button>
            <Button
                unstyled
                type="button"
                className="text-base-dark display-block padding-1 border-bottom border-base-lighter text-no-underline width-full">
                Edit
            </Button>
            <Button
                unstyled
                type="button"
                className="text-base-dark display-block padding-1 border-bottom border-base-lighter text-no-underline width-full">
                Delete
            </Button>
        </div>
    );
};
