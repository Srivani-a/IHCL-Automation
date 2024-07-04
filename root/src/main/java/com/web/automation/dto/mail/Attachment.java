package com.web.automation.dto.mail;

import java.nio.file.Path;
import java.util.Objects;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class Attachment {

	private final Path path;
	private final Path actualName;
	private final Path configurableName;
	
	public Attachment(Path path, Path configurableName) {
		super();
		if(Objects.isNull(path) || Objects.isNull(configurableName))
			throw new IllegalArgumentException("Attachment path cannot be null/empty.");
		this.path = path;
		this.configurableName = configurableName;
		actualName = path.getFileName();
	}

	public Path getPath() {
		return path;
	}

	public Path getActualName() {
		return actualName;
	}

	public Path getConfigurableName() {
		return configurableName;
	}

	@Override
	public String toString() {
		return "Attachment [path=" + path + ", actualName=" + actualName + ", configurableName=" + configurableName
				+ "]";
	}
}