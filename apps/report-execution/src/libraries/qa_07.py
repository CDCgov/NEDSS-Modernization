from src.db_transaction import Transaction
from src.models import ReportResult


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    library_params: dict,
    **kwargs,
):
    """QA07: Duplicate Cases. The Report will consist of 3 reports, identical 
    except for the number of days used, and will display in the NBS Report Module as:
•	QA07 - Duplicate Cases (30 Days)
•	QA07 - Duplicate Cases (60 Days)
•	QA07 - Duplicate Cases (90 Days)
This report generates a list, by name, of individuals that have possible duplicate 
case incidents. 
User filtering includes a time period (based on case confirmation date), diagnoses
and range between occurrences. “Cases” only include investigations with Case Status 
of Probable or Confirmed.

Conversion notes:
* The original SAS code had the days value hard coded in the library, but we made 
it a parameter that can be passed in from the API. 
    """
    content = trx.query(subset_query)

    header = f'Custom Report For Table: {data_source_name}'

    return ReportResult(content_type='table', content=content, header=header)
