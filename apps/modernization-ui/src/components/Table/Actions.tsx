import { Button } from '@trussworks/react-uswds';

export const Actions = ({ handleAction }: any) => {
    return (
        <div className="actions-card">
            <Button
                onClick={() => handleAction('details')}
                unstyled
                type="button"
                className="text-base-dark display-block padding-1 border-bottom border-base-lighter text-no-underline width-full">
                Details
            </Button>
            <Button
                onClick={() => handleAction('edit')}
                unstyled
                type="button"
                className="text-base-dark display-block padding-1 border-bottom border-base-lighter text-no-underline width-full">
                Edit
            </Button>
            <Button
                onClick={() => handleAction('delete')}
                unstyled
                type="button"
                className="text-base-dark display-block padding-1 border-bottom border-base-lighter text-no-underline width-full">
                Delete
            </Button>
        </div>
    );
};
