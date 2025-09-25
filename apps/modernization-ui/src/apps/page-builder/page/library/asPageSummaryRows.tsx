import { Link } from 'react-router';
import { PageSummary } from 'apps/page-builder/generated';
import { TableBody } from 'components/Table';
import { internalizeDate } from 'date';

const asTableRow = (page: PageSummary): TableBody => ({
    id: page.id,
    tableDetails: [
        {
            id: 1,
            title: (
                <div className="page-name">
                    <Link to={`/page-builder/edit/page/${page.id}`}>{page?.name}</Link>
                </div>
            )
        },
        { id: 2, title: <div className="event-text">{page.eventType?.name}</div> },
        {
            id: 3,
            title: (
                <div>
                    {page.conditions?.map((c, index) => (
                        <div key={index}>{c.name}</div>
                    ))}
                </div>
            )
        },
        { id: 4, title: page?.status },
        {
            id: 5,
            title: internalizeDate(page?.lastUpdate)
        },
        { id: 6, title: page?.lastUpdateBy }
    ]
});

const asTableRows = (pages: PageSummary[]): TableBody[] => pages.map(asTableRow);

export { asTableRows };
