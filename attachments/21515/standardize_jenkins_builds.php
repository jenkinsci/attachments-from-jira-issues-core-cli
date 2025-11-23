<?php
for($i = 1; $i < count($argv); $i++) {
  $filename = $argv[$i].'/config.xml';
  $backup = "$filename.bak";
  if (!copy($filename, $backup)) {
      echo "Failed to copy $filename to $backup\n";
  }
  $data = file_get_contents($filename);
  $project = new SimpleXMLElement($data);
  //if (!file_put_contents('/tmp/old.xml', $project->asXML())) {
  //    echo "Failed to write to /tmp/old.xml\n";
  //}
  //$project->disabled = 'false';
  $project->builders->{'hudson.tasks.Shell'}->command = 'make test';
  //$project->triggers->{'hudson.triggers.TimerTrigger'}->spec = '@daily';
  //$project->publishers->{'hudson.tasks.BuildTrigger'}->childProjects = 'FullIntegrationBuild';
  //$project->buildWrappers->{'hudson.plugins.build__timeout.BuildTimeoutWrapper'}->timeoutMinutes = '5';
  //$project->buildWrappers->{'hudson.plugins.build__timeout.BuildTimeoutWrapper'}->failBuild = 'true';
  if (!file_put_contents($filename, $project->asXML())) {
      echo "Failed to write to $filename\n";
  }
  //if (!file_put_contents('/tmp/new.xml', $project->asXML())) {
  //    echo "Failed to write to /tmp/old.xml\n";
  //}
}
