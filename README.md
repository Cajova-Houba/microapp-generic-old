# Generic microapplication
Generic microapplication. Without any particular entities or DAOs. Can be builded by mvn compile command.

The purpose is to create persistent classes, their DAOs and then use different module for displaying.

If you want to use this, you should also do quite a lot of renaming. I will try to automate this later, but for now, here
is a list of things you _should_ rename:

* Every file with name containing 'microappName'. Don't forget to update reference in applicationContext-*-jpa.xml. 
* Also, if you change the name of jdbc-microappName.properties (which you certainly should), don't forget to change the jdbc.properties.file property in pom.xml
* Every bean in xml context files (*-jpa.xml and *-resources.xml) with name containing 'generic' or 'Generic'
* Persistence unit name in META-INF/persistence-microappName.xml. 
* Update references to those files (and entity manager factory bean) in org.microapp.generic.dao.jpa.BaseDaoTestCase 
* Update reference to the persistence unit in org.microapp.generic.dao.jpa.GenericDaoJpa

This module contains only generic package with gneric object (org.microapp.generic.*). For keeping things clear, I recomend to create a package org.microapp.microappName and put all your sources here. After creating this package, dont't forget to update <context:component-scan /> in applicationConfig-*-.jpa.xml.
