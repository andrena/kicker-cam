package de.andrena.kickercam.command;

import java.util.List;

public interface CatCommandFactory {

	Process run(List<String> playlistFiles) throws CommandException;

}