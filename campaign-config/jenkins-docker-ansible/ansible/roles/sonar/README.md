# Ansible Role: SonarQube

[![Build Status](https://travis-ci.org/geerlingguy/ansible-role-sonar.svg?branch=master)](https://travis-ci.org/geerlingguy/ansible-role-sonar)

An Ansible Role that installs [SonarQube](http://www.sonarqube.org/) on RedHat/CentOS and Debian/Ubuntu Linux servers.

## Requirements

Requires the `unzip` utility to be installed on the server. SonarQube 5.x+ requires Java 1.7+.

## Role Variables

Available variables are listed below, along with default values:

    workspace: /root

Directory where downloaded files will be temporarily stored.

    sonar_download_url: http://dist.sonar.codehaus.org/sonarqube-4.5.4.zip
    sonar_version_directory: sonarqube-4.5.4

The URL from which SonarQube will be downloaded, and the resulting directory name (should match the download archive, without the archive extension).

    sonar_web_host: ""
    sonar_web_port: ""
    sonar_web_context: ""

The hostname/IP address, port, and context through which SonarQube will be accessed. If none are provided, defaults are used.

    sonar_mysql_host: localhost
    sonar_mysql_port: 3306
    sonar_mysql_database: sonar
    sonar_mysql_user: sonar
    sonar_mysql_password: sonar

MySQL connection details.

    sonar_mysql_allowed_hosts:
      - 127.0.0.1
      - ::1
      - localhost

A list of hosts from which MySQL connections to the sonar database should be allowed.

## Dependencies

  - geerlingguy.java
  - geerlingguy.mysql

## Example Playbook

    - hosts: all
      roles:
        - { role: geerlingguy.sonar }

## License

MIT / BSD

## Author Information

This role was created in 2014 by [Jeff Geerling](http://jeffgeerling.com/), author of [Ansible for DevOps](http://ansiblefordevops.com/).
