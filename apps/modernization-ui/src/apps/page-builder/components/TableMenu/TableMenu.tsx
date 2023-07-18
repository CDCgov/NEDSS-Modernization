import { Input } from 'components/FormInputs/Input';
import { Button } from '@trussworks/react-uswds';
import { Icon } from '@trussworks/react-uswds';
import { useNavigate } from 'react-router-dom';

type Props = {
    tableType: string;
};

export const TableMenu = ({ tableType }: Props) => {
    const navigate = useNavigate();
    const addNew = () => {
        navigate(`/page-builder/add/${tableType}`);
    };

    return (
        <div className="manage-pages__header">
            {/* Search input readonly until search is enabled */}
            <Input placeholder="Search pages by keyword" type="text" htmlFor="searchbar" id="searchbar" readOnly />
            <Button type="submit">
                <Icon.Search size={3} />
            </Button>
            <Button type="button" outline>
                <Icon.Print size={3} />
            </Button>
            <Button type="button" outline>
                <Icon.FileDownload size={3} />
            </Button>
            <Button type="button" onClick={addNew}>
                <p>Add new {tableType}</p>
            </Button>
        </div>
    );
};
