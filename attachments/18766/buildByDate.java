private AbstractBuild<?, ?> getStartBuild() {
	return getDatedBuild(startDate, true);
}

private AbstractBuild<?, ?> getEndBuild() {
	return getDatedBuild(endDate, false);
}

private AbstractBuild<?, ?> getDatedBuild(DateTime compareDate, boolean getNewerThanBoundary) {
	AbstractBuild<?, ?> build = project.getLastCompletedBuild();
	AbstractBuild<?, ?> lastBuild = null;
	
	while (build != null) {
		assert (build.getTimestamp().compareTo(build.getPreviousBuild().getTimestamp()) >= 0);
		DateTime buildTime = new DateTime(build.getTimestamp());
		if (buildTime.compareTo(compareDate) == -1) { break; }
		lastBuild = build;
		build = build.getPreviousBuild();
	}
	
	// We broke when we went over the boundary, so roll back one
	if (getNewerThanBoundary) { build = lastBuild; }
	
	return build;
}