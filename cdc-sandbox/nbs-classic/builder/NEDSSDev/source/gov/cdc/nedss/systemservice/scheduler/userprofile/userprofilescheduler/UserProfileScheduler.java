package gov.cdc.nedss.systemservice.scheduler.userprofile.userprofilescheduler;

import gov.cdc.nedss.systemservice.ejb.nbssecurityejb.updateprofilebatchprocess.PopulateProfileProcess;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;


public class UserProfileScheduler extends SchedulerUtil {
	static final LogUtils logger = new LogUtils(UserProfileScheduler.class.getName());

	boolean isProcessRuning= false;
	public void run() {
		logger.debug("Generating report for UserProfileScheduler");
		SchedulerUtil.resetProperties();
		String isUserProfileSchedulerToRun = SchedulerUtil.getPropertyValue("USER_PROFILE_SCHEDULE_TO_RUN");
		if (isUserProfileSchedulerToRun.equalsIgnoreCase("YES") && !isProcessRuning) {
			isProcessRuning= true;
			userProfileProcessor();
			isProcessRuning= false;
		}
	}

	private void userProfileProcessor() {
		String populateFlag = SchedulerUtil.getPropertyValue(SchedulerConstants.POPULATE_FLAG);
		String userName = SchedulerUtil.getPropertyValue(SchedulerConstants.USER_PROFILE_USER_NAME);

		String USER_PROFILE_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE = SchedulerUtil
				.getPropertyValue(SchedulerConstants.USER_PROFILE_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE);
		String USER_PROFILE_SCHEDULE_TIME_IN_HOUR = SchedulerUtil
				.getPropertyValue(SchedulerConstants.USER_PROFILE_SCHEDULE_TIME_IN_HOUR);
		String USER_PROFILE_SCHEDULE_TIME_IN_MINUTE = SchedulerUtil
				.getPropertyValue(SchedulerConstants.USER_PROFILE_SCHEDULE_TIME_IN_MINUTE);
		int USER_PROFILE_SCHEDULE_TIME_IN_HOUR_INT = Integer.parseInt(USER_PROFILE_SCHEDULE_TIME_IN_HOUR.trim());
		int USER_PROFILE_SCHEDULE_TIME_IN_MINUTE_INT = Integer.parseInt(USER_PROFILE_SCHEDULE_TIME_IN_MINUTE.trim());
		
		try {
			boolean isProcessReadyToRun = false;
			if (USER_PROFILE_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.DAILY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				isProcessReadyToRun = this.dailyBatchProcess(USER_PROFILE_SCHEDULE_TIME_IN_HOUR_INT, USER_PROFILE_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (USER_PROFILE_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.HOURLY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				isProcessReadyToRun = this.hourlyBatchProcess(processTime, isScheduledToRun,
						USER_PROFILE_SCHEDULE_TIME_IN_HOUR_INT, USER_PROFILE_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (USER_PROFILE_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.MINUTE)) {
				isProcessReadyToRun = this.minuteBatchProcess(runTimeInMinutes, runTimeMinuteDiff,
						USER_PROFILE_SCHEDULE_TIME_IN_MINUTE_INT);
			} else {
				logger.error("USER_PROFILE_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE is not defined correct as DAILY, HOURLY or MINUTE!");
			}
			if (isProcessReadyToRun)
				PopulateProfileProcess.userProfileProcess(populateFlag, userName);

		} catch (Exception ex) {
			logger.fatal("error raised in userProfileProcessor:" + SchedulerUtil.getStackTrace(ex));
		}
	}
}
