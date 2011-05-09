package de.leuphana.ardrone.dronesystem.old.domain;

public enum Commands {
	COMMAND_CONF_MAXHIGHT(
			"AT*REF=AT*CONFIG=%1$d,\"control:altitude_max\",\"{0}\"", true), /**/
	COMMAND_CONF_MAXHIGHT_800(
			"AT*REF= AT*CONFIG=%1$d,\"control:altitude_max\",\"800\"", true), /**/
	CONF_EULER_ANGLE("AT*CONFIG=%1$d,\"control:euler_angle_max\",\"{0}\""), /**/
	CONF_EULER_ANGLE_025("AT*CONFIG=%1$d,\"control:euler_angle_max\",\"0.25\""), /**/
	CONF_YAW("AT*CONFIG=%1$d,\"control:control_yaw\",\"{0}\""), /**/
	CONF_YAW_45("AT*CONFIG=%1$d,\"control:control_yaw\",\"4.5\""), /**/
	CONF_VZ_MAX_2000("AT*CONFIG=%1$d,\"control:control_vz_max\",\"2000.0\""), /**/
	CONF_VZ_MAX("AT*CONFIG=%1$d,\"control:control_vz_max\",\"{0}\""), /**/
	COMMAND_CONF_PMODE("AT*PMODE=%1$d,2"), /**/
	COMMAND_CONF_MISC("AT*MISC=%1$d,2,20,2000,3000"), /**/
	COMMAND_CONF_TRIM("AT*FTRIM=%1$d"), /**/
	COMMAND_START("AT*REF=%1$d,290718208"), /**/
	COMMAND_LAND("AT*REF=%1$d,290717696"), /**/
	COMMAND_HOVER("AT*PCMD=%1$d,0,0,0,0,0"), /**/
	COMMAND_HOVER_BACK("AT*PCMD=%1$d,1,0,0,0,0"), /**/
	// private final static String COMMAND_NAV =
	// "AT*PCMD=1,%6$d,%2$f,%3$f,%4$d,%5$d";
	COMMAND_NAV("AT*PCMD=%1$d,{0},{1},{2},{3},{4}"), /**/
	// private final static String COMMAND_NAV =
	// "AT*PCMD=%1$d,%6$d,%2$d,%3$d,%4$d,%5$d";
	// private final static String COMMAND_HORZPLANE2 = "AT*FTRIM=%1$d,";
	COMMAND_RESETWATCHDOG("AT*COMWDG=1"), /**/
	COMMAND_ACK("AT*CTRL=%1$d,5,0"), /**/
	CONF_OUTDOOR("AT*CONFIG=%1$d,\"control:outdoor\",\"TRUE\""), /**/
	CONF_INDOOR("AT*CONFIG=%1$d,\"control:outdoor\",\"FALSE\""), /**/
	CONF_SHELL_TRUE("AT*CONFIG=%1$d,\"control:flight_without_shell\",\"TRUE\""), /**/
	CONF_SHELL_FALSE(
			"AT*CONFIG=%1$d,\"control:flight_without_shell\",\"FALSE\""), /**/
	CONF_CONTROLLEVEL_DEFAULT("AT*CONFIG=%1$d,\"control:control_level\",\"3\""), /**/
	CONF_CONTROLLEVEL_RACE("AT*CONFIG=%1$d,\"control:control_level\",\"1\""), /**/
	// CONF_CONTROLLEVEL_DEFAULT("AT*CONFIG=%1$d,\"control:control_level\",\"1\""),
	// /**/
	// CONF_CONTROLLEVEL_RACE("AT*CONFIG=%1$d,\"control:control_level\",\"3\""),
	// /**/

	CONF_NAVDATA_TRUE("AT*CONFIG=%1$d,\"general:navdata_demo\",\"TRUE\""), /**/
	CONF_NAVDATA_FALSE("AT*CONFIG=%1$d,\"general:navdata_demo\",\"FALSE\""), /**/
	CONF_NAVDATA_ALL("AT*CONFIG=%1$d,\"general:navdata_all\",\"TRUE\""), /**/
	LIST("");

	private String command;
	private boolean onlyCounterNeeded;

	private Commands(String command, boolean onlyCounterNeeded) {
		this.command = command;
		this.onlyCounterNeeded = onlyCounterNeeded;
	}

	private Commands(String command) {
		this(command, false);
	}

	public String getCommandString() {
		return command;
	}
}
