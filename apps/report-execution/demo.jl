using HTTP
using JSON

nbs_custom_basic = """
{
    "version": 1,
    "is_export": true,
    "is_builtin": true,
    "report_title": "NBS Custom",
    "library_name": "nbs_custom",
    "data_source_name": "[NBS_ODSE].[dbo].[Filter_operator]",
    "subset_query": "SELECT * FROM [NBS_ODSE].[dbo].[Filter_operator]",
    "time_range": {"start": "2024-01-01", "end": "2024-12-31"}
}
"""

nbs_custom_with_where = """
{
    "version": 1,
    "is_export": true,
    "is_builtin": true,
    "report_title": "NBS Custom",
    "library_name": "nbs_custom",
    "data_source_name": "[NBS_ODSE].[dbo].[Filter_operator]",
    "subset_query": "SELECT config_key, config_value, short_name FROM [NBS_ODSE].[dbo].[NBS_Configuratino] WHERE config_value != default_value"
}
"""

nbs_sr_05 = """
{
    "version": 1,
    "is_export": true,
    "is_builtin": true,
    "report_title": "SR2: Disease statistics by state",
    "library_name": "nbs_sr_05",
    "data_source_name": "[NBS_ODSE].[dbo].[PHCDemographic]",
    "subset_query": "'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]'"
}
"""

function execute_report(spec)
    resp = HTTP.post(
        "http://localhost:8001/report/execute",
        body = spec,
        headers = ["Content-Type" => "application/json", "Accept" => "application/json"],
    )
    resp_json = String(take!(resp.body))
    parsed = JSON.parse(resp_json)
    csv = parsed["content"]
    fname = tempname()
    open(fname, "w") do f
        write(f, csv)
    end

    parsed["content"] = "Data writtenn to $fname"

    JSON.json(stdout, j; pretty=true)
end
