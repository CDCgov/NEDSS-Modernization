import { Button, Checkbox, Icon } from '@trussworks/react-uswds';
import { FindPatientsByFilterQuery } from '../../generated/graphql/schema';

type TableContentProps = {
    tableHead: { name: string; sortable: boolean }[];
    tableBody: FindPatientsByFilterQuery['findPatientsByFilter'];
};

export const TableContent = ({ tableHead, tableBody }: TableContentProps) => {
    const dateFormat = (date: Date) => {
        return new Date(date).toLocaleDateString('en-US');
    };

    return (
        <>
            <thead>
                <tr>
                    <th>
                        <Checkbox id="headCheckbox" name="headCheckbox" label="" />
                    </th>
                    {tableHead.map((head: any, index) => (
                        <th key={index} scope="col">
                            {head.name}
                            {head.sortable && (
                                <Button className="usa-button--unstyled" type={'button'}>
                                    <Icon.SortArrow />
                                </Button>
                            )}
                        </th>
                    ))}
                </tr>
            </thead>
            <tbody>
                {tableBody?.content.map((item: any, index) => (
                    <tr key={index}>
                        <td>
                            <Checkbox id={`${index}-checkbox`} name="checkbox" label="" />
                        </td>
                        <td>
                            {item.lastNm}, {item.firstNm}
                        </td>
                        <td>{dateFormat(item.birthTime)}</td>
                        <td>Staff</td>
                        <td>10 days ago</td>
                        <td>Negative</td>
                        <td>
                            <Button className="usa-button--unstyled" type={'button'}>
                                <Icon.MoreHoriz className="font-ui-2xl" />
                            </Button>
                        </td>
                    </tr>
                ))}
            </tbody>
        </>
    );
};
