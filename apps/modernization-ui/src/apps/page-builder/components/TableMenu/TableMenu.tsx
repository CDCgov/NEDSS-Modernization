import { Input } from 'components/FormInputs/Input';
import { Button } from '@trussworks/react-uswds';
import { Icon } from '@trussworks/react-uswds';
import { useNavigate } from 'react-router-dom';

type Props = {
    tableType: string;
};

export const TableMenu = ({ tableType }: Props) => {
    const navigate = useNavigate();
    const onClick = () => {
        navigate(`/page-builder/add-new-${tableType}`);
    };

    return (
        <div className="manage-pages__header">
            <Input placeholder="Search pages by keyword" type="text" htmlFor="searchbar" id="searchbar" />
            <Button type="submit">
                <Icon.Search size={3} />
            </Button>
            <Button type="button" outline>
                <Icon.Print size={3} />
            </Button>
            <Button type="button" outline>
                <Icon.FileDownload size={3} />
            </Button>
            <Button type="button" onClick={onClick}>
                <p>Add new {tableType}</p>
            </Button>
        </div>
    );
};
