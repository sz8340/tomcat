print 'Connecting to ...'
connect('weblogic','weblogic1','t3://localhost:7001')
print 'Deploying...'
deploy('fda','/u01/fda.war',targets='DockerCluster')
startApplication('fda')
