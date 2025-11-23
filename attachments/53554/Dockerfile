FROM jenkins/jenkins:lts

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt
USER root
RUN usermod jenkins -a -G shadow
RUN echo "jenkins:jenkins" | chpasswd
USER jenkins
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false
