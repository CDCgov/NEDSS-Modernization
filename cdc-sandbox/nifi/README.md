# NiFi

## When making changes to NiFi Processors

1. Make changes within a local NiFi container
2. Delete the existing template for the piece you have modified
3. Create a new template with changes
4. Download template and place in the `cdc-sanbox/nifi/templates/` folder
5. Copy the `flow.xml.gz` in the `/opt/nifi/nifi-current/conf` folder of the docker container into `cdc-sanbox/nifi/nifi_conf/conf/`
6. Commit changes
7. Create PR
