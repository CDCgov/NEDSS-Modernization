import { Button } from 'design-system/button';
import { ActionProps } from 'react-querybuilder';

const RemoveButton = (props: ActionProps) => {
    return (
        <Button
            icon="delete"
            type="button"
            className="trash-icon"
            tertiary={true}
            destructive={true}
            sizing="small"
            aria-label={props.title!}
            onClick={(e) => props.handleOnClick(e)}
        />
    );
};

export { RemoveButton };
