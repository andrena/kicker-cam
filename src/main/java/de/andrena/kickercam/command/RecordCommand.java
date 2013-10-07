package de.andrena.kickercam.command;

import java.io.File;

public class RecordCommand extends ShellCommand {
	public static final String PLAYLIST_FILENAME = "list.m3u8";

	private static final String RECORD_COMMAND = "raspivid -n -w 1280 -h 720 -fps 25 -g 25 -t 0 -b 10000000 -o -"
			+ " | psips | ffmpeg -y"
			+ "   -analyzeduration 0"
			+ "   -f h264"
			+ "   -i -"
			+ "   -c:v copy"
			+ "   -map 0:0"
			+ "   -f segment"
			+ "   -segment_time 1"
			+ "   -segment_format mp4"
			+ "   -segment_list " + PLAYLIST_FILENAME //
			+ "   -segment_list_size 4" //
			+ "   -segment_wrap 10" //
			+ "   -segment_list_flags live" //
			+ "   -segment_list_type hls" //
			+ "   segment-%02d.mp4";

	public RecordCommand(File workingDirectory) {
		super(RECORD_COMMAND, workingDirectory);
	}

}
