import { ActionProps } from 'react-querybuilder';
import { Button } from 'design-system/button';

const RemoveButton = (props: ActionProps) => {
    return (
        <Button
            icon="delete"
            type="button"
            className="trash-icon"
            tertiary
            destructive
            sizing="small"
            aria-label={props.title!}
            onClick={(e) => props.handleOnClick(e)}
        />
    );
};

export { RemoveButton };
