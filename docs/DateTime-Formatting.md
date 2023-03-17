# Date/Time Formats 

## API Date/Time formats

### GraphQL

| Type     | Accepted                                         | Produced               |
|----------|--------------------------------------------------|------------------------|
| Date     | `MM/dd/yyyy`, `M/d/yy`, `M/d/yyyy`, `yyyy-MM-dd` | `yyyy-MM-dd`           |
| DateTime | `yyyy-MM-ddThh:mm:ssZ`                           | `yyyy-MM-ddThh:mm:ssZ` |
|          |                                                  |                        |

### JSON

| Type      | Accepted                                         | Produced               |
|-----------|--------------------------------------------------|------------------------|
| LocalDate | `MM/dd/yyyy`, `M/d/yy`, `M/d/yyyy`, `yyyy-MM-dd` | `yyyy-MM-dd`           |
| Instant   | `yyyy-MM-ddThh:mm:ssZ`                           | `yyyy-MM-ddThh:mm:ssZ` |

## Datastore Date/Time formats

Both Elasticsearch and MS SQL Server accept datetime strings formatted as `yyyy-MM-dd hh:mm:ss`. This is mostly handled
automatically by the libraries used to interact with each datastore. 
