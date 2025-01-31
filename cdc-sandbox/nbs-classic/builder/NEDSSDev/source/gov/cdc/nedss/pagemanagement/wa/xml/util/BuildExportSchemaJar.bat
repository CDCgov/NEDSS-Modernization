Rem XMLBeans file to generate the jar file
Rem The jar is regenerated each time the schema changes
scomp -noann -novdoc -out dmbTemplateExportSchema.jar DMBTemplateExportSchema.xsd
