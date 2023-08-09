package gov.cdc.nbs.questionbank.page.command;

public sealed interface PageDetailCommand {
	long id();
	
	public record PageTab(
			long id
			) implements PageDetailCommand{
		
	}
	
	public record PageSection (
			long id
			)implements PageDetailCommand {
		
	}
	
	public record PageSubSection (
			long id
			)implements PageDetailCommand {
		
	}
	

}
