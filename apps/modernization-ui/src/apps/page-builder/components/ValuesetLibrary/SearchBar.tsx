import { Input } from 'components/FormInputs/Input';
import { Button } from '@trussworks/react-uswds';
import { Icon } from '@trussworks/react-uswds';

export const SearchBar = () => {
    return (
        <div className="valueset-local-library__header">
            <Input placeholder="Search pages by keyword" type="text" htmlFor="searchbar" id="searchbar" />
            <Button type="submit">
                <Icon.Search size={3} />
            </Button>
        </div>
    );
};
