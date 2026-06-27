import React from 'react';
import { ActionProps } from 'react-querybuilder';
import { Button } from '../../../../../design-system/button';

const AddButton = (props: ActionProps) => {
    return (
        <Button
            type={'button'}
            onClick={(e) => props.handleOnClick(e)}
            secondary={props.className === 'ruleGroup-addGroup'}
        >
            {props.title}
        </Button>
    );
};

export { AddButton };
